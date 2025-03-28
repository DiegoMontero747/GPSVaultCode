package negocio.Tarjetas;

import java.util.List;

import org.bson.Document;

import integracion.bbdd.Collections;
import integracion.bbdd.MongoDBManager;
import negocio.Factory.ResultContext;
import presentacion.Controller.Evento;

public class SATarjetasImp implements SATarjetas {
	
	private MongoDBManager db;
	public SATarjetasImp() {
		db = MongoDBManager.getInstance();
	}
	
	public SATarjetasImp(MongoDBManager db) {
		this.db = db;
	}
	@Override
	public ResultContext crearTarjetaDebito(TTarjeta tarjeta) {
	    if (tarjeta == null) {
	        return new ResultContext(Evento.CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS, null);
	    }

	    String nombre = tarjeta.getNombreCompleto();
	    String tipoDocumento = tarjeta.getTipoDocumento();
	    String numeroDocumento = tarjeta.getNumeroDocumento();
	    String iban = tarjeta.getNumeroCuenta();

	    if(nombre!= null && numeroDocumento != null && iban != null) {
	    	nombre = nombre.trim(); numeroDocumento = numeroDocumento.trim(); iban = iban.trim();
	    }
	    
	    if (nombre == null|| numeroDocumento == null || iban == null) {
	        return new ResultContext(Evento.CREAR_TARJETA_ERROR_DATOS_NULOS, null);
	    }
	    
	    if (nombre.isBlank() || numeroDocumento.isBlank() || iban.isBlank()) {
	        return new ResultContext(Evento.CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS, null);
	    }

	    // Validación del tipo de documento (DNI o NIE)
	    if ("DNI".equalsIgnoreCase(tipoDocumento)) {
	        if (!validarDNI(numeroDocumento))
	            return new ResultContext(Evento.CREAR_TARJETA_ERROR_TIPO_DOCUMENTO_INVALIDO, null); // DNI inválido
	        
	    } else if ("NIE".equalsIgnoreCase(tipoDocumento)) {
	        if (!validarNIE(numeroDocumento))
	            return new ResultContext(Evento.CREAR_TARJETA_ERROR_TIPO_DOCUMENTO_INVALIDO, null); // NIE inválido
	    }

	    // Validar si la cuenta IBAN existe en la base de datos
	    Document docIban = new Document();
	    docIban.append("numeroCuenta", iban);
	    List<Document> listaCuentas = db.readDocument( docIban,Collections.CUENTABANC);
	    if (listaCuentas.isEmpty()  || !validarIBAN(iban)) {
	        return new ResultContext(Evento.CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE, null);
	    }

	    // Crear documento de la tarjeta
	    Document nuevaTarjeta = new Document()
	        .append("nombreCompleto", nombre)
	        .append("tipoDocumento", tipoDocumento)
	        .append("numeroDocumento", numeroDocumento)
	        .append("numeroCuenta", iban)
	        .append("tipoTarjeta", "Debito")
	        .append("estado", "Activa");

	    // Intentamos insertar el documento (void)
	    db.insertDocument(Collections.TARJETA, nuevaTarjeta);

	    // Verificación de que la tarjeta fue insertada buscando nuevamente el documento.
	    Document docLeerTarjeta = new Document().append("numeroDocumento", nuevaTarjeta.getString("numeroDocumento"));
	   List<Document> listaTarjetas = db.readDocument(docLeerTarjeta,Collections.TARJETA);
	    if (listaTarjetas.isEmpty()) {
	        return new ResultContext(Evento.CREAR_TARJETA_ERROR_DB, null); // Si no encontramos el documento, ha fallado la inserción
	    }

	    return new ResultContext(Evento.CREAR_TARJETA_OK,listaTarjetas.getFirst()); // Si encontramos el documento, la inserción fue exitosa
	}

	// Validar DNI: 8 numeros y una letra
	private boolean validarDNI(String dni) {
	    String regexDNI = "^[0-9]{8}[A-Za-z]$";
	    return dni.matches(regexDNI);
	}

	// Validar NIE: Letra X/Y/Z, 7 numeros y una letra
	private boolean validarNIE(String nie) {
	    String regexNIE = "^[XYZ]\\d{7}[A-Za-z]$";
	    return nie.matches(regexNIE);
	}

	private boolean validarIBAN(String iban) {
	    String regexIBAN = "^[A-Z]{2}\\d{22}$";
	    return iban.matches(regexIBAN);
	}



}