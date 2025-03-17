package negocio.ManejoSesiones;

import negocio.Factory.ResultContext;
import presentacion.Controller.Evento;

public interface SAManejoSesiones{

	ResultContext inicioSesion(TSesion ses);
}
