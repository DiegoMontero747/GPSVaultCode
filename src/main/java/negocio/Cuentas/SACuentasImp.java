package negocio.Cuentas;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import integracion.bbdd.Collections;
import integracion.bbdd.MongoDBManager;
import negocio.Factory.ResultContext;
import presentacion.Controller.Evento;

public class SACuentasImp implements SACuentas {

	private MongoDBManager db;

	// Constructor por defecto
	public SACuentasImp() {
		db = MongoDBManager.getInstance();
	}

	// Constructor con inyección de dependencias
	public SACuentasImp(MongoDBManager db) {
		this.db = db;
	}

	@Override
	public ResultContext crearCuentaBancaria(TCuenta cuenta) {
		if (cuenta == null) {
			return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_ERROR_CUENTA_NULL, null);
		}

		String nombre = cuenta.getNombre();
		String apellidos = cuenta.getApellidos();
		String tipoDoc = cuenta.getDNI();
		String direccion = cuenta.getDireccion();
		String cod_postal = cuenta.getCodPostal();
		String telefono = cuenta.getTelefono();

		if (nombre == null || apellidos == null || tipoDoc == null || direccion == null || telefono == null)
			return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_ERROR_DATOS_NULOS, null);

		nombre = nombre.trim();
		apellidos = apellidos.trim();
		tipoDoc = tipoDoc.trim();
		direccion = direccion.trim();
		cod_postal = cod_postal.trim();
		telefono = telefono.trim();

		if (nombre.isBlank() || apellidos.isBlank() || tipoDoc.isBlank() || direccion.isBlank() || telefono.isBlank()) {
			return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_ERROR_DATOS_INCOMPLETOS, null);
		}

		if (!validarSoloAlfabeticos(nombre)) {
			return new ResultContext(Evento.ERROR_CADENA_NO_ALFABETICA, null);
		}

		if (!validarSoloAlfabeticos(apellidos)) {
			return new ResultContext(Evento.ERROR_CADENA_NO_ALFABETICA, null);
		}
		
		if (!validarNumTelefono(telefono)) {
			return new ResultContext(Evento.ERROR_FORMATO_NUMERO_TELEFONO, null);
		}
		
		if (!validarCodPostal(cod_postal)) {
			return new ResultContext(Evento.ERROR_FORMATO_CODIGO_POSTAL, null);
		}


		if (!validarDNI(tipoDoc) && !validarNIE(tipoDoc)) {
			return new ResultContext(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, null);
		}


        
		String numeroCuenta = generarIBANUnico();

		// TODO adaptar los campos del SA al esquema de la BD de MongoValidator
		Document cliente = new Document().append("DNI/NIE", tipoDoc).append("Nombre", nombre)
				.append("Apellidos", apellidos).append("Telefono", Integer.valueOf(telefono)).append("Dir", direccion)
				.append("Cod-postal", Integer.valueOf(cod_postal));// Hay que modificar TCuenta para que lleve el codigo postal

		Document nuevaCuenta = new Document().append("IBAN", numeroCuenta).append("Titular", nombre).append("Fondos", Float.valueOf(0))
				.append("DNI/NIE", tipoDoc);

		// Esto debería comprobar si existe ya el cliente. Si existe, no hace nada, si
		// no, lo crea
		Document docLeerCliente = new Document().append("DNI/NIE", cliente.getString("DNI/NIE"));
		List<Document> comprobaciones = db.readDocument(docLeerCliente, Collections.CLIENTE);

		if (comprobaciones.isEmpty()) { //Si no existe el cliente, se inserta en la BD
			db.insertDocument(Collections.CLIENTE, cliente);
		}

		comprobaciones = db.readDocument(docLeerCliente, Collections.CLIENTE);

		if (!comprobaciones.isEmpty()) { //Se comprueba si existe el cliente en la BD
			db.insertDocument(Collections.CUENTABANC, nuevaCuenta);

			// verificamos que la cuenta fue insertada correctamente
			Document docLeerCuenta = new Document().append("DNI/NIE", nuevaCuenta.getString("DNI/NIE"));
			comprobaciones = db.readDocument(docLeerCuenta, Collections.CUENTABANC);
			if (comprobaciones.isEmpty()) {
				return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_ERROR_DB, null); // si no encontramos la cuenta ha
																						// fallado la insercion en la
																						// bbdd
			}
			return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_OK, comprobaciones.get(0)); // la cuenta fue creada
																								// exitosamente
		}
		return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_ERROR_DB, null); // Si no existe el cliente,
																				// es que ha fallado la insercion
	}

	private boolean validarDNI(String dni) {
		String regexDNI = "^[0-9]{8}[A-Za-z]$";
		return dni.matches(regexDNI);
	}

	private boolean validarNIE(String nie) {
		String regexNIE = "^[XYZxyz][0-9]{7}[A-Za-z]$";
		return nie.matches(regexNIE);
	}

	private boolean validarSoloAlfabeticos(String cadena) {
		String regex = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ\\s]+$";
		return cadena.matches(regex);
	}
	
	private boolean validarNumTelefono(String telefono) {
		String regex = "^[0-9]{9}$";
		return telefono.matches(regex);
	}
	
	private boolean validarCodPostal(String cod_postal) {
		String regex = "^[0-9]{5}$";
		return cod_postal.matches(regex);
	}

	// -------- GENERADOR DE IBAN --------
	private String generarIBANUnico() {
		String numeroIBAN;
		do {
			numeroIBAN = generarIBANAleatorio();
		} while (!db.readDocument(new Document("numeroCuenta", numeroIBAN), Collections.CUENTABANC).isEmpty());
		return numeroIBAN;
	}

	private String generarIBANAleatorio() {
		StringBuilder sb = new StringBuilder("ES");
		for (int i = 0; i < 22; i++) {
			sb.append((int) (Math.random() * 10));
		}
		return sb.toString();
	}
}