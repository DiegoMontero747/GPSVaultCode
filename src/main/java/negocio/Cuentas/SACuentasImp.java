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
        String tipoDoc = cuenta.getTipoDoc();
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

        if (!validarDNI(tipoDoc)) {
            return new ResultContext(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, null);
        }
        
        if (!validarNIE(tipoDoc)) {
            return new ResultContext(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, null);
        }

        Document nuevaCuenta = new Document()
            .append("nombreCompleto", nombre + " " + apellidos)
            .append("dni", tipoDoc)
            .append("direccion", direccion)
            .append("telefono", telefono)
            .append("estado", "Activa");

        db.insertDocument(Collections.CUENTABANC, nuevaCuenta);

        // verificamos que la cuenta fue insertada correctamente
        Document docLeerCuenta = new Document()
        		.append("tipoDoc", nuevaCuenta.getString("tipoDoc"));
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
}
