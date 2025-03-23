package negocio;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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
import negocio.ManejoSesiones.SACrearCuentaAdministracionImp;
import negocio.ManejoSesiones.SAManejoSesionesImp;
import negocio.ManejoSesiones.TCrearCuentaAdm;
import negocio.ManejoSesiones.TSesion;
import presentacion.Controller.Evento;

public class SACrearCuentaAdministracionImpTest {
	
	 	@Mock
	    private MongoDBManager db; // Mock de MongoDBManager

	    @InjectMocks
	    private SACrearCuentaAdministracionImp saCrearCuentaAdministracion; // Inyectamos el mock en la clase bajo prueba

	    private TCrearCuentaAdm datos_crearCuenta;
	    
	    @Before
	    public void setUp() {
	        // Inicializar los mocks
	        MockitoAnnotations.openMocks(this);
	        datos_crearCuenta = new TCrearCuentaAdm();
	        
	    }
	    
	    @Test
		public void crearCuentaAdm_OK() {
	    	
	    	
			datos_crearCuenta.setNombre("Mario");
			datos_crearCuenta.setApellido("Bros");
			datos_crearCuenta.setDni("12345678P");
			datos_crearCuenta.setRol("administracion");
			datos_crearCuenta.setUsuario("Champinyon");
			datos_crearCuenta.setConstrasenya("mario64");
			
			//perfil que vamos a insertar
			Document nuevoperfil = new Document();
			nuevoperfil.append("nombre",datos_crearCuenta.getNombre());
			nuevoperfil.append("apellido",datos_crearCuenta.getApellido());
			nuevoperfil.append("rol",datos_crearCuenta.getRol());
			nuevoperfil.append("contrasenya",datos_crearCuenta.getConstrasenya());
			nuevoperfil.append("usuario",datos_crearCuenta.getUsuario());
			nuevoperfil.append("dni",datos_crearCuenta.getDni());
			
			
			
			//usuario aleatorio que se va a devolver en la lista al leer de la BD, no coincide con el usuario que vamos
			//a introducir
			Document usuario1 = new Document();
			usuario1.append("nombre","usuario1_nombre");
			usuario1.append("apellido","usuario1apellido");
			usuario1.append("rol","pasivo");
			usuario1.append("contrasenya","usuario1_contrasenya");
			usuario1.append("usuario","usuario1");
			usuario1.append("dni","02423121U");
			
			List<Document> lista_mock = new ArrayList<>();
			lista_mock.add(usuario1);
			when(db.getAllDocuments(Collections.PERFIL)).thenReturn(lista_mock);
			doNothing().when(db).insertDocument(Collections.PERFIL, nuevoperfil);
			
			ResultContext resultado = saCrearCuentaAdministracion.crearCuenta(datos_crearCuenta);
			
			assertEquals(Evento.CREAR_CUENTA_ADM_EXITO, resultado.getEvento());
		}
		@Test
		public void crearCuentaAdm_DNI_Erroneo() {
			datos_crearCuenta.setNombre("Mario");
			datos_crearCuenta.setApellido("Bros");
			datos_crearCuenta.setDni("1234567P");
			datos_crearCuenta.setRol("administracion");
			datos_crearCuenta.setUsuario("Champinyon");
			datos_crearCuenta.setConstrasenya("mario64");
			
			//perfil que vamos a insertar
			Document nuevoperfil = new Document();
			nuevoperfil.append("nombre",datos_crearCuenta.getNombre());
			nuevoperfil.append("apellido",datos_crearCuenta.getApellido());
			nuevoperfil.append("rol",datos_crearCuenta.getRol());
			nuevoperfil.append("contrasenya",datos_crearCuenta.getConstrasenya());
			nuevoperfil.append("usuario",datos_crearCuenta.getUsuario());
			nuevoperfil.append("dni",datos_crearCuenta.getDni());
			
			//usuario aleatorio que se va a devolver en la lista al leer de la BD, no coincide con el usuario que vamos
			//a introducir
			Document usuario1 = new Document();
			usuario1.append("nombre","usuario1_nombre");
			usuario1.append("apellido","usuario1apellido");
			usuario1.append("rol","pasivo");
			usuario1.append("contrasenya","usuario1_contrasenya");
			usuario1.append("usuario","usuario1");
			usuario1.append("dni","02423121U");
			
			List<Document> lista_mock = new ArrayList<>();
			lista_mock.add(usuario1);
			when(db.getAllDocuments(Collections.PERFIL)).thenReturn(lista_mock);
			doNothing().when(db).insertDocument(Collections.PERFIL, nuevoperfil);
			
			ResultContext resultado = saCrearCuentaAdministracion.crearCuenta(datos_crearCuenta);
			assertEquals(Evento.CREAR_CUENTA_ADM_ERROR_FORMATO_DNI, resultado.getEvento());
		}
		@Test
		public void crearCuentaAdm_DNI_Existente() {
		

			datos_crearCuenta.setNombre("Mario");
			datos_crearCuenta.setApellido("Bros");
			datos_crearCuenta.setDni("12345678P");
			datos_crearCuenta.setRol("administracion");
			datos_crearCuenta.setUsuario("Champinyon");
			datos_crearCuenta.setConstrasenya("mario64");
			
			//perfil que vamos a insertar
			Document nuevoperfil = new Document();
			nuevoperfil.append("nombre",datos_crearCuenta.getNombre());
			nuevoperfil.append("apellido",datos_crearCuenta.getApellido());
			nuevoperfil.append("rol",datos_crearCuenta.getRol());
			nuevoperfil.append("contrasenya",datos_crearCuenta.getConstrasenya());
			nuevoperfil.append("usuario",datos_crearCuenta.getUsuario());
			nuevoperfil.append("dni",datos_crearCuenta.getDni());
			
			//usuario que se va a devolver en la lista al leer de la BD, en este caso coincide con el usuario que 
			//vamos a introducir
			Document usuario1 = new Document();
			usuario1.append("nombre","usuario1_nombre");
			usuario1.append("apellido","usuario1apellido");
			usuario1.append("rol","pasivo");
			usuario1.append("contrasenya","usuario1_contrasenya");
			usuario1.append("usuario","usuario1");
			usuario1.append("dni","12345678P");
			
			List<Document> lista_mock = new ArrayList<>();
			lista_mock.add(usuario1);
			when(db.getAllDocuments(Collections.PERFIL)).thenReturn(lista_mock);
			doNothing().when(db).insertDocument(Collections.PERFIL, nuevoperfil);
			
			ResultContext resultado = saCrearCuentaAdministracion.crearCuenta(datos_crearCuenta);
			assertEquals(Evento.CREAR_CUENTA_ADM_ERROR_DNI_ENCONTRADO, resultado.getEvento());
		}
		
		@Test
		public void crearCuentaAdm_ROL_Incorrecto() {
			//creamos el personal primero
			 
			
			
			
			datos_crearCuenta.setNombre("Mario");
			datos_crearCuenta.setApellido("Bros");
			datos_crearCuenta.setDni("12345678P");
			datos_crearCuenta.setRol("lider");
			datos_crearCuenta.setUsuario("Champinyon");
			datos_crearCuenta.setConstrasenya("mario64");
			
			//perfil que vamos a insertar
			Document nuevoperfil = new Document();
			nuevoperfil.append("nombre",datos_crearCuenta.getNombre());
			nuevoperfil.append("apellido",datos_crearCuenta.getApellido());
			nuevoperfil.append("rol",datos_crearCuenta.getRol());
			nuevoperfil.append("contrasenya",datos_crearCuenta.getConstrasenya());
			nuevoperfil.append("usuario",datos_crearCuenta.getUsuario());
			nuevoperfil.append("dni",datos_crearCuenta.getDni());
			
			//usuario que se va a devolver en la lista al leer de la BD, en este caso coincide con el usuario que 
			//vamos a introducir
			Document usuario1 = new Document();
			usuario1.append("nombre","usuario1_nombre");
			usuario1.append("apellido","usuario1apellido");
			usuario1.append("rol","pasivo");
			usuario1.append("contrasenya","usuario1_contrasenya");
			usuario1.append("usuario","usuario1");
			usuario1.append("dni","49382323C");
			
			List<Document> lista_mock = new ArrayList<>();
			lista_mock.add(usuario1);
			when(db.getAllDocuments(Collections.PERFIL)).thenReturn(lista_mock);
			doNothing().when(db).insertDocument(Collections.PERFIL, nuevoperfil);
			
			ResultContext resultado = saCrearCuentaAdministracion.crearCuenta(datos_crearCuenta);
			assertEquals(Evento.CREAR_CUENTA_ADM_ERROR_ROL_INCORRECTO, resultado.getEvento());
		}
}
