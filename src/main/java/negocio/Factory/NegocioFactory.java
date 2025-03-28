package negocio.Factory;

import negocio.ManejoSesiones.SAManejoSesiones;
import negocio.Tarjetas.SATarjetas;

public abstract class NegocioFactory {

	private static NegocioFactory instancia;

	public static NegocioFactory getInstance() {
		if (instancia == null)
			instancia = new NegocioFactoryImp();
		return instancia;
	}

	public abstract SAManejoSesiones crearSAManejoSesiones();
	
	public abstract SATarjetas crearSATarjetas();


}
