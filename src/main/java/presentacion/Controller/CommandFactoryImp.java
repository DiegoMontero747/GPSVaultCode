package presentacion.Controller;

import presentacion.Controller.Comandos.ManejoSesiones.command_CrearCuentaAdministracion;
import presentacion.Controller.Comandos.ManejoSesiones.command_CrearCuentaCliente;
import presentacion.Controller.Comandos.ManejoSesiones.command_InicioSesion;

public class CommandFactoryImp extends CommandFactory{

	@Override
	public Command getCommand(Evento commandName) {
		Command comando = null;
		
		switch(commandName) {
		case INICIA_CUENTA:
			comando = new command_InicioSesion();
			break;
		case CREAR_CUENTA_ADM:
			comando = new command_CrearCuentaAdministracion();
			break;
		case CREAR_CUENTA_BANCARIA:
			comando = new command_CrearCuentaCliente();
			break;
		}
		
		return comando;
	}
	
}
