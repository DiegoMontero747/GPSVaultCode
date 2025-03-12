package negocio.Factory;

import presentacion.Controller.Evento;

public class SAManejoSesionesImp implements SAManejoSesiones {

    @Override
    public Evento inicioSesion(String username, String password) {
    	
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
        	
            return Evento.INICIAR_SESSION_ERROR_1; // Usuario no existe en la BBDD
        }

        // Simulación user/pass
        String usuarioCorrecto = "admin";
        String passwordCorrecta = "1234";

        if (!username.equals(usuarioCorrecto)) {
            return Evento.INICIAR_SESSION_ERROR_1; // Usuario no encontrado
        }

        if (!password.equals(passwordCorrecta)) {
            return Evento.INICIAR_SESSION_KO_ERROR_2; // Contraseña incorrecta
        }

        return Evento.INICIAR_SESSION_OK; // Inicio de sesión válido
    }
}