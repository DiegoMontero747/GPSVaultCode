package presentacion.Controller;

public enum Evento {
	//SESSION-----------------------------------------------
	
	//TODO no se si estos eventos cambiaran porque creo que estarian mejor colocados en el evento que devuelve el negocio
	
	
	GUI_INICIAR_SESSION,
	
	INICIA_CUENTA,
	
	//usuario no existe en la BBDD
	INICIAR_SESSION_ERROR_1,
	
	//la contraseña no coincide 
	INICIAR_SESSION_KO_ERROR_2,
	
	//datos incompletos
	INICIAR_SESSION_KO_ERROR_3,
	
	//inicio de sesion valido
	INICIAR_SESSION_OK;
	
	
}
