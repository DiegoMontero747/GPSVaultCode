package presentacion.GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;

public class ApplicationContainer extends JFrame implements ObservadorGUI{
	
	 private static ApplicationContainer instance;
	 	private Container contentPane;
	    private Map<String, JPanel> views; // Mapa para almacenar los paneles
	    private JPanel currentView; // Panel actualmente mostrado

	    private ApplicationContainer() {
	        setTitle("VAULTCODE");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(600, 400);
	        setLocationRelativeTo(null);
	        this.setLayout(new BorderLayout());

	        contentPane = getContentPane();
	        views = new HashMap<>();

	        setVisible(true);
	    }

	    public static ApplicationContainer getInstance() {
	        if (instance == null) {
	            instance = new ApplicationContainer();
	            Controller.getInstance().handleRequest(new Context(Evento.GUI_INICIO_SESION, null));
	        }
	        return instance;
	    }

	    public void addView(String name, JPanel view) {
	        views.put(name, view);
	    }

	    public void showView(String name) {
	        JPanel newView = views.get(name);
	        if (newView != null) {
	            contentPane.removeAll(); // Quitar el panel actual
	            contentPane.add(newView, BorderLayout.CENTER); // Agregar el nuevo panel
	            pack();
	            validate();
	            setVisible(true);
	            repaint();
	        } else {
	            System.err.println("Error: Vista '" + name + "' no encontrada.");
	        }
	    }

	    @Override
	    public void actualizar(Context c) {
	        switch (c.getEvento()) {
	            case GUI_INICIO_SESION:
	                showView("LOGIN");
	                break;
	        }
	    }
}
