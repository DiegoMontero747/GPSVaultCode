package negocio.Factory;

import negocio.ManejoSesiones.SAManejoSesiones;
import negocio.ManejoSesiones.SAManejoSesionesImp;

public class NegocioFactoryImp extends NegocioFactory {

    @Override
    public SAManejoSesiones crearSAManejoSesiones() {
        return new SAManejoSesionesImp();
    }

}
