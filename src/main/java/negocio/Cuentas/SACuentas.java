package negocio.Cuentas;

import negocio.Factory.ResultContext;
import presentacion.Controller.Evento;

public interface SACuentas{

	ResultContext inicioSesion(TCuenta ses);
}
