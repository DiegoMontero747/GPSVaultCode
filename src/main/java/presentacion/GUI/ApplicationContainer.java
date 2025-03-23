package presentacion.GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
	 	private CardLayout cardLayout;
	    private Map<String, JPanel> views; // Mapa para almacenar los paneles

	    private ApplicationContainer() {
	        setTitle("VAULTCODE");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(800, 600);
	        setLocationRelativeTo(null);
	        
	        views = new HashMap<>();
	        cardLayout = new CardLayout();
	        setLayout(cardLayout);

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
	        add(view, name); // Agrega el panel al CardLayout
	    }

	    public void showView(String name) {
	    	 if (views.containsKey(name)) {
	             cardLayout.show(getContentPane(), name); // Muestra el panel sin eliminar nada
	             revalidate();
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
