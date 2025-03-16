package presentacion.GUI;

import java.util.HashMap;
import java.util.Map;

import presentacion.Controller.Context;

public abstract class FactoryGUI {
	private static FactoryGUI instance;

	public static FactoryGUI getInstance() {
		if (instance == null) {
			instance = new FactoryGUIImp();
		}
		return instance;
	}

	public abstract ObservadorGUI generarGUI(Context commandContext);
}
