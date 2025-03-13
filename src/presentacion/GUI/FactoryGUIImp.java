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
            case GUI_INICIAR_SESSION:
            case INICIAR_SESSION_OK:
            case INICIAR_SESSION_ERROR_1:
            case INICIAR_SESSION_KO_ERROR_2:
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
