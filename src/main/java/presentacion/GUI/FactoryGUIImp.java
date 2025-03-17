package presentacion.GUI;

import java.util.HashMap;
import java.util.Map;

import presentacion.Controller.Context;
import presentacion.Controller.Evento;

public class FactoryGUIImp extends FactoryGUI {

	protected final Map<Class<? extends ObservadorGUI>, ObservadorGUI> instancias = new HashMap<>();
	
    @Override
    public ObservadorGUI generarGUI(Context commandContext) {
    	 Class<? extends ObservadorGUI> claveVista = getVistaClass(commandContext.getEvento());

         if (claveVista == null) {
             return null;  // No se necesita ninguna GUI para este evento
         }

         // Si la vista ya existe, simplemente la devolvemos
         return instancias.computeIfAbsent(claveVista, this::crearNuevaVista);
    }

    private Class<? extends ObservadorGUI> getVistaClass(Evento evento) {
        // Mapeamos cada evento a su respectiva vista
        switch (evento) {
            case GUI_INICIO_SESION,
            INICIO_SESION_OK,
            INICIO_SESION_ERROR_CONTRASENYA_INCOMPLETA,
            INICIO_SESION_ERROR_CONTRASENYA_INCORRECTA, 
            INICIO_SESION_ERROR_USUARIO_INCOMPLETO, 
            INICIO_SESION_ERROR_USUARIO_INEXISTENTE:
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
