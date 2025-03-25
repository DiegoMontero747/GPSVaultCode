package presentacion.Controller;

import java.util.ArrayList;
import java.util.List;

import presentacion.GUI.ObservadorGUI;

public abstract class Controller {

	
private static Controller instance;

	protected List<ObservadorGUI> observadores = new ArrayList<>();

	public static Controller getInstance() {
		if (instance == null) instance = new ControllerImp();
		return instance;
	}
	
	public abstract void handleRequest(Context requestContext);
	
	public abstract void registerObserver(ObservadorGUI obs);
	
	public abstract void notificarObservadores(Context c);
}
