package negocio;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import integracion.bbdd.Collections;
import integracion.bbdd.MongoDBManager;
import negocio.Cuentas.SACuentasImp;
import negocio.Cuentas.TCuenta;
import negocio.Factory.ResultContext;
import presentacion.Controller.Evento;

public class SACuentasImpTest {

    @Mock
    private MongoDBManager db;
    @InjectMocks
    private SACuentasImp saCuentasImp;

    private TCuenta cuenta;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        cuenta = new TCuenta();
    }

    @Test
    public void testCrearCuentaBancaria_OK() {
        // Simulamos la cuenta bancaria a insertar
    	cuenta.setTipoDoc("DNI");
        cuenta.setNombre("Juan");
        cuenta.setApellidos("Perez");
        cuenta.setDNI("12345678A");
        cuenta.setDireccion("Calle Falsa 123");
        cuenta.setTelefono("600123456");
        cuenta.setCodPostal("28908");
        
        Document cliente = new Document().append("DNI/NIE", cuenta.getDNI()).append("Nombre", cuenta.getNombre())
				.append("Apellidos", cuenta.getApellidos()).append("Telefono", Integer.valueOf(cuenta.getTelefono())).append("Dir", cuenta.getDireccion())
				.append("Cod-postal", Integer.valueOf(cuenta.getCodPostal()));// Hay que modificar TCuenta para que lleve el codigo postal

        
        Document cuentaDoc = new Document()
        		.append("IBAN", generarIBANAleatorio())
        		.append("Titular", cuenta.getNombre())
				.append("Fondos", Float.valueOf(0))
				.append("DNI/NIE", cuenta.getDNI());
        ArrayList<Document> cuentaList = new ArrayList<>();
        cuentaList.add(cuentaDoc);

        // Devuelve la lista con el documento por lo que se ha insertado correctamente
        when(db.readDocument(new Document().append("dni", cuenta.getDNI()), Collections.CUENTABANC))
                .thenReturn(cuentaList); 

        ResultContext result = saCuentasImp.crearCuentaBancaria(cuenta);

        assertEquals(Evento.CREAR_CUENTA_BANCARIA_OK, result.getEvento());
    }

    @Test
    public void testCrearCuentaBancaria_CuentaNull() {
        ResultContext result = saCuentasImp.crearCuentaBancaria(null);
        assertEquals(Evento.CREAR_CUENTA_BANCARIA_ERROR_CUENTA_NULL, result.getEvento());
    }

    @Test
    public void testCrearCuentaBancaria_DatosNulos() {
        cuenta.setNombre(null);
        cuenta.setApellidos(null);
        cuenta.setDNI(null);
        cuenta.setDireccion(null);
        cuenta.setTelefono(null);

        ResultContext result = saCuentasImp.crearCuentaBancaria(cuenta);

        assertEquals(Evento.CREAR_CUENTA_BANCARIA_ERROR_DATOS_NULOS, result.getEvento());
    }

    @Test
    public void testCrearCuentaBancaria_DatosIncompletos() {
        cuenta.setNombre("");
        cuenta.setApellidos("");
        cuenta.setDNI("");
        cuenta.setDireccion("");
        cuenta.setTelefono("");

        ResultContext result = saCuentasImp.crearCuentaBancaria(cuenta);

        assertEquals(Evento.CREAR_CUENTA_BANCARIA_ERROR_DATOS_INCOMPLETOS, result.getEvento());
    }

    @Test
    public void testCrearCuentaBancaria_CadenaNoAlfabetica() {
        cuenta.setNombre("Juan1");
        cuenta.setApellidos("Pérez");
        cuenta.setDNI("12345678A");
        cuenta.setDireccion("Calle Falsa 123");
        cuenta.setTelefono("600123456");

        ResultContext result = saCuentasImp.crearCuentaBancaria(cuenta);
        assertEquals(Evento.ERROR_CADENA_NO_ALFABETICA, result.getEvento());

        cuenta.setNombre("Juan");
        cuenta.setApellidos("Pérez1");

        result = saCuentasImp.crearCuentaBancaria(cuenta);
        assertEquals(Evento.ERROR_CADENA_NO_ALFABETICA, result.getEvento());
    }

    @Test
    public void testCrearCuentaBancaria_TipoDocumentoInvalido() {
        cuenta.setNombre("Juan");
        cuenta.setApellidos("Perez");
        cuenta.setDNI("12345"); // DNI no válido
        cuenta.setDireccion("Calle Falsa 123");
        cuenta.setTelefono("600123456");

        ResultContext result = saCuentasImp.crearCuentaBancaria(cuenta);
        assertEquals(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, result.getEvento());
    }

    @Test
    public void testCrearCuentaBancaria_DBError() {
        // Simulamos un fallo en la base de datos (la cuenta no se inserta correctamente)
        cuenta.setNombre("Juan");
        cuenta.setApellidos("Pérez");
        cuenta.setDNI("12345678A");
        cuenta.setDireccion("Calle Falsa 123");
        cuenta.setTelefono("600123456");

        when(db.readDocument(new Document().append("dni", cuenta.getTipoDoc()), Collections.CUENTABANC))
                .thenReturn(new ArrayList<>()); // Cuenta no existe

        // Simulamos que el insert
    }
    
    private String generarIBANAleatorio() {
		StringBuilder sb = new StringBuilder("ES");
		for (int i = 0; i < 22; i++) {
			sb.append((int) (Math.random() * 10));
		}
		return sb.toString();
	}
    
}
