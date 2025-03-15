package integracion;
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

public class InicioSesionIntegracionTest {

	private SAManejoSesionesImp saManejoSesiones;
	private MongoDBManager db;


    private TSesion sesion;

    @Before
    public void setUp() {
    	
    	saManejoSesiones = new SAManejoSesionesImp();
        db = MongoDBManager.getInstance();
        //tenemos que eliminar los usuarios de la bd que vayamos a crear si existen y luego volver a crearlos
        db.deleteDocument(Collections.PERFIL, new Document("nombre", "usuarioexistente")
                .append("password", "passwordCorrecta")
                .append("rol", "admin"));
        
        db.insertDocument(Collections.PERFIL, new Document("nombre", "usuarioexistente")
                .append("password", "passwordCorrecta")
                .append("rol", "admin"));
        sesion = new TSesion();
    }

    @Test
    public void testInicioSesion_Ok() {
        // Configurar la sesión
        sesion.setUsername("usuarioexistente");
        sesion.setPsswd("passwordCorrecta");

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIO_SESION_OK, result.getEvento());
    }

    @Test
    public void testInicioSesion_UsuarioNoExiste() {
        // Configurar la sesión
        sesion.setUsername("usuarioinexistente");
        sesion.setPsswd("password");

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIO_SESION_ERROR_USUARIO_INEXISTENTE, result.getEvento());
    }

    @Test
    public void testInicioSesion_ContrasenyaIncorrecta() {
        // Configurar la sesión
        sesion.setUsername("usuarioexistente");
        sesion.setPsswd("password_incorrecta");

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIO_SESION_ERROR_CONTRASENYA_INCORRECTA, result.getEvento());
    }

    @Test
    public void testInicioSesion_DatosIncompletos() {
        // Configurar la sesión sin username ni password
        sesion.setUsername("");
        sesion.setPsswd("");

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIO_SESION_ERROR_USUARIO_INCOMPLETO, result.getEvento());
    }
    
    @Test
    public void testInicioSesion_UsernameIncompleto() {
        // Configurar la sesión sin username
        sesion.setUsername("");
        sesion.setPsswd("passwordCorrecta");

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIO_SESION_ERROR_USUARIO_INCOMPLETO, result.getEvento());
    }
    
    @Test
    public void testInicioSesion_ContrasenyaIncompleta() {
        // Configurar la sesión sin password
        sesion.setUsername("usuarioexistente");
        sesion.setPsswd("");

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIO_SESION_ERROR_CONTRASENYA_INCOMPLETA, result.getEvento());
    }
    
    @Test
    public void testInicioSesion_DatosNulos() {
        // Configurar la sesión con username y password nulos
        sesion.setUsername(null);
        sesion.setPsswd(null);

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIO_SESION_ERROR_USUARIO_INCOMPLETO, result.getEvento());
    }
    
    @Test
    public void testInicioSesion_UsernameNulo() {
        // Configurar la sesión con username nulo
        sesion.setUsername(null);
        sesion.setPsswd("passwordCorrecta");

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIO_SESION_ERROR_USUARIO_INCOMPLETO, result.getEvento());
    }
    
    @Test
    public void testInicioSesion_ContrasenyaNula() {
        // Configurar la sesión con password nula
        sesion.setUsername("usuarioexistente");
        sesion.setPsswd(null);

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIO_SESION_ERROR_CONTRASENYA_INCOMPLETA, result.getEvento());
    }
    
    @Test
    public void testInicioSesion_DatosSoloEspacios() {
        // Configurar la sesión con username y password nulos
        sesion.setUsername("     ");
        sesion.setPsswd("    ");

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIO_SESION_ERROR_USUARIO_INCOMPLETO, result.getEvento());
    }
    
    @Test
    public void testInicioSesion_UsernameSoloEspacios() {
        // Configurar la sesión con username nulo
        sesion.setUsername("     ");
        sesion.setPsswd("passwordCorrecta");

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIO_SESION_ERROR_USUARIO_INCOMPLETO, result.getEvento());
    }
    
    @Test
    public void testInicioSesion_ContrasenyaSoloEspacios() {
        // Configurar la sesión con password nula
        sesion.setUsername("usuarioexistente");
        sesion.setPsswd("    ");

        // Ejecutar el método bajo prueba
        ResultContext result = saManejoSesiones.inicioSesion(sesion);

        // Verificar el resultado
        assertEquals(Evento.INICIO_SESION_ERROR_CONTRASENYA_INCOMPLETA, result.getEvento());
    }
}