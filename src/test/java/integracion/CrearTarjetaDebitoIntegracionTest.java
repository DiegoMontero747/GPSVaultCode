package integracion;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import integracion.bbdd.Collections;
import integracion.bbdd.MongoDBManager;
import negocio.Factory.ResultContext;
import negocio.ManejoSesiones.TSesion;
import negocio.Tarjetas.SATarjetasImp;
import negocio.Tarjetas.TTarjeta;
import presentacion.Controller.Evento;


public class CrearTarjetaDebitoIntegracionTest {
	private SATarjetasImp saTarjetasImp;
	private MongoDBManager db;


    private TTarjeta tarjeta;
    
    @Before
    public void set_up() {
    	saTarjetasImp = new SATarjetasImp();
        db = MongoDBManager.getInstance();
        Document doc = new Document("nombreCompleto", "Juan Pérez")
                .append("tipoDocumento", "DNI")
                .append("numeroDocumento", "12345678A")
                .append("numeroCuenta", "ES1234567890123456789012");
        //tenemos que eliminar los usuarios de la bd que vayamos a crear si existen y luego volver a crearlos
        db.deleteDocument(Collections.TARJETA, doc);
    }
    
    @After
    public void clean_up() {
    	Document doc = new Document("nombreCompleto", "Juan Pérez")
                .append("tipoDocumento", "DNI")
                .append("numeroDocumento", "12345678A")
                .append("numeroCuenta", "ES1234567890123456789012");
    	db.deleteDocument(Collections.TARJETA, doc);
    }
    
    @Test
    public void testCrearTarjetaDebito_Ok() {
        //cuenta bancaria existente en la BBDD
        Document doc = new Document("nombreCompleto", "Juan Pérez")
                .append("tipoDocumento", "DNI")
                .append("numeroDocumento", "12345678A")
                .append("numeroCuenta", "ES1234567890123456789012");

        
        db.insertDocument(Collections.TARJETA, doc);

        tarjeta.setNombreCompleto("Juan Pérez");
        tarjeta.setTipoDocumento("DNI");
        tarjeta.setNumeroDocumento("12345678A");
        tarjeta.setNumeroCuenta("ES1234567890123456789012");

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_OK, result.getEvento());
    }

    @Test
    public void testCrearTarjetaDebito_CuentaNoExiste() {
        

        tarjeta.setNombreCompleto("Juan Pérez");
        tarjeta.setTipoDocumento("DNI");
        tarjeta.setNumeroDocumento("12345678A");
        tarjeta.setNumeroCuenta("ES1234567890123456789012");

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE, result.getEvento());
    }

    @Test
    public void testCrearTarjetaDebito_DatosIncompletos() {
        tarjeta.setNombreCompleto("");
        tarjeta.setTipoDocumento("");
        tarjeta.setNumeroDocumento("");
        tarjeta.setNumeroCuenta("");

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS, result.getEvento());

        tarjeta.setNombreCompleto(null);
        tarjeta.setNumeroDocumento(null);
        tarjeta.setNumeroCuenta(null);

        result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS, result.getEvento());
    }


    @Test
    public void testCrearTarjetaDebito_NombreNulo() {
        tarjeta.setNombreCompleto(null);
        tarjeta.setTipoDocumento("DNI");
        tarjeta.setNumeroDocumento("12345678A");
        tarjeta.setNumeroCuenta("ES1234567890123456789012");

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS, result.getEvento());
    }

    @Test
    public void testCrearTarjetaDebito_TipoDocumentoInvalido() {
        tarjeta.setNombreCompleto("Juan Pérez");
        tarjeta.setTipoDocumento("DNI");
        tarjeta.setNumeroDocumento("12345"); //dni no valido
        tarjeta.setNumeroCuenta("ES1234567890123456789012");

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);
        assertEquals(Evento.CREAR_TARJETA_ERROR_TIPO_DOCUMENTO_INVALIDO, result.getEvento());

        tarjeta.setTipoDocumento("NIE");
        tarjeta.setNumeroDocumento("X12345");  // nie no valido
        tarjeta.setNumeroCuenta("ES1234567890123456789012");

        result = saTarjetasImp.crearTarjetaDebito(tarjeta);
        assertEquals(Evento.CREAR_TARJETA_ERROR_TIPO_DOCUMENTO_INVALIDO, result.getEvento());
    }


    
}
