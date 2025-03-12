package presentacion.GUI;

import presentacion.Controller.Context;

public class FactoryGUIImp extends FactoryGUI{
	
	@Override
	public ObservadorGUI generarGUI(Context commandContext) {
		switch (commandContext.getEvento()) {
		case GUI_INICIAR_SESSION:
			return new GUI_InicioSesion();
		}
		return null;
	}
}
