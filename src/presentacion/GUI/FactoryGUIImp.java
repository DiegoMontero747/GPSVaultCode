package presentacion.GUI;

import presentacion.Controller.Context;
import presentacion.Controller.Evento;

import java.util.HashMap;
import java.util.Map;

public class FactoryGUIImp extends FactoryGUI {
    
    private final Map<Class<? extends ObservadorGUI>, ObservadorGUI> instancias = new HashMap<>();

    @Override
    public ObservadorGUI generarGUI(Context commandContext) {
        Evento evento = commandContext.getEvento();
        Class<? extends ObservadorGUI> claveVista = getVistaClass(evento);

        // Si ya existe, la reutilizamos
        if (instancias.containsKey(claveVista)) {
            return instancias.get(claveVista);
        }

        ObservadorGUI nuevaGUI = crearNuevaVista(claveVista);

        if (nuevaGUI != null) {
            instancias.put(claveVista, nuevaGUI);
        }

        return nuevaGUI;
    }

    private Class<? extends ObservadorGUI> getVistaClass(Evento evento) {
        // Mapeamos cada evento a su respectiva vista
        switch (evento) {
            case GUI_INICIO_SESION:
            	return GUI_InicioSesion.class;
            case INICIO_SESION_OK:
            	return GUI_InicioSesion.class;
            case INICIO_SESION_ERROR_USUARIO_INEXISTENTE:
            	return GUI_InicioSesion.class;
            case INICIO_SESION_ERROR_CONTRASENYA_INCORRECTA:
                return GUI_InicioSesion.class;
            case INICIO_SESION_ERROR_USUARIO_INCOMPLETO:
                return GUI_InicioSesion.class;
            case INICIO_SESION_ERROR_CONTRASENYA_INCOMPLETA:
                return GUI_InicioSesion.class;


            // Agregar más casos según se necesiten

            default:
                return null;
        }
    }

    private ObservadorGUI crearNuevaVista(Class<? extends ObservadorGUI> vistaClass) {
        if (vistaClass == null) return null;

        try {
            return vistaClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
