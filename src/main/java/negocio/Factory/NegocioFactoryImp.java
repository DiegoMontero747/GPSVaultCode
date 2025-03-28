package negocio.Factory;

import negocio.ManejoSesiones.SAManejoSesiones;
import negocio.ManejoSesiones.SAManejoSesionesImp;
import negocio.Tarjetas.*;

public class NegocioFactoryImp extends NegocioFactory {

    @Override
    public SAManejoSesiones crearSAManejoSesiones() {
        return new SAManejoSesionesImp();
    }
    
    public SATarjetas crearSATarjetas() {
        return new SATarjetasImp();
    }

}
