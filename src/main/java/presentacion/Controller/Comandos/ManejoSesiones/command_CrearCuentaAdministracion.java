package presentacion.Controller.Comandos.ManejoSesiones;

import negocio.Factory.NegocioFactory;
import negocio.Factory.ResultContext;
import negocio.ManejoSesiones.SACrearCuentaAdministracion;
import negocio.ManejoSesiones.TCrearCuentaAdm;
import presentacion.Controller.Command;
import presentacion.Controller.Context;

public class command_CrearCuentaAdministracion implements Command {

	@Override
	public Context execute(Object object) {
		
		TCrearCuentaAdm datos = (TCrearCuentaAdm) object;
		SACrearCuentaAdministracion sa = NegocioFactory.getInstance().crearSACrearCuentaAdministracion();
		
		ResultContext result = sa.crearCuenta(datos);
		
		
		
		return new Context(result.getEvento(), result.getDato());
	}
	
}
