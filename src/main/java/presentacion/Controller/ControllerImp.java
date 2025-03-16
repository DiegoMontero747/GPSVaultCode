package presentacion.Controller;

import presentacion.GUI.FactoryGUI;
import presentacion.GUI.ObservadorGUI;

public class ControllerImp extends Controller {

	@Override
	public void handleRequest(Context requestContext) {

		Command comando = CommandFactory.getInstance().getCommand(requestContext.getEvento());

        if (comando != null) {
            Context commandContext = comando.execute(requestContext.getDato());

            // 🚨 Obtener la instancia existente para evitar duplicados
            ObservadorGUI vista = FactoryGUI.getInstance().generarGUI(commandContext);
            if (vista != null) {
                vista.actualizar(commandContext);  // Solo actualiza, no crea nueva
            }
        }else {
        	ObservadorGUI vista = FactoryGUI.getInstance().generarGUI(requestContext);
        }
	}

}
