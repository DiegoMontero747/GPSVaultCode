package presentacion.Controller.Comands.Tarjetas;

import negocio.Factory.NegocioFactory;
import negocio.Factory.ResultContext;
import negocio.ManejoSesiones.SAManejoSesionesImp;
import negocio.ManejoSesiones.TSesion;
import negocio.Tarjetas.SATarjetas;
import negocio.Tarjetas.TTarjeta;
import presentacion.Controller.Command;
import presentacion.Controller.Context;
import presentacion.Controller.Evento;

public class command_CrearTarjetaDebito implements Command {

	    @Override
	    public Context execute(Object object) {
	        if (!(object instanceof TSesion)) {
	           //TODO anyadir evento para la tarjeta //return new Context(Evento.INICIO_SESION_ERROR_USUARIO_INCOMPLETO, "Formato de datos incorrecto");
	        }

	        TTarjeta tarjeta = (TTarjeta) object;

	        // Llamar al servicio de manejo de sesiones
	        SATarjetas servicio = NegocioFactory.getInstance().crearSATarjetas();
	        ResultContext resultado = servicio.crearTarjetaDebito(tarjeta);

	        // Devolver el contexto basado en el evento obtenido
	        return new Context(resultado.getEvento(), resultado.getDato());
	    }
	 
	
}
