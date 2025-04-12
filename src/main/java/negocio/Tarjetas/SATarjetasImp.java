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
        } else if ("NIE".equalsIgnoreCase(tipoDocumento)) {
            if (!validarNIE(numeroDocumento))
                return new ResultContext(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, null);
        }

        // Validar IBAN
        Document docIban = new Document().append("numeroCuenta", iban);
        List<Document> listaCuentas = db.readDocument(docIban, Collections.CUENTABANC);
        if (listaCuentas.isEmpty() || !validarIBAN(iban)) {
            return new ResultContext(Evento.CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE, null);
        }

        Document docCuenta = listaCuentas.get(0);
        int numTarjetas = docCuenta.getInteger("numTarjetas", 0);
        if (numTarjetas >= 5) {
            return new ResultContext(Evento.CREAR_TARJETA_ERROR_MAX_TARJETAS, null);
        }

        // Comprobar si ya existe una tarjeta asociada al documento
        Document docLeerTarjeta = new Document().append("numeroDocumento", numeroDocumento);
        List<Document> listaTarjetasExistentes = db.readDocument(docLeerTarjeta, Collections.TARJETA);
        if (!listaTarjetasExistentes.isEmpty()) {
            return new ResultContext(Evento.CREAR_TARJETA_ERROR_TARJETA_EXISTENTE, null);
        }

        // Generar número de tarjeta único
        String numeroTarjeta = generarNumeroTarjetaUnico();
        
        //TODO cambiar los campos de la tarjeta para que se adapten tanto al validator como al esquema de la BD
        //PD Comentado por Hugo
        Document nuevaTarjeta = new Document()
                .append("Num_tarjeta", numeroTarjeta)
                .append("nombreCompleto", nombre + " " + apellidos)
                .append("tipoDocumento", tipoDocumento)
                .append("numeroDocumento", numeroDocumento)
                .append("IBAN", iban)
                .append("fechaNacimiento", fechaNacimiento)
                .append("direccion", direccion)
                .append("telefono", telefono)
                .append("tipoTarjeta", "Debito")
                .append("estado", "Activa");

        db.insertDocument(Collections.TARJETA, nuevaTarjeta);

        // Verificar inserción
        List<Document> listaTarjetas = db.readDocument(docLeerTarjeta, Collections.TARJETA);
        if (listaTarjetas.isEmpty()) {
            return new ResultContext(Evento.CREAR_TARJETA_ERROR_DB, null);
        }

        // Pasamos la tarjeta con el número generado de vuelta en el ResultContext
        return new ResultContext(Evento.CREAR_TARJETA_OK, listaTarjetas.get(0));
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
        telefono = telefono.trim();        
        if (telefono.length() != 9 || !telefono.matches("[0-9]+")) {
            return false;
        }
        
        String regexTelefono = "^[0-9]{9}$";
        return telefono.matches(regexTelefono);
    }

    private boolean validarSoloAlfabeticos(String cadena) {
        String regex = "^[A-Za-z]+$";
        return cadena.matches(regex);
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