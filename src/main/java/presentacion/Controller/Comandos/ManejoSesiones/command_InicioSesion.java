package presentacion.Controller.Comandos.ManejoSesiones;

import negocio.Factory.ResultContext;
import negocio.ManejoSesiones.SAManejoSesionesImp;
import negocio.ManejoSesiones.TSesion;
import presentacion.Controller.Command;
import presentacion.Controller.Context;
import presentacion.Controller.Evento;

public class command_InicioSesion implements Command {

	    @Override
	    public Context execute(Object object) {
	        if (!(object instanceof TSesion)) {
	            return new Context(Evento.INICIO_SESION_ERROR_USUARIO_INCOMPLETO, "Formato de datos incorrecto");
	        }

	        TSesion sesion = (TSesion) object;

	        // Llamar al servicio de manejo de sesiones
	        SAManejoSesionesImp servicio = new SAManejoSesionesImp();
	        ResultContext resultado = servicio.inicioSesion(sesion);

	        // Devolver el contexto basado en el evento obtenido
	        return new Context(resultado.getEvento(), resultado.getDato());
	    }
}
