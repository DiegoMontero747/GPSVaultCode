package presentacion.Controller;

import negocio.Factory.ResultContext;
import negocio.ManejoSesiones.SAManejoSesionesImp;
import negocio.ManejoSesiones.TSesion;

public class command_InicioSesion implements Command {

	    @Override
	    public Context execute(Object object) {
	        if (!(object instanceof TSesion)) {
	            return new Context(Evento.INICIAR_SESSION_KO_ERROR_3, "Formato de datos incorrecto");
	        }

	        TSesion sesion = (TSesion) object;

	        // Llamar al servicio de manejo de sesiones
	        SAManejoSesionesImp servicio = new SAManejoSesionesImp();
	        ResultContext resultado = servicio.inicioSesion(sesion);

	        // Devolver el contexto basado en el evento obtenido
	        return new Context(resultado.getEvento(), resultado.getDato());
	    }
}
