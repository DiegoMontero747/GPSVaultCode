package negocio.Factory;

import negocio.ManejoSesiones.SACrearCuentaAdministracion;
import negocio.ManejoSesiones.SACrearCuentaAdministracionImp;
import negocio.ManejoSesiones.SAManejoSesiones;
import negocio.ManejoSesiones.SAManejoSesionesImp;

public class NegocioFactoryImp extends NegocioFactory {

    @Override
    public SAManejoSesiones crearSAManejoSesiones() {
        return new SAManejoSesionesImp();
    }

	@Override
	public SACrearCuentaAdministracion crearSACrearCuentaAdministracion() {
		// TODO Auto-generated method stub
		return new SACrearCuentaAdministracionImp();
	}
    
    

}
