package presentacion.GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;

public class ApplicationContainer extends JFrame implements ObservadorGUI{
	
	private static ApplicationContainer instance;
 	private CardLayout cardLayout;

    private ApplicationContainer() {
        setTitle("VAULTCODE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
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
        add(view, name); // Agrega el panel al CardLayout
     // Registrarse como observador
        Controller.getInstance().registerObserver(this);
        Controller.getInstance().registerObserver((ObservadorGUI) view);
        
    }
    
    //esto es para el GUI_Principal que es un JLayeredPane 
    public void addView(String name, JLayeredPane view) {
        add(view, name); // Agrega el panel al CardLayout
     // Registrarse como observador
        Controller.getInstance().registerObserver(this);
        Controller.getInstance().registerObserver((ObservadorGUI) view);
        
    }

    public void showView(String name) {
         cardLayout.show(getContentPane(), name); // Muestra el panel sin eliminar nada
         revalidate();
         repaint();
    }

    @Override
    public void actualizar(Context c) {
        switch (c.getEvento()) {
            case GUI_INICIO_SESION:
                showView("LOGIN");
                break;
            case INICIO_SESION_OK:
            	//Controller.getInstance().UNregisterObserver((ObservadorGUI) ((BorderLayout) getLayout()).getLayoutComponent(BorderLayout.CENTER));
            	showView("VISTA_PRINCIPAL");
            	break;
        }
    }
}
