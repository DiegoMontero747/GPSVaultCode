package presentacion.Controller.Comandos.ManejoSesiones;

import negocio.Cuentas.SACuentas;
import negocio.Cuentas.TCuenta;
import negocio.Factory.NegocioFactory;
import negocio.Factory.ResultContext;
import presentacion.Controller.Command;
import presentacion.Controller.Context;

public class command_CrearCuentaCliente implements Command {
	@Override
	public Context execute(Object object) {
		
		TCuenta datos = (TCuenta) object;
		SACuentas sa = NegocioFactory.getInstance().crearSACuentas();
		
		ResultContext result = sa.crearCuentaBancaria(datos);
		
		
		
		return new Context(result.getEvento(), result.getDato());
	}
}
