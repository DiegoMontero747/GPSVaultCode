package negocio.Cuentas;

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
        String telefono = cuenta.getTelefono();

        if (nombre == null || apellidos == null || tipoDoc == null || direccion == null || telefono == null)
            return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_ERROR_DATOS_NULOS, null);

        nombre = nombre.trim();
        apellidos = apellidos.trim();
        tipoDoc = tipoDoc.trim();
        direccion = direccion.trim();
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

        if (!validarDNI(tipoDoc) && !validarNIE(tipoDoc)) {
            return new ResultContext(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, null);
        }

        String numeroCuenta = generarIBANUnico();

        //TODO adaptar los campos del SA al esquema de la BD de MongoValidator
        Document nuevaCuenta = new Document()
            .append("nombre", nombre)
            .append("apellidos", apellidos)
            .append("dni", tipoDoc)
            .append("direccion", direccion)
            .append("telefono", telefono)
            .append("estado", "Activa")
            .append("IBAN", numeroCuenta);
        
        db.insertDocument(Collections.CUENTABANC, nuevaCuenta);

        // verificamos que la cuenta fue insertada correctamente
        Document docLeerCuenta = new Document()
        		.append("dni", nuevaCuenta.getString("dni"));
        List<Document> cuentas = db.readDocument(docLeerCuenta, Collections.CUENTABANC);
        if (cuentas.isEmpty()) {
            return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_ERROR_DB, null); // si no encontramos la cuenta ha fallado la insercion en la bbdd
        }
        return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_OK, cuentas.get(0)); // la cuenta fue creada exitosamente
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
        String regex = "^[A-Za-z]+$";
        return cadena.matches(regex);
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