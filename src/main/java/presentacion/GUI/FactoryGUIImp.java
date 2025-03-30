package presentacion.GUI;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import presentacion.Controller.Context;
import presentacion.Controller.Evento;

public class FactoryGUIImp extends FactoryGUI {

    protected final Map<Class<? extends ObservadorGUI>, ObservadorGUI> instancias = new HashMap<>();

    public FactoryGUIImp() {
        // Agregamos el ApplicationContainer al mapa de manera inicial
        instancias.put(ApplicationContainer.class, ApplicationContainer.getInstance());
    }

    @Override
    public ObservadorGUI generarGUI(Context commandContext) {
        Class<? extends ObservadorGUI> claveVista = getVistaClass(commandContext.getEvento());
        System.out.println("paso2");

        if (claveVista == null) {
            return null; // No se necesita ninguna GUI para este evento
        }

        // Si la vista ya existe, simplemente la devolvemos
        return instancias.get(claveVista);
    }

    private Class<? extends ObservadorGUI> getVistaClass(Evento evento) {
        // Mapeamos cada evento a su respectiva vista
        switch (evento) {
            case GUI_INICIO_SESION:
            	instancias.putIfAbsent(GUI_InicioSesion.class, new GUI_InicioSesion());
                ApplicationContainer.getInstance().addView("LOGIN",
                		(JPanel) instancias.get(GUI_InicioSesion.class));
                return ApplicationContainer.class;

		case INICIO_SESION_OK:
			instancias.putIfAbsent(GUI_Principal.class, GUI_Principal.getInstance());
			ApplicationContainer.getInstance().addView("VISTA_PRINCIPAL",
					(JPanel) instancias.get(GUI_Principal.class));
			return ApplicationContainer.class;
		case GUI_PRINCIPAL:
			return GUI_Principal.class;

		case INICIO_SESION_ERROR_CONTRASENYA_INCOMPLETA,
             INICIO_SESION_ERROR_CONTRASENYA_INCORRECTA,
             INICIO_SESION_ERROR_USUARIO_INCOMPLETO,
             INICIO_SESION_ERROR_USUARIO_INEXISTENTE:
             return GUI_InicioSesion.class;

        case GUI_CREAR_TARJETA_DEBITO:
             return GUI_CrearTarjetaDebito.class;
        case GUI_CREAR_CUENTA_ADMINISTRACION:
        	instancias.putIfAbsent(GUI_CrearCuentaAdministracion.class, new GUI_CrearCuentaAdministracion());
        	GUI_Principal.getInstance().addView("CREAR_CUENTA_ADMINISTRACION",
        			(JPanel) instancias.get(GUI_CrearCuentaAdministracion.class));
        	return GUI_Principal.class;

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
