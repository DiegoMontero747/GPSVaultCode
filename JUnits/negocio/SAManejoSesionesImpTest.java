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
import negocio.ManejoSesiones.SAManejoSesionesImp;
import negocio.ManejoSesiones.TSesion;
import presentacion.Controller.Evento;

public class SAManejoSesionesImpTest {

    @Mock
    private MongoDBManager db; // Mock de MongoDBManager

    @InjectMocks
    private SAManejoSesionesImp saManejoSesiones; // Inyectamos el mock en la clase bajo prueba

    private TSesion sesion;

    @Before
    public void setUp() {
        // Inicializar los mocks
        MockitoAnnotations.openMocks(this);
        sesion = new TSesion();
    }

    @Test
    public void testInicioSesion_UsuarioNoExiste() {
        // Configurar el mock
    	
        when(db.getDocumentByNombre(Collections.PERFIL, "usuario_inexistente")).thenReturn(null);

        // Configurar la sesión
        sesion.setUsername("usuario_inexistente");
        sesion.setPsswd("password");

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIAR_SESSION_ERROR_1, result.getEvento());
    }

    @Test
    public void testInicioSesion_ContrasenyaIncorrecta() {
        // Configurar el mock
        Document doc = new Document("username", "usuario_existente")
                .append("password", "password_correcta")
                .append("rol", "admin");
       
        when(db.getDocumentByNombre(Collections.PERFIL, "usuario_existente")).thenReturn(doc);

        // Configurar la sesión
        sesion.setUsername("usuario_existente");
        sesion.setPsswd("password_incorrecta");

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIAR_SESSION_KO_ERROR_2, result.getEvento());
    }

    @Test
    public void testInicioSesion_Ok() {
        // Configurar el mock
        Document doc = new Document("username", "usuario_existente")
                .append("password", "password_correcta")
                .append("rol", "admin");

        when(db.getDocumentByNombre(Collections.PERFIL, "usuario_existente")).thenReturn(doc);

        // Configurar la sesión
        sesion.setUsername("usuario_existente");
        sesion.setPsswd("password_correcta");

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIAR_SESSION_OK, result.getEvento());
        
    }

    @Test
    public void testInicioSesion_DatosIncompletos() {
        // Configurar la sesión sin username ni password
        sesion.setUsername("");
        sesion.setPsswd("");

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIAR_SESSION_KO_ERROR_3, result.getEvento());
    }
}