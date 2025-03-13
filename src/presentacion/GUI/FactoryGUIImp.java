package presentacion.GUI;

import java.util.EnumSet;
import java.util.Set;

import presentacion.Controller.Context;
import presentacion.Controller.Evento;

public class FactoryGUIImp extends FactoryGUI{
	
	@Override
	public ObservadorGUI generarGUI(Context commandContext) {
		switch (commandContext.getEvento()) {
		case GUI_INICIAR_SESSION, 
	    INICIAR_SESSION_OK, 
	    INICIAR_SESSION_ERROR_1, 
	    INICIAR_SESSION_KO_ERROR_2, 
	    INICIAR_SESSION_KO_ERROR_3:
			return new GUI_InicioSesion();
		}
		return null;
	}
}
