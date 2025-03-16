package presentacion.Controller;

public abstract class Controller {

	
private static Controller instance;
	
	public static Controller getInstance() {
		if (instance == null) instance = new ControllerImp();
		return instance;
	}
	
	public abstract void handleRequest(Context requestContext);
}
