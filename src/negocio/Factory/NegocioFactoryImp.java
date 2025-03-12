package negocio.Factory;

public class NegocioFactoryImp extends NegocioFactory {

    @Override
    public SAManejoSesiones crearSAManejoSesiones() {
        return new SAManejoSesionesImp();
    }

}
