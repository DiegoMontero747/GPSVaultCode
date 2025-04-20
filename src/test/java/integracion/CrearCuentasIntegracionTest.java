package integracion;

import static org.junit.Assert.assertEquals;

import org.bson.Document;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import integracion.bbdd.Collections;
import integracion.bbdd.MongoDBManager;
import negocio.Cuentas.SACuentasImp;
import negocio.Cuentas.TCuenta;
import negocio.Factory.ResultContext;
import presentacion.Controller.Evento;

public class CrearCuentasIntegracionTest {

    private SACuentasImp saCuentas;
    private MongoDBManager db;
    private TCuenta cuenta;

    @Before
    public void setUp() {
        saCuentas = new SACuentasImp();
        db = MongoDBManager.getInstance();

        // Aseguramos que no exista el documento antes del test
        db.deleteDocument(Collections.CUENTABANC, new Document("Titular", "12345678A"));
        db.deleteDocument(Collections.CLIENTE, new Document("Nombre", "Juan").append("Apellidos", "Pérez"));

        cuenta = new TCuenta();
        cuenta.setNombre("Juan");
        cuenta.setApellidos("Pérez");
        cuenta.setDNI("12345678A");
        cuenta.setDireccion("Calle Falsa 123");
        cuenta.setTelefono("600123456");
        cuenta.setTipoDoc("DNI");
    }

    @After
    public void tearDown() {
        db.deleteDocument(Collections.CUENTABANC, new Document("Titular", "12345678A"));
        db.deleteDocument(Collections.CLIENTE, new Document("Nombre", "Juan").append("Apellidos", "Pérez"));
    }

    @Test
    public void testCrearCuentaBancaria_OK() {
        ResultContext resultado = saCuentas.crearCuentaBancaria(cuenta);
        assertEquals(Evento.CREAR_CUENTA_BANCARIA_OK, resultado.getEvento());
    }

    @Test
    public void testCrearCuentaBancaria_DatosNulos() {
        TCuenta cuentaNula = new TCuenta();
        ResultContext resultado = saCuentas.crearCuentaBancaria(cuentaNula);
        assertEquals(Evento.CREAR_CUENTA_BANCARIA_ERROR_DATOS_NULOS, resultado.getEvento());
    }

    @Test
    public void testCrearCuentaBancaria_TelefonoIncorrecto() {
        cuenta.setTelefono("60012345678"); // Teléfono con demasiados dígitos
        ResultContext resultado = saCuentas.crearCuentaBancaria(cuenta);
        assertEquals(Evento.CREAR_CUENTA_BANCARIA_ERROR_TEL_INCORRECTO, resultado.getEvento());
    }

    @Test
    public void testCrearCuentaBancaria_DNIIncorrecto() {
        cuenta.setDNI("12345"); // DNI no válido
        ResultContext resultado = saCuentas.crearCuentaBancaria(cuenta);
        assertEquals(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, resultado.getEvento());
    }
    
    @Test
    public void testCrearCuentaBancaria_NIEIncorrecto() {
        cuenta.setTipoDoc("NIE");
        cuenta.setDNI("12345"); // NIE no válido
        ResultContext resultado = saCuentas.crearCuentaBancaria(cuenta);
        assertEquals(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, resultado.getEvento());
    }

    @Test
    public void testCrearCuentaBancaria_NombreNoAlfabetica() {
        cuenta.setNombre("Juan1"); // Nombre con número
        ResultContext resultado = saCuentas.crearCuentaBancaria(cuenta);
        assertEquals(Evento.ERROR_CADENA_NO_ALFABETICA, resultado.getEvento());
    }
    
    @Test
    public void testCrearCuentaBancaria_ApellidoNoAlfabetico() {
        cuenta.setNombre("Pérez1"); // Nombre con número
        ResultContext resultado = saCuentas.crearCuentaBancaria(cuenta);
        assertEquals(Evento.ERROR_CADENA_NO_ALFABETICA, resultado.getEvento());
    }
}