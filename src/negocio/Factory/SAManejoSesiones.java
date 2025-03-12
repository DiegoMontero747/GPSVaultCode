package negocio.Factory;

import presentacion.Controller.Evento;

public interface SAManejoSesiones{

	Evento inicioSesion(String username, String password);
}
