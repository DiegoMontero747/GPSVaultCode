package presentacion.Controller;

import presentacion.Controller.Comandos.ManejoSesiones.command_InicioSesion;

public class CommandFactoryImp extends CommandFactory{

	@Override
	public Command getCommand(Evento commandName) {
		Command comando = null;
		
		switch(commandName) {
		case INICIA_CUENTA:
			comando = new command_InicioSesion();
			break;
		 case GUI_MENU_ADMIN:
            comando = new CommandMenuAdmin();
            break;
         case GUI_MENU_CLIENTE:
            comando = new CommandMenuCliente();
            break;
		}
		return comando;
	}
	
}
