package negocio.Factory;

public abstract class NegocioFactory {

	private static NegocioFactory instancia;

	public static NegocioFactory getInstance() {
		if (instancia == null)
			instancia = new NegocioFactoryImp();
		return instancia;
	}

	public abstract SAManejoSesiones crearSAManejoSesiones();

}
