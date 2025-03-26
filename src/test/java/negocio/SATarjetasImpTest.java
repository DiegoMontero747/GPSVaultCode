package negocio;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

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
        //cuenta bancaria existente en la BBDD
        Document doc = new Document("nombreCompleto", "Juan Pérez")
                .append("tipoDocumento", "DNI")
                .append("numeroDocumento", "12345678A")
                .append("numeroCuenta", "ES1234567890123456789012");

        when(db.getDocumentByNombre(Collections.CUENTABANC, "12345678A")).thenReturn(doc);

        tarjeta.setNombreCompleto("Juan Pérez");
        tarjeta.setTipoDocumento("DNI");
        tarjeta.setNumeroDocumento("12345678A");
        tarjeta.setNumeroCuenta("ES1234567890123456789012");

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_OK, result.getEvento());
    }

    @Test
    public void testCrearTarjetaDebito_CuentaNoExiste() {
        when(db.getDocumentByNombre(Collections.CUENTABANC, "12345678A")).thenReturn(null);

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


    @Test
    public void testCrearTarjetaDebito_DBError() {
        doThrow(new RuntimeException("Error en la BD"))
            .when(db).insertDocument(eq(Collections.TARJETA), any(Document.class));

        tarjeta.setNombreCompleto("Juan Pérez");
        tarjeta.setTipoDocumento("DNI");
        tarjeta.setNumeroDocumento("12345678A");
        tarjeta.setNumeroCuenta("ES1234567890123456789012");

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_ERROR_DB, result.getEvento());
    }
    
    @Test
    public void testCrearTarjetaDebito_TarjetaNoInsertada() {
        // Simular un fallo en la insert BBDD aunque no haya un error
        when(db.getDocumentByNombre(Collections.TARJETA, "12345678A")).thenReturn(null); // Simulamos que no se encuentra el documento después de la inserción

        tarjeta.setNombreCompleto("Juan Pérez");
        tarjeta.setTipoDocumento("DNI");
        tarjeta.setNumeroDocumento("12345678A");
        tarjeta.setNumeroCuenta("ES1234567890123456789012");

        ResultContext result = saTarjetasImp.crearTarjetaDebito(tarjeta);

        assertEquals(Evento.CREAR_TARJETA_ERROR_DB, result.getEvento());
    }
}
