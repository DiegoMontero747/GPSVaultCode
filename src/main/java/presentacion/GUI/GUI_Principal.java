package presentacion.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;
import presentacion.GUI_Components.Menu_Header;
import presentacion.GUI_Components.Menu_Sidebar;

public class GUI_Principal extends JLayeredPane implements ObservadorGUI {
    private static final long serialVersionUID = 1L;
    private static GUI_Principal instance;
    
    // Componentes principales
    private JPanel contentPanel;      // Panel base (DEFAULT_LAYER)
    private JPanel cardPanel;         // Panel para CardLayout
    private CardLayout cardLayout;    // Layout para cambiar vistas
    private Menu_Sidebar menu;        // Sidebar (PALETTE_LAYER)
    private Menu_Header header;       // Barra superior
    
    // Dimensiones
    private static final int SIDEBAR_WIDTH = 200;
    private static final int HEADER_HEIGHT = 50;

    public static GUI_Principal getInstance() {
        if (instance == null) {
            instance = new GUI_Principal();
        }
        return instance;
    }

    private GUI_Principal() {
        initializeComponents();
        setupLayout();
        setupResponsiveBehavior();
    }

    private void initializeComponents() {
        // Configuración básica del layered pane
        setOpaque(true);
        setBackground(new Color(50, 50, 50));
        setLayout(null); // Usamos layout absoluto para posicionamiento manual

        // Panel de contenido principal (fondo)
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        // Sidebar (menu lateral)
        menu = new Menu_Sidebar();
        menu.setOpaque(false);

        
        // Header
        header = new Menu_Header(menu);
        contentPanel.add(header, BorderLayout.NORTH);

        // Panel para CardLayout (vistas intercambiables)
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);
        contentPanel.add(cardPanel, BorderLayout.CENTER);

       
        // Añadir componentes a las capas correspondientes
        add(contentPanel, JLayeredPane.DEFAULT_LAYER);
        add(menu, JLayeredPane.PALETTE_LAYER);
    }

    private void setupLayout() {
        // Posicionar componentes inicialmente
        updateComponentBounds(getWidth(), getHeight());
    }

    private void setupResponsiveBehavior() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
               updateComponentBounds(getWidth(), getHeight());
               
            }
        });
    }

    private void updateComponentBounds(int width, int height) {
        // Actualizar bounds del contentPanel (ocupa todo el espacio)
        contentPanel.setBounds(0, 0, width, height);
        
        // Actualizar bounds del sidebar
        
//----------------------esta es la linea de código que hace que el sidebar se redimensione según el tamaño de la ventana-------------------------------
        menu.setBounds(0, HEADER_HEIGHT, width/4 < SIDEBAR_WIDTH ? SIDEBAR_WIDTH : width/4, height - HEADER_HEIGHT);
        menu.setupResponsiveDesign();      // Forzar redibujado
        revalidate();
        repaint();
    }

    public void addView(String name, JPanel view) {
        cardPanel.add(view, name);
        Controller.getInstance().registerObserver(this);
        
        if (view instanceof ObservadorGUI) {
            Controller.getInstance().registerObserver((ObservadorGUI) view);
        }
    }

    public void showView(String name) {
        cardLayout.show(cardPanel, name);
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
			menu.setVisible(false);
            updateComponentBounds(getWidth(), getHeight());
			break;
		case GUI_CREAR_CUENTA_ADMINISTRACION:
			showView("CREAR_CUENTA_ADMINISTRACION");
			break;
		case GUI_CREAR_CUENTA_BANCARIA:
			showView("CREAR_CUENTA_CLIENTE_BANCO");
			break;
		case GUI_CREAR_TARJETA_DEBITO:
			showView("CREAR_TARJETA_DEBITO");
			break;
		}
		
	}



   

   
}