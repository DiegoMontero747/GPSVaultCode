package presentacion.Controller;

public enum Evento {
	//SESSION-----------------------------------------------
	
	//TODO no se si estos eventos cambiaran porque creo que estarian mejor colocados en el evento que devuelve el negocio
	
	
	GUI_INICIAR_SESSION,
	
	INICIA_CUENTA,
	
	//error de contraseña o usuario
	INICIAR_SESSION_ERROR_1,
	
	//otro error ajeno al usuario
	INICIAR_SESSION_KO_ERROR_2,
	
	//inicio de sesion valido
	INICIAR_SESSION_OK;
	
	
}
