package presentacion.Controller.Comandos.ManejoSesiones;

import negocio.Factory.NegocioFactory;
import presentacion.Controller.Command;
import presentacion.Controller.Context;
import presentacion.Controller.Evento;

public class IniciaCuenta implements Command{
	@Override
	public Context execute(Object object) {
		int id = 0;
		NegocioFactory nf = NegocioFactory.getInstance();
		Evento evento = null;
		// TODO Auto-generated method stub
		return new Context(evento, id);
	}
}