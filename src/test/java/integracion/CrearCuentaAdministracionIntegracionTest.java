package integracion;

import static org.junit.Assert.assertEquals;

import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import integracion.bbdd.Collections;
import integracion.bbdd.MongoDBManager;
import negocio.Factory.ResultContext;
import negocio.ManejoSesiones.SACrearCuentaAdministracionImp;
import negocio.ManejoSesiones.SAManejoSesionesImp;
import negocio.ManejoSesiones.TCrearCuentaAdm;
import negocio.ManejoSesiones.TSesion;
import presentacion.Controller.Evento;


public class CrearCuentaAdministracionIntegracionTest {
	private SACrearCuentaAdministracionImp saCrearCuentaAdministracion;
	private MongoDBManager db;


	private TCrearCuentaAdm datos_crearCuenta;
	
	//BeforeClass se ejecuta una vez antes de todos los test
	@Before
    public void setUp() {
    	
		saCrearCuentaAdministracion = new SACrearCuentaAdministracionImp();
        db = MongoDBManager.getInstance();
        //tenemos que eliminar los usuarios de la bd que vayamos a crear si existen
        db.deleteDocument(Collections.PERFIL, new Document("Nombre", "Mario")
        		.append("Apellidos", "Bros")
        		.append("DNI", "12345678P")         
                .append("Rol", "administracion")
                .append("Nombre_usuario", "Champinyon")
                .append("Contrasenya", "mario641241241")
        		.append("Telefono", 123456789));
        
        datos_crearCuenta = new TCrearCuentaAdm();
    }
	
	@After
    public void tearDown() {
		db.deleteDocument(Collections.PERFIL, new Document("DNI", "12345678P"));
    }
	
	@Test
	public void crearCuentaAdm_OK() {
		datos_crearCuenta.setNombre("Mario");
		datos_crearCuenta.setApellido("Bros");
		datos_crearCuenta.setDni("12345678P");
		datos_crearCuenta.setRol("administracion");
		datos_crearCuenta.setUsuario("Champinyon");
		datos_crearCuenta.setConstrasenya("mario641241241");
		datos_crearCuenta.setTelf("123456789");
		
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
		datos_crearCuenta.setConstrasenya("mario641241241");
		datos_crearCuenta.setTelf("123456789");
		
		ResultContext resultado = saCrearCuentaAdministracion.crearCuenta(datos_crearCuenta);
		assertEquals(Evento.CREAR_CUENTA_ADM_ERROR_FORMATO_DNI, resultado.getEvento());
	}
	@Test
	public void crearCuentaAdm_DNI_Existente() {
		//creamos el personal primero
		 db.insertDocument(Collections.PERFIL, new Document("Nombre", "Mario")
	                .append("Contrasenya", "mario641241241")
	                .append("Rol", "administracion")
	                .append("Nombre_usuario", "Champinyon")
	                .append("DNI", "12345678P")
	                .append("Apellidos", "Bros"));
		
		
		
		datos_crearCuenta.setNombre("Mario");
		datos_crearCuenta.setApellido("Bros");
		datos_crearCuenta.setDni("12345678P");
		datos_crearCuenta.setRol("administracion");
		datos_crearCuenta.setUsuario("Champinyon");
		datos_crearCuenta.setConstrasenya("mario641241241");
		datos_crearCuenta.setTelf("123456789");
		
		ResultContext resultado = saCrearCuentaAdministracion.crearCuenta(datos_crearCuenta);
		assertEquals(Evento.CREAR_CUENTA_ADM_ERROR_DNI_ENCONTRADO, resultado.getEvento());
	}
	
	@Test
	public void crearCuentaAdm_Nulos() {
		ResultContext resultado = saCrearCuentaAdministracion.crearCuenta(datos_crearCuenta);
		assertEquals(Evento.CREAR_CUENTA_ADM_ERROR_DATOS_NULOS, resultado.getEvento());
	}
	
	@Test
	public void crearCuentaAdm_Telef_Erroneo() {
		datos_crearCuenta.setNombre("Mario");
		datos_crearCuenta.setApellido("Bros");
		datos_crearCuenta.setDni("12345678P");
		datos_crearCuenta.setRol("administracion");
		datos_crearCuenta.setUsuario("Champinyon");
		datos_crearCuenta.setConstrasenya("mario641241241");
		datos_crearCuenta.setTelf("1234567890");
		
		ResultContext resultado = saCrearCuentaAdministracion.crearCuenta(datos_crearCuenta);
		assertEquals(Evento.CREAR_CUENTA_ADM_ERROR_TEL_INCORRECTO, resultado.getEvento());
	
	}
}
