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
import negocio.Factory.ResultContext;
import negocio.Tarjetas.SATarjetasImp;
import negocio.Tarjetas.TTarjeta;
import presentacion.Controller.Evento;

public class SATarjetasImpTest {

    @Mock
    private MongoDBManager db;
    @InjectMocks
    private SATarjetasImp saTarjetasImp;

    private TTarjeta tarjeta;
    
    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tarjeta = new TTarjeta();
    }

    @Test
    public void testCrearTarjetaDebito_Ok() {
        // Simular cuenta bancaria existente en la BBDD
        ArrayList<Document> listaCuentas = new ArrayList<>();
        Document doc = new Document("Nombre", "Juan")
        		.append("Apellidos", "Perez")
                .append("tipoDocumento", "DNI")
                .append("DNI/NIE", "12345678A")
                .append("IBAN", "ES1234567890123456789012");
        listaCuentas.add(doc);

        when(db.readDocument(new Document().append("numeroCuenta", "ES1234567890123456789012"), Collections.CUENTABANC))
                .thenReturn(listaCuentas);

        tarjeta.setDocCliente("12345678A"); // documento cliente
        tarjeta.setIban("ES1234567890123456789012"); // IBAN

        ArrayList<Document> listaTarjetas = new ArrayList<>();
        Document tarjetaInsertada = new Document()
        	    .append("docCliente", "12345678A") // Documento cliente
        	    .append("Num_tarjeta", "1234567812345678") // Número de tarjeta
        	    .append("IBAN", "ES1234567890123456789012") // IBAN
        	    .append("CVV", 123) // CVV
        	    .append("Caducidad", "01/30") // Fecha de caducidad
        	    .append("Tipo_tarjeta", "Débito") // Tipo de tarjeta
        	    .append("Estado", true); // Activa
        
        listaTarjetas.add(tarjetaInsertada);
        //primero devuelvo una lista vacia para simular que no existe la tarjeta y luego la lista con la tarjeta insertada
        when(db.readDocument(new Document().append("numeroDocumento", "12345678A"), Collections.TARJETA))
                .thenReturn(new ArrayList<Document>()).thenReturn(listaTarjetas);

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_OK, result.getEvento());
    }


    @Test
    public void testCrearTarjetaDebito_CuentaNoExiste() {
        
        when(db.readDocument(new Document().append("numeroDocumento", "12345678A"),Collections.CUENTABANC)).thenReturn(null);
        tarjeta.setDocCliente("12345678A"); // documento cliente
        tarjeta.setIban("ES1234567890123456000000"); // IBAN

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE, result.getEvento());
    }

    @Test
    public void testCrearTarjetaDebito_DatosIncompletos() {
    	tarjeta.setDocCliente(" "); // documento cliente
        tarjeta.setIban(" "); // IBAN
        

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS, result.getEvento());

        tarjeta.setDocCliente(null); // documento cliente
        tarjeta.setIban(null); // IBAN

        result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_ERROR_DATOS_NULOS, result.getEvento());
    }

    @Test
    public void testCrearTarjetaDebito_TipoDocumentoInvalido() {
    	tarjeta.setDocCliente("12345678AAAA"); // documento cliente
        tarjeta.setIban("ES1234567890123456789012"); // IBAN

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);
        assertEquals(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, result.getEvento());

        
        tarjeta.setDocCliente("X12345FGF");  // nie no valido
        tarjeta.setIban("ES1234567890123456789012");

        result = saTarjetasImp.crearTarjetaDebito(tarjeta);
        assertEquals(Evento.ERROR_TIPO_DOCUMENTO_INVALIDO, result.getEvento());
    }


    @Test
    public void testCrearTarjetaDebito_DBError() {
    	// el primer read document es correcto
    	ArrayList<Document> listaCuentas = new ArrayList<>();
    	 Document doc = new Document("Nombre", "Juan Pérez")
         		.append("Apellidos", "Perez")
                 .append("tipoDocumento", "DNI")
                 .append("DNI/NIE", "12345678A")
                 .append("IBAN", "ES1234567890123456789012");
        listaCuentas.add(doc);
		
		
        when(db.readDocument(new Document().append("IBAN", "ES1234567890123456789012"),Collections.CUENTABANC)).thenReturn(listaCuentas);
    	
        // Simular un error en la insercion de la tarjeta
       //devuelve una lista vacia cuando deberia devolver una lista con la tarjeta insertada
        when(db.readDocument(new Document().append("DNI/NIE", "12345678A"),Collections.TARJETA)).thenReturn(new ArrayList<Document>());

        tarjeta.setDocCliente("12345678A"); // documento cliente
        tarjeta.setIban("ES1234567890123456789012"); // IBAN

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_ERROR_DB, result.getEvento());
    }
    
    @Test
    public void testCrearTarjetaDebito_TarjetaNoInsertada() {
    	
    	// el primer read document es correcto
    	ArrayList<Document> listaCuentas = new ArrayList<>();
    	 Document doc = new Document("Nombre", "Juan Pérez")
         		.append("Apellidos", "Perez")
                 .append("tipoDocumento", "DNI")
                 .append("DNI/NIE", "12345678A")
                 .append("IBAN", "ES1234567890123456789012");
        listaCuentas.add(doc);
		
		
        when(db.readDocument(new Document().append("numeroCuenta", "ES1234567890123456789012"),Collections.CUENTABANC)).thenReturn(listaCuentas);
    	
        // Simular un fallo en la insert BBDD aunque no haya un error
    	 when(db.readDocument(new Document().append("numeroDocumento", "12345678A"),Collections.TARJETA)).thenReturn(new ArrayList<Document>());
       // Simulamos que no se encuentra el documento después de la inserción

    	 tarjeta.setDocCliente("12345678A"); // documento cliente
         tarjeta.setIban("ES1234567890123456789012"); // IBAN

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_ERROR_DB, result.getEvento());
    }
    @Test
    public void testCrearTarjetaDebito_MaximoTarjetasAlcanzado() {
		// Simular cuenta bancaria existente en la BBDD
		ArrayList<Document> listaCuentas = new ArrayList<>();
		 Document doc = new Document("Nombre", "Juan Pérez")
	        		.append("Apellidos", "Perez")
	                .append("tipoDocumento", "DNI")
	                .append("DNI/NIE", "12345678A")
	                .append("IBAN", "ES1234567890123456789012")
				.append("numTarjetas", 5); // Simulamos que ya tiene 5 tarjetas

		listaCuentas.add(doc);

		when(db.readDocument(new Document().append("numeroCuenta", "ES1234567890123456789012"), Collections.CUENTABANC))
				.thenReturn(listaCuentas);

		 tarjeta.setDocCliente("12345678A"); // documento cliente
	     tarjeta.setIban("ES1234567890123456789012"); // IBAN

		ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

		assertEquals(Evento.CREAR_TARJETA_ERROR_MAX_TARJETAS, result.getEvento());
	}
    @Test
    public void testCrearTarjetaDebito_TarjetaYaExistente() {
		// Simular cuenta bancaria existente en la BBDD
		ArrayList<Document> listaCuentas = new ArrayList<>();
		Document doc = new Document("nombreCompleto", "Juan Pérez")
				.append("tipoDocumento", "DNI")
				.append("numeroDocumento", "12345678A")
				.append("numeroCuenta", "ES1234567890123456789012");
		listaCuentas.add(doc);

		when(db.readDocument(new Document().append("numeroCuenta", "ES1234567890123456789012"), Collections.CUENTABANC))
				.thenReturn(listaCuentas);

		// Simular tarjeta ya existente
		ArrayList<Document> listaTarjetas = new ArrayList<>();
		Document tarjetaExistente = new Document()
				.append("nombreCompleto", "Juan Pérez")
				.append("tipoDocumento", "DNI")
				.append("numeroDocumento", "12345678A")
				.append("numeroCuenta", "ES1234567890123456789012")
				.append("tipoTarjeta", "Debito")
				.append("estado", "Activa");
		
		listaTarjetas.add(tarjetaExistente);

		when(db.readDocument(new Document().append("numeroDocumento", "12345678A"), Collections.TARJETA))
				.thenReturn(listaTarjetas);

		 tarjeta.setDocCliente("12345678A"); // documento cliente
	     tarjeta.setIban("ES1234567890123456789012"); // IBAN

		ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

		assertEquals(Evento.CREAR_TARJETA_ERROR_TARJETA_EXISTENTE, result.getEvento());
	}
}
