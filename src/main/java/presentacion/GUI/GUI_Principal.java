package presentacion.GUI;

import javax.swing.*;
import java.awt.*;
import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;
import presentacion.GUI_Components.Menu_Header;
import presentacion.GUI_Components.Menu_Sidebar;

public class GUI_Principal extends JLayeredPane implements ObservadorGUI {
    private static final long serialVersionUID = 1L;
    private static GUI_Principal instance;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Menu_Sidebar menu;
    private JPanel contentPanel;

    public GUI_Principal() {
        initialize();
    }

    public static GUI_Principal getInstance() {
        if (instance == null) {
            instance = new GUI_Principal();
        }
        return instance;
    }

    private void initialize() {
        this.setSize(800, 500);
        this.setLayout(null); // Usamos layout absoluto para posicionamiento preciso
        this.setBackground(new Color(50, 50, 50));

        // Capa de contenido (fondo)
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBounds(0, 0, 800, 500);
        contentPanel.setOpaque(false);

        // Header de Menu (ahora en la capa de contenido)
        menu = new Menu_Sidebar();
        contentPanel.add(new Menu_Header(menu), BorderLayout.NORTH);

        // Panel para CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);
        contentPanel.add(cardPanel, BorderLayout.CENTER);

        // Añadir el contentPanel como capa base
        this.add(contentPanel, JLayeredPane.DEFAULT_LAYER);
        
        // Configurar el sidebar como panel flotante
        menu.setBounds(0, 50, 200, 450); // Posición y tamaño inicial
        menu.setOpaque(false);
        this.add(menu, JLayeredPane.PALETTE_LAYER); // Capa superior
        
        this.setVisible(true);
    }

    public void addView(String name, JPanel view) {
        cardPanel.add(view, name);
        Controller.getInstance().registerObserver(this);
        Controller.getInstance().registerObserver((ObservadorGUI) view);
    }

    public void showView(String name) {
        cardLayout.show(cardPanel, name);
        revalidate();
        repaint();
    }

    @Override
    public void actualizar(Context c) {
        switch (c.getEvento()) {
            case GUI_PRINCIPAL:
                System.out.println(c.getDato());
                menu.init((String) c.getDato());
                menu.setVisible(true); // Mostrar/ocultar según necesidad
                revalidate();
                repaint();
                break;
            case GUI_CREAR_CUENTA_ADMINISTRACION:
                showView("CREAR_CUENTA_ADMINISTRACION");
                break;
        }
    }

    // Método para alternar la visibilidad del sidebar
    public void toggleSidebar() {
        menu.setVisible(!menu.isVisible());
        revalidate();
        repaint();
    }
}