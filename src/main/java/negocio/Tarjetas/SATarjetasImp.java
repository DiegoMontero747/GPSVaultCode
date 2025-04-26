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

		String numeroDocumento = tarjeta.getDocCliente();
		String iban = tarjeta.getIban();
		String tipoTarjeta = tarjeta.getTipoTarjeta();

		if (numeroDocumento == null || iban == null) {
			return new ResultContext(Evento.CREAR_TARJETA_ERROR_DATOS_NULOS, null);
		}

		numeroDocumento = numeroDocumento.trim();
		iban = iban.trim();

		if (numeroDocumento.isBlank() || iban.isBlank()) {
			return new ResultContext(Evento.CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS, null);
		}

		if (!validarDNI(numeroDocumento) && !validarNIE(numeroDocumento)) {
			return new ResultContext(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, null);
		}

		if (!validarIBAN(iban)) {
			return new ResultContext(Evento.CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE, null);
		}

		// Validar que el DNI exista en la coleccion de cuentas
		Document docCliente = new Document().append("DNI/NIE", numeroDocumento);
		List<Document> listaClientes = db.readDocument(docCliente, Collections.CUENTABANC);
		if (listaClientes.isEmpty()) {
			return new ResultContext(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, null);
		}

		// Validar que el IBAN exista en la colección de cuentas
		Document docIban = new Document().append("IBAN", iban);
		List<Document> listaCuentas = db.readDocument(docIban, Collections.CUENTABANC);
		if (listaCuentas.isEmpty()) {
			return new ResultContext(Evento.CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE, null);
		}

		// Validar que el IBAN ingresado esta asociado al DNI ingresado
		if (!listaCuentas.get(0).getString("DNI/NIE").equalsIgnoreCase(numeroDocumento)) {
			return new ResultContext(Evento.CREAR_TARJETA_ERROR_IBANDNI_NOASOCIADOS, null);
		}

		/*
		 * No tiene sentido, no existe numTarjetas en CUENTABANC Document docCuenta =
		 * listaCuentas.get(0); int numTarjetas = docCuenta.getInteger("numTarjetas",
		 * 0); if (numTarjetas >= 5) { return new
		 * ResultContext(Evento.CREAR_TARJETA_ERROR_MAX_TARJETAS, null); }
		 */
		List<Document> listaTarjetasXcuenta = db.readDocument(docIban, Collections.TARJETA);
		int nTarjetasActivas = 0;
		for (Document d : listaTarjetasXcuenta) {
			if (d.getString("estado").equalsIgnoreCase("Activa")) {
				nTarjetasActivas++;
			}
		}
		if (nTarjetasActivas >= 5) {
			return new ResultContext(Evento.CREAR_TARJETA_ERROR_MAX_TARJETAS, null);
		}

		/*
		 * Esto creo que es como comprobar que la tarjeta no exista ya en la db pero no
		 * tiene sentido hacerlo antes de haber generado el numero de la tarjeta //
		 * Comprobar si ya existe una tarjeta para el mismo documento Document
		 * docLeerTarjeta = new Document().append("numeroDocumento", numeroDocumento);
		 * List<Document> listaTarjetasExistentes = db.readDocument(docLeerTarjeta,
		 * Collections.TARJETA); if (!listaTarjetasExistentes.isEmpty()) { return new
		 * ResultContext(Evento.CREAR_TARJETA_ERROR_TARJETA_EXISTENTE, null); }
		 */

		String numeroTarjeta = generarNumeroTarjetaUnico();
		int cvv = generarCVV();
		String caducidad = generarCaducidad();

		// Crear el documento de la nueva tarjeta
		Document nuevaTarjeta = new Document().append("Num_tarjeta", numeroTarjeta).append("IBAN", iban)
				.append("cvv", cvv).append("caducidad", caducidad).append("tipoTarjeta", tipoTarjeta)
				.append("estado", "Activa");

		db.insertDocument(Collections.TARJETA, nuevaTarjeta);

		// Verificar que se haya insertado correctamente
		Document docTarjeta = new Document().append("Num_Tarjeta", numeroTarjeta);
		List<Document> listaTarjetas = db.readDocument(docTarjeta, Collections.TARJETA);
		if (listaTarjetas.isEmpty()) {
			return new ResultContext(Evento.CREAR_TARJETA_ERROR_DB, null);
		}

		return new ResultContext(Evento.CREAR_TARJETA_OK, listaTarjetas.get(0));
	}

	private boolean validarDNI(String DNI) {
		String regexDNI = "^[0-9]{8}[A-Za-z]$";
		return DNI.matches(regexDNI);
	}

	private boolean validarNIE(String NIE) {
		String regexNIE = "^[XYZxyz][0-9]{7}[A-Za-z]$";
		return NIE.matches(regexNIE);
	}

	private boolean validarIBAN(String iban) {
		String regexIBAN = "^[A-Z]{2}\\d{22}$";
		return iban.matches(regexIBAN);
	}

	private int generarCVV() {
		return (int) (Math.random() * 900) + 100; // Número entre 100 y 999
	}

	private String generarCaducidad() {
		java.time.LocalDate hoy = java.time.LocalDate.now();
		java.time.LocalDate caducidad = hoy.plusYears(4);
		int mes = caducidad.getMonthValue();
		int anio = caducidad.getYear() % 100; // Solo dos dígitos del año
		return String.format("%02d/%02d", mes, anio);
	}

	// -------- GENERADOR DE NÚMERO DE TARJETA --------
	private String generarNumeroTarjetaUnico() {
		String numero;
		do {
			numero = generarNumeroTarjetaAleatorio();
		} while (!db.readDocument(new Document().append("numeroTarjeta", numero), Collections.TARJETA).isEmpty());
		return numero;
	}

	private String generarNumeroTarjetaAleatorio() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			sb.append((int) (Math.random() * 10));
		}
		return sb.toString();
	}
}