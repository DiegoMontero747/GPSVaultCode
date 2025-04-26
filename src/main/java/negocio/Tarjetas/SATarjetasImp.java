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

        String numeroDocumento = tarjeta.getNumeroDocumento();
        String iban = tarjeta.getIban();

        if (numeroDocumento == null || iban == null) {
            return new ResultContext(Evento.CREAR_TARJETA_ERROR_DATOS_NULOS, null);
        }

        numeroDocumento = numeroDocumento.trim();
        iban = iban.trim();

        if (numeroDocumento.isBlank() || iban.isBlank()) {
            return new ResultContext(Evento.CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS, null);
        }


        if (!validarIBAN(iban)) {
            return new ResultContext(Evento.CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE, null);
        }

        // Validar que el IBAN exista en la colección de cuentas
        Document docIban = new Document().append("numeroCuenta", iban);
        List<Document> listaCuentas = db.readDocument(docIban, Collections.CUENTABANC);
        if (listaCuentas.isEmpty()) {
            return new ResultContext(Evento.CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE, null);
        }

        Document docCuenta = listaCuentas.get(0);
        int numTarjetas = docCuenta.getInteger("numTarjetas", 0);
        if (numTarjetas >= 5) {
            return new ResultContext(Evento.CREAR_TARJETA_ERROR_MAX_TARJETAS, null);
        }

        // Comprobar si ya existe una tarjeta para el mismo documento
        Document docLeerTarjeta = new Document().append("numeroDocumento", numeroDocumento);
        List<Document> listaTarjetasExistentes = db.readDocument(docLeerTarjeta, Collections.TARJETA);
        if (!listaTarjetasExistentes.isEmpty()) {
            return new ResultContext(Evento.CREAR_TARJETA_ERROR_TARJETA_EXISTENTE, null);
        }

        String numeroTarjeta = generarNumeroTarjetaUnico();
        int cvv = generarCVV();
        String caducidad = generarCaducidad();

        // Crear el documento de la nueva tarjeta
        Document nuevaTarjeta = new Document()
                .append("Num_tarjeta", numeroTarjeta)
                .append("IBAN", iban)
                .append("cvv", cvv)
                .append("caducidad", caducidad)
                .append("tipoTarjeta", "Debito")
                .append("estado", "Activa")
                .append("numeroDocumento", numeroDocumento); // Asociar al cliente

        db.insertDocument(Collections.TARJETA, nuevaTarjeta);

        // Verificar que se haya insertado correctamente
        List<Document> listaTarjetas = db.readDocument(new Document("Num_tarjeta", numeroTarjeta), Collections.TARJETA);
        if (listaTarjetas.isEmpty()) {
            return new ResultContext(Evento.CREAR_TARJETA_ERROR_DB, null);
        }

        return new ResultContext(Evento.CREAR_TARJETA_OK, listaTarjetas.get(0));
    }

    private boolean validarIBAN(String iban) {
        String regexIBAN = "^[A-Z]{2}\\d{22}$";
        return iban.matches(regexIBAN);
    }

    private int generarCVV() {
        return (int)(Math.random() * 900) + 100; // Número entre 100 y 999
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
        } while (!db.readDocument(new Document("numeroTarjeta", numero), Collections.TARJETA).isEmpty());
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