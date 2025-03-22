package presentacion.Controller;

public enum Evento {
	//SESSION-----------------------------------------------
	
	//TODO no se si estos eventos cambiaran porque creo que estarian mejor colocados en el evento que devuelve el negocio
	
	
	GUI_INICIO_SESION,
	
	INICIA_CUENTA,
	
	// usuario no existe en la BBDD
	INICIO_SESION_ERROR_USUARIO_INEXISTENTE,
	
	// la contraseña no coincide 
	INICIO_SESION_ERROR_CONTRASENYA_INCORRECTA,
	
	// usuario incompletos
	INICIO_SESION_ERROR_USUARIO_INCOMPLETO,
	
	// contrasenya incompletos
	INICIO_SESION_ERROR_CONTRASENYA_INCOMPLETA,
	
	//inicio de sesion valido
	INICIO_SESION_OK, 
	
	//Si el usuario es un administrador
	GUI_VISTAROLADMIN;
	
	
}
