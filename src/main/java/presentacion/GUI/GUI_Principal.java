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

    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Menu_Sidebar menu;
    private Menu_Header header;

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
        setOpaque(true);
        setBackground(Color.BLACK);
        setLayout(null);

        // Panel de fondo con imagen y overlay
        BackgroundPanel backgroundPanel = new BackgroundPanel("media/background2.png");
        backgroundPanel.setLayout(new BorderLayout());

        // Sidebar
        menu = new Menu_Sidebar();
        menu.setOpaque(false);

        // Header
        header = new Menu_Header(menu);
        backgroundPanel.add(header, BorderLayout.NORTH);

        // Panel de vistas
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setOpaque(false);
        backgroundPanel.add(cardPanel, BorderLayout.CENTER);

        // Añadir al layered pane
        add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);
        add(menu, JLayeredPane.PALETTE_LAYER);
    }

    private void setupLayout() {
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
        for (Component comp : getComponentsInLayer(JLayeredPane.DEFAULT_LAYER)) {
            comp.setBounds(0, 0, width, height);
        }

        menu.setBounds(0, HEADER_HEIGHT, width / 4 < SIDEBAR_WIDTH ? SIDEBAR_WIDTH : width / 4, height - HEADER_HEIGHT);
        menu.setupResponsiveDesign();

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

    // Clase interna para pintar la imagen con opacidad + overlay oscuro
    private static class BackgroundPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private final Image image;
        private final float imageOpacity = 0.8f;   // Fade a la imagen (0 = invisible, 1 = opaca)
        private final float overlayOpacity = 0.2f; // Oscurecer fondo (0 = transparente, 1 = negro total)

        public BackgroundPanel(String path) {
            Image img = null;
            try {
                img = new ImageIcon(path).getImage();
            } catch (Exception e) {
                System.err.println("No se pudo cargar la imagen: " + path);
            }
            this.image = img;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();

            if (image != null) {
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, imageOpacity));
                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }

            // Dibujar overlay oscuro
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, overlayOpacity));
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.dispose();
        }
    }
}