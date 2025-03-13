package presentacion.Controller.Comandos.ManejoSesiones;

import negocio.Factory.NegocioFactory;
import negocio.Factory.ResultContext;
import negocio.ManejoSesiones.TSesion;
import presentacion.Controller.Command;
import presentacion.Controller.Context;
import presentacion.Controller.Evento;

public class IniciaCuenta implements Command{
	@Override
	public Context execute(Object object) {
		ResultContext rc;
		NegocioFactory nf = NegocioFactory.getInstance();
		rc = nf.crearSAManejoSesiones().inicioSesion((TSesion) object);
		//puede que en el futuro se metan cambio de eventos y negocio tenga sus eventos propios separados de presentacion
		return new Context(rc.getEvento(), rc.getDato());
	}
}