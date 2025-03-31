package presentacion.GUI;

import javax.swing.*;

import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;
import presentacion.GUI_Components.Menu_Header;
import presentacion.GUI_Components.Menu_Sidebar;

import java.awt.*;

public class GUI_Principal extends JPanel implements ObservadorGUI {
	private static final long serialVersionUID = 1L;
	private static GUI_Principal instance;
	private JPanel cardPanel;
	private CardLayout cardLayout;
	private Menu_Sidebar menu;

	public GUI_Principal() {
		initialize();
		// this.setVisible(true);
	}
	
	public static GUI_Principal getInstance() {
        if (instance == null) {
            instance = new GUI_Principal();
        }
        return instance;
    }

	private void initialize() {
		this.setSize(800, 500);
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(50, 50, 50));

		// Header de Menu
		menu = new Menu_Sidebar();
		this.add(new Menu_Header(menu), BorderLayout.NORTH);

		// Este CardLayout permitira que podamos mostrar las diferentes subvistas sin
		// mucha complcacion
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		cardPanel.setOpaque(false);
		cardPanel.setVisible(true);

		this.add(cardPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	
	public void addView(String name, JPanel view) {
        cardPanel.add(view, name); // Agrega el panel al CardLayout
     // Registrarse como observador
        Controller.getInstance().registerObserver(this);
        Controller.getInstance().registerObserver((ObservadorGUI) view);
        
    }

    public void showView(String name) {
         cardLayout.show(cardPanel, name); // Muestra el panel sin eliminar nada
         revalidate();
         repaint();
    }

	@Override
	public void actualizar(Context c) {
		// TODO Auto-generated method stub
		switch (c.getEvento()) {
		case GUI_PRINCIPAL:
			System.out.println(c.getDato());
			menu.init((String) c.getDato());
			this.add(menu, BorderLayout.WEST);
			menu.setVisible(false);
			break;
		case GUI_CREAR_CUENTA_ADMINISTRACION:
			showView("CREAR_CUENTA_ADMINISTRACION");
			break;
		case GUI_CREAR_CUENTA_BANCARIA:
			showView("CREAR_CUENTA_CLIENTE_BANCO");
			break;
		
		}

	}
}