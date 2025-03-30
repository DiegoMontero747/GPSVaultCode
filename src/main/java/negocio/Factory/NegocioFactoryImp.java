package negocio.Factory;

import negocio.ManejoSesiones.SACrearCuentaAdministracion;
import negocio.ManejoSesiones.SACrearCuentaAdministracionImp;
import negocio.ManejoSesiones.SAManejoSesiones;
import negocio.ManejoSesiones.SAManejoSesionesImp;
import negocio.Tarjetas.*;

public class NegocioFactoryImp extends NegocioFactory {

    @Override
    public SAManejoSesiones crearSAManejoSesiones() {
        return new SAManejoSesionesImp();
    }
    
    public SATarjetas crearSATarjetas() {
        return new SATarjetasImp();
    }

	@Override
	public SACrearCuentaAdministracion crearSACrearCuentaAdministracion() {
		// TODO Auto-generated method stub
		return new SACrearCuentaAdministracionImp();
	}
    
    

}
