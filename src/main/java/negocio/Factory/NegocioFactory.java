package negocio.Factory;

import negocio.ManejoSesiones.SACrearCuentaAdministracion;
import negocio.ManejoSesiones.SAManejoSesiones;
import negocio.Tarjetas.SATarjetas;
import negocio.Cuentas.SACuentas;


public abstract class NegocioFactory {

	private static NegocioFactory instancia;

	public static NegocioFactory getInstance() {
		if (instancia == null)
			instancia = new NegocioFactoryImp();
		return instancia;
	}

	public abstract SAManejoSesiones crearSAManejoSesiones();
	public abstract SACrearCuentaAdministracion crearSACrearCuentaAdministracion();
	public abstract SATarjetas crearSATarjetas();
	public abstract SACuentas crearSACuentas();

}
