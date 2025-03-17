package presentacion.Controller;

public abstract class CommandFactory {
	
	public static CommandFactoryImp factory;
	
	public static CommandFactory getInstance() {
		
		if(factory == null) {
			factory = new CommandFactoryImp();
		}
		
		return factory;
	}

	public abstract Command getCommand(Evento commandName);
}
