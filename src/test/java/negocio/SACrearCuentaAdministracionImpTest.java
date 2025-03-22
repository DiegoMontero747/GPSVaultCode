package negocio;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import integracion.bbdd.MongoDBManager;
import negocio.ManejoSesiones.SACrearCuentaAdministracionImp;
import negocio.ManejoSesiones.SAManejoSesionesImp;
import negocio.ManejoSesiones.TCrearCuentaAdm;
import negocio.ManejoSesiones.TSesion;

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
}
