package presentacion.Controller;

import presentacion.Controller.Evento;
import presentacion.Controller.Comandos.*;
import presentacion.Controller.Comandos.ManejoSesiones.IniciaCuenta;

public class CommandFactoryImp extends CommandFactory{

	@Override
	public Command getCommand(Evento commandName) {
		Command comando = null;
		
		switch(commandName) {
		case INICIA_CUENTA:
			comando = new IniciaCuenta();
			break;
		}
		return comando;
	}
	
}
