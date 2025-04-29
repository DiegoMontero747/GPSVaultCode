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
        Document doc = new Document("Nombre", "Juan")
        		.append("Apellidos", "Perez")
                .append("DNI/NIE", "12345678A");
        Document doc2 = new Document()
        		.append("DNI/NIE", "12345678A")
                .append("IBAN", "ES1234567890123456789012");
        //tenemos que eliminar los usuarios de la bd que vayamos a crear si existen y luego volver a crearlos
        db.deleteDocument(Collections.CUENTABANC, doc2);
        db.deleteDocument(Collections.CLIENTE, doc);
        tarjeta = new TTarjeta();
    }
    
    @After
    public void clean_up() {
    	Document doc = new Document("Nombre", "Juan")
        		.append("Apellidos", "Perez")
                .append("DNI/NIE", "12345678A");
    	db.deleteDocument(Collections.CLIENTE, doc);
    	Document doc2 = new Document("Titular", "Juan")
                .append("DNI/NIE", "12345678A")
                .append("IBAN", "ES1234567890123456789012");
		db.deleteDocument(Collections.CUENTABANC, doc2);
    }
    
    @Test
    public void testCrearTarjetaDebito_Ok() {
        //cuenta bancaria existente en la BBDD
        Document doc = new Document("Nombre", "Juan")
        		.append("Apellidos", "Perez")
                .append("DNI/NIE", "12345678A");
        Document doc3 = new Document("Titular", "Juan")
                .append("DNI/NIE", "12345678A")
                .append("IBAN", "ES1234567890123456789012");

        
        db.insertDocument(Collections.CUENTABANC, doc3);
        db.insertDocument(Collections.CLIENTE, doc);

        tarjeta.setDocCliente("12345678A"); // documento cliente
        tarjeta.setIban("ES1234567890123456789012"); // IBAN

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);
        db.deleteDocument(Collections.TARJETA, new Document("Num_tarjeta", result.getDato().toString()));
        assertEquals(Evento.CREAR_TARJETA_OK, result.getEvento());
    }

    @Test
    public void testCrearTarjetaDebito_CuentaNoExiste() {
    	
        Document doc = new Document("Nombre", "Juan")
        		.append("Apellidos", "Perez")
                .append("DNI/NIE", "12345678A");
        
        db.insertDocument(Collections.CLIENTE, doc);
        

    	   tarjeta.setDocCliente("12345678A"); // documento cliente
           tarjeta.setIban("ES1234567890123456780000"); // IBAN

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);
        assertEquals(Evento.CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE, result.getEvento());
    }

    @Test
    public void testCrearTarjetaDebito_DatosIncompletos() {
    	   tarjeta.setDocCliente(""); // documento cliente
           tarjeta.setIban(""); // IBAN

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS, result.getEvento());

        tarjeta.setDocCliente(null); // documento cliente
        tarjeta.setIban(null); // IBAN

        result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_ERROR_DATOS_NULOS, result.getEvento());
    }


    @Test
    public void testCrearTarjetaDebito_NombreNulo() {
    	   tarjeta.setDocCliente(null); // documento cliente
           tarjeta.setIban("ES1234567890123456789012"); // IBAN

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_ERROR_DATOS_NULOS, result.getEvento());
    }

    @Test
    public void testCrearTarjetaDebito_TipoDocumentoInvalido() {
    	   tarjeta.setDocCliente("12345678AAAAA"); // documento cliente
           tarjeta.setIban("ES1234567890123456789012"); // IBAN
        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);
        assertEquals(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, result.getEvento());

        tarjeta.setDocCliente("X12345CCC"); // documento cliente
        tarjeta.setIban("ES1234567890123456789012"); // IBAN
   
        result = saTarjetasImp.crearTarjetaDebito(tarjeta);
        assertEquals(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, result.getEvento());
    }


    
}
