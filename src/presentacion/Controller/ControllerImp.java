package presentacion.Controller;

import presentacion.GUI.FactoryGUI;
import presentacion.GUI.ObservadorGUI;

public class ControllerImp extends Controller {

	@Override
	public void handleRequest(Context requestContext) {

		Command comando = CommandFactory.getInstance().getCommand(requestContext.getEvento());

		if (comando != null) {

			Context commandContext = comando.execute(requestContext.getDato());

			ObservadorGUI vista = FactoryGUI.getInstance().generarGUI(commandContext);
			vista.actualizar(commandContext);
		} else
			FactoryGUI.getInstance().generarGUI(requestContext);
	}

}
