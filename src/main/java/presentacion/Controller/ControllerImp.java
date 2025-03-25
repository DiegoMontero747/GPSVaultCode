package presentacion.Controller;

import presentacion.GUI.FactoryGUI;
import presentacion.GUI.ObservadorGUI;

public class ControllerImp extends Controller {

	 @Override
	    public void handleRequest(Context requestContext) {
	        Command comando = CommandFactory.getInstance().getCommand(requestContext.getEvento());
	        Context commandContext = (comando != null) ? comando.execute(requestContext.getDato()) : requestContext;

	        // 🚨 Obtener o crear la vista 
	        ObservadorGUI vista = FactoryGUI.getInstance().generarGUI(commandContext);

	        if (vista != null) {
	            vista.actualizar(commandContext);  // Si la vista existe, actualizarla
	        }
	    }

}
