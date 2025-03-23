package presentacion.Controller;

import presentacion.Controller.Command;
import presentacion.Controller.Context;
import presentacion.Controller.Evento;

public class CommandMenuCliente implements Command {
    @Override
    public Context execute(Object data) {
        return new Context(Evento.GUI_MENU_CLIENTE, data);
    }
}