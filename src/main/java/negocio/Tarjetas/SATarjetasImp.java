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
	        return new ResultContext(Evento.CREAR_TARJETA_ERROR_TARJETA_NULL, null);
	    }

	    String nombre = tarjeta.getNombre();
	    String apellidos = tarjeta.getApellidos();
        String tipoDocumento = tarjeta.getTipoDocumento();
        String numeroDocumento = tarjeta.getNumeroDocumento();
        String iban = tarjeta.getNumeroCuenta();
        String fechaNacimiento = tarjeta.getFechaNacimiento();
        String direccion = tarjeta.getDireccion();
        String telefono = tarjeta.getTelefono();

        if (nombre == null || apellidos == null || numeroDocumento == null || iban == null ||
                fechaNacimiento == null || direccion == null || telefono == null) {
                return new ResultContext(Evento.CREAR_TARJETA_ERROR_DATOS_NULOS, null);
            }
        
        nombre = nombre.trim();
        apellidos = apellidos.trim();
        numeroDocumento = numeroDocumento.trim();
        iban = iban.trim();
        fechaNacimiento = fechaNacimiento.trim();
        telefono = telefono.trim();
        //no hago trim a la direccion ya que habra espacios en blanco
	    
        if (nombre.isBlank() || apellidos.isBlank() || numeroDocumento.isBlank() || iban.isBlank() ||
                fechaNacimiento.isBlank() || direccion.isBlank() || telefono.isBlank()) {
                return new ResultContext(Evento.CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS, null);
        }
	    
	    if (!validarSoloAlfabeticos(nombre)) {
            return new ResultContext(Evento.ERROR_CADENA_NO_ALFABETICA, null);
        }
	    
	    if (!validarTelefono(telefono)) {
            return new ResultContext(Evento.ERROR_NUMERO_TELEFONO_INVALIDO, null);
        }

	    if ("DNI".equalsIgnoreCase(tipoDocumento)) {
	        if (!validarDNI(numeroDocumento))
	            return new ResultContext(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, null);
	    }
	    else if ("NIE".equalsIgnoreCase(tipoDocumento)) {
	        if (!validarNIE(numeroDocumento))
	            return new ResultContext(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, null);
	        }

	    // validar si la cuenta IBAN existe en la base de datos
	    Document docIban = new Document();
	    docIban.append("numeroCuenta", iban);
	    List<Document> listaCuentas = db.readDocument( docIban,Collections.CUENTABANC);
	    if (listaCuentas.isEmpty()  || !validarIBAN(iban)) {
	        return new ResultContext(Evento.CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE, null);
	    }
	    
	    Document docCuenta = db.readDocument(new Document("numeroCuenta", iban), Collections.CUENTABANC).get(0);
	    int numTarjetas = docCuenta.getInteger("numTarjetas", 0); // Si no tiene, asumimos 0

	    if (numTarjetas >= 5) {
	        return new ResultContext(Evento.CREAR_TARJETA_ERROR_MAX_TARJETAS, null);
	    }

	    Document nuevaTarjeta = new Document()
	        .append("nombreCompleto", nombre + " " + apellidos)
	        .append("tipoDocumento", tipoDocumento)
	        .append("numeroDocumento", numeroDocumento)
	        .append("numeroCuenta", iban)
	        .append("fechaNacimiento", fechaNacimiento)
	        .append("direccion", direccion)
	        .append("telefono", telefono)
	        .append("tipoTarjeta", "Debito")
	        .append("estado", "Activa");
	    
	    db.insertDocument(Collections.TARJETA, nuevaTarjeta);

	    // verificamos que la tarjeta ha sido introducida, PK: DNI/NIE
	    Document docLeerTarjeta = new Document().append("numeroDocumento", nuevaTarjeta.getString("numeroDocumento"));
	   List<Document> listaTarjetas = db.readDocument(docLeerTarjeta,Collections.TARJETA);
	    if (listaTarjetas.isEmpty()) {
	        return new ResultContext(Evento.CREAR_TARJETA_ERROR_DB, null); // si no encontramos el documento ha fallado la insercion en la bbdd
	    }

	    return new ResultContext(Evento.CREAR_TARJETA_OK,listaTarjetas.get(0)); // si encontramos el documento, la inserción fue exitosa
	}

	private boolean validarDNI(String dni) {
	    String regexDNI = "^[0-9]{8}[A-Za-z]$";
	    return dni.matches(regexDNI);
	}
	
	private boolean validarNIE(String nie) {
	    String regexNIE = "^[XYZxyz][0-9]{7}[A-Za-z]$";
	    return nie.matches(regexNIE);
	}

	private boolean validarIBAN(String iban) {
	    String regexIBAN = "^[A-Z]{2}\\d{22}$";
	    return iban.matches(regexIBAN);
	}
	
	 private boolean validarTelefono(String telefono) {
	        String regexTelefono = "^[0-9]{9}$";
	        return telefono.matches(regexTelefono);
	 }
	 
	 private boolean validarSoloAlfabeticos(String cadena) {
	        String regex = "^[A-Za-z]+$";
	        return cadena.matches(regex);
	    }

}
