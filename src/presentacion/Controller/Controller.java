package presentacion.Controller;

public class Controller {

	
    public void action(int evento, Object transfer) {

        Command com = CommandFactory.getInstance().getCommand(evento); 
        IGUI gui;

        System.out.println(evento);
        if(com == null) {

            gui = FactoryGUI.getInstance().getVista(evento);
            gui.actualizar(evento, transfer);

        }else {

            Pair<Integer, Object> respuesta = com.execute(transfer);


            gui = FactoryGUI.getInstance().getVista(respuesta.getKey());

            if(gui != null) {
                gui.actualizar(respuesta.getKey(), respuesta.getValue());
            } 

        }

    }
}
