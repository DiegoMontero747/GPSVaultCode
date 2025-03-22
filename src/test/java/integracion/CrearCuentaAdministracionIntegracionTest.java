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
        db.deleteDocument(Collections.PERFIL, new Document("nombre", "Mario")
                .append("contrasenya", "mario64")
                .append("rol", "administracion")
                .append("usuario", "Champinyon")
                .append("dni", "12345678P")
                .append("apellido", "Bros"));
        
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
		
		ResultContext resultado = saCrearCuentaAdministracion.crearCuenta(datos_crearCuenta);
		assertEquals(Evento.CREAR_CUENTA_ADM_EXITO, resultado.getEvento());
	}
}
