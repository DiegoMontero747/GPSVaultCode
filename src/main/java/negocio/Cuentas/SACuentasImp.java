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
            return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_ERROR_CUENTA_NO_VALIDA, null);
        }

        String nombre = cuenta.getNombre();
        String apellidos = cuenta.getApellidos();
        String dni = cuenta.getDni();
        String direccion = cuenta.getDireccion();
        String telefono = cuenta.getTelefono();

        if (nombre == null || apellidos == null || dni == null || direccion == null || telefono == null)
            return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_ERROR_DATOS_NULOS, null);

        nombre = nombre.trim();
        apellidos = apellidos.trim();
        dni = dni.trim();
        direccion = direccion.trim();
        telefono = telefono.trim();

        if (nombre.isBlank() || apellidos.isBlank() || dni.isBlank() || direccion.isBlank() || telefono.isBlank()) {
            return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_ERROR_DATOS_INCOMPLETOS, null);
        }
        
        if (!validarSoloAlfabeticos(nombre)) {
            return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_ERROR_CADENA_NO_ALFABETICA, null);
        }

        if (!validarSoloAlfabeticos(apellidos)) {
            return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_ERROR_CADENA_NO_ALFABETICA, null);
        }

        if (!validarDNI(dni)) {
            return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_ERROR_TIPO_DOCUMENTO_INVALIDO, null);
        }

        Document nuevaCuenta = new Document()
            .append("nombreCompleto", nombre + " " + apellidos)
            .append("dni", dni)
            .append("direccion", direccion)
            .append("telefono", telefono)
            .append("estado", "Activa");

        db.insertDocument(Collections.CUENTABANC, nuevaCuenta);

        // Verificamos que la cuenta fue insertada correctamente
        Document docLeerCuenta = new Document().append("dni", nuevaCuenta.getString("dni"));
        List<Document> cuentas = db.readDocument(docLeerCuenta, Collections.CUENTABANC);
        if (cuentas.isEmpty()) {
            return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_ERROR_DB, null); // Si no encontramos la cuenta, ha fallado la inserción
        }

        return new ResultContext(Evento.CREAR_CUENTA_BANCARIA_OK, cuentas.get(0)); // La cuenta fue creada exitosamente
    }

    private boolean validarDNI(String dni) {
        String regexDNI = "^[0-9]{8}[A-Za-z]$";
        return dni.matches(regexDNI);
    }

    private boolean validarSoloAlfabeticos(String cadena) {
        String regex = "^[A-Za-z]+$";
        return cadena.matches(regex);
    }
}
