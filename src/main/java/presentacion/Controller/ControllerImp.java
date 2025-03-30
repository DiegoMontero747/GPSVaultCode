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
	        
	        //notificar a los observadores
	        notificarObservadores(commandContext);
	    }

	 @Override
	 public void registerObserver(ObservadorGUI obs) {
	        if (!observadores.contains(obs)) {
	            observadores.add(obs);
	        }
	    }

	 @Override
	public void notificarObservadores(Context c) {
	      for (ObservadorGUI obs : observadores) {
	          obs.actualizar(c);
	      }
	 }
	 
	 @Override
	 public void UNregisterObserver(ObservadorGUI obs) {
		 if(observadores.contains(obs))
			 observadores.remove(obs);
	 }
}
