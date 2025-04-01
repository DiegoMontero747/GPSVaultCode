package presentacion.GUI_Components;

import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;
import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Menu_Sidebar extends JPanel {
    private static final long serialVersionUID = 1L;
    private CardLayout cardLayout;
    private JPanel contenedor;
    private Map<String, JPanel> panelsMap;
    
    // Constantes para diseño responsive
    //EL TAMAÑO DEL SIDEBAR ENTERO SE CAMBIA EN GUI_PRINCIPAL
    private static final int MIN_WIDTH = 100; // no funciona
    private static final int MAX_WIDTH = 100; // no funciona
    private static final int BUTTON_HEIGHT = 40; // TAMAÑO MAXIMO
    private static final int BUTTON_WIDTH = 500; // TAMAÑO MAXIMO 
    
    // Colores
    private static final Color BACKGROUND_COLOR = new Color(20, 20, 20, 180); 
    private static final Color BUTTON_COLOR = new Color(255, 94, 0, 180);
    private static final Color BUTTON_HOVER_COLOR = new Color(255, 120, 30, 220) ;
    private static final Color BUTTON_TEXT_COLOR = Color.BLACK;

    public Menu_Sidebar() {
        initComponents();
        setupResponsiveDesign();
    }

    private void initComponents() {
        setOpaque(true);
        setBackground(BACKGROUND_COLOR);
        
        panelsMap = new HashMap<>();
        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        contenedor.setOpaque(false);
        
        setLayout(new BorderLayout());
        add(contenedor, BorderLayout.CENTER);
    }

    public void setupResponsiveDesign() {
       
        adjustLayoutForSize(getWidth(),-1);
        
    }
 
    //en la altura ponemos -1 si queremos que utilice su propia altura actual
    private void adjustLayoutForSize(int currentWidth,int currentHeight) {
     
        
        // Ajustar ancho del sidebar
        int newWidth = calculateOptimalWidth(currentWidth);
        
        setMaximumSize(new Dimension(newWidth, currentHeight == -1 ? getHeight():currentHeight));
        
        
       
        
        // Revalidar y repintar
        
        revalidate();
        repaint();
    }

    private int calculateOptimalWidth(int currentWidth) {
 
        
            // Escalar entre MIN_WIDTH y MAX_WIDTH basado en el ancho disponible
            float scaleFactor = Math.min(1.0f, (currentWidth) / 1000.0f);
            return MIN_WIDTH + (int)((MAX_WIDTH - MIN_WIDTH) * scaleFactor);
        
    }

   
    public void init(String opcion) {
        loadConfiguration("config/config.json");
        showPanel(opcion.toUpperCase());
        setVisible(true);
    }

    private void loadConfiguration(String configPath) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(configPath)));
            JSONObject config = new JSONObject(jsonContent);
            
            createSectionPanel(config, "ADMIN", Evento.GUI_CREAR_CUENTA_ADMINISTRACION);
            // Agregar otras secciones según sea necesario
            createSectionPanel(config, "PASIVO", Evento.GUI_PRINCIPAL);
            createSectionPanel(config, "ACTIVO", Evento.GUI_PRINCIPAL);
            createSectionPanel(config, "SERVICIOS_CENTRALES", Evento.GUI_PRINCIPAL);
            
        } catch (IOException e) {
            handleConfigError(e);
        }
    }

    private void createSectionPanel(JSONObject config, String sectionName, Evento defaultEvent) {
        JPanel panel = new JPanel() {
        	@Override
            protected void paintComponent(Graphics g) {
                // Limpiar fondo primero
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(40, 40, 40, 200));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        try {
            JSONArray buttons = config.getJSONObject(sectionName).getJSONArray("buttons");
            for (int i = 0; i < buttons.length(); i++) {
                JSONObject buttonConfig = buttons.getJSONObject(i);
                addButtonToPanel(panel, buttonConfig, defaultEvent);
            }
        } catch (Exception e) {
            addDefaultButton(panel, sectionName, defaultEvent);
        }
        
        panelsMap.put(sectionName, panel);
        contenedor.add(panel, sectionName);
    }

    private void addButtonToPanel(JPanel panel, JSONObject buttonConfig, Evento defaultEvent) {
        String text = buttonConfig.getString("text");
        String eventName = buttonConfig.optString("event", defaultEvent.name());
        
        try {
            Evento event = Evento.valueOf(eventName);
            JButton button = createResponsiveButton(text, event);
            panel.add(Box.createRigidArea(new Dimension(0, 8)));
            panel.add(button);
        } catch (IllegalArgumentException e) {
            System.err.println("Evento no válido: " + eventName);
        }
    }

    private JButton createResponsiveButton(String text, Evento event) {
    	// codigo para volver a pintar el boton y arreglar el problema del hover, si da problemas
    	//comentar todo el override y quitar la funcion de hover
        JButton button = new JButton(text){
            @Override
            protected void paintComponent(Graphics g) {
                // 1. Limpiar completamente el área del botón
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.Clear);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                // 2. Dibujar fondo con transparencia
                g2.setComposite(AlphaComposite.SrcOver);
                Color bgColor = getModel().isRollover() ? 
                              BUTTON_HOVER_COLOR: // Hover (más opaco)
                              BUTTON_COLOR;    // Normal
                
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                // 3. Dibujar borde
                g2.setColor(new Color(0, 0, 0, 60));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                
                // 4. Dibujar texto (con sombra para mejor legibilidad)
                g2.setColor(BUTTON_TEXT_COLOR);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                
                // Posición centrada
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        
        // Configuración responsive del botón
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
       
        //setupButtonHoverEffects(button);
        setButtonStyle(button);
        
        // Acción del botón
        button.addActionListener((ActionEvent e) -> {
            Controller.getInstance().handleRequest(new Context(event, null));
        });
        
        return button;
    }
    
    private void setButtonStyle(JButton button) {
    	button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT)); // Tamaño predeterminado
    	button.setBackground(new Color(255, 94, 0, 180)); // Color de fondo
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setOpaque(true);
	}

    private void setupButtonHoverEffects(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	button.repaint();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
            	button.repaint();
               
            }
        });
    }

    private void addDefaultButton(JPanel panel, String sectionName, Evento defaultEvent) {
        JButton defaultButton = createResponsiveButton(sectionName + " (Default)", defaultEvent);
        panel.add(defaultButton);
    }

    private void handleConfigError(IOException e) {
        System.err.println("Error loading sidebar config: " + e.getMessage());
        JOptionPane.showMessageDialog(this, 
            "Error al cargar la configuración del menú", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        
        setupDefaultConfiguration();
    }

    private void showPanel(String panelName) {
        if (panelsMap.containsKey(panelName)) {
            cardLayout.show(contenedor, panelName);
        } else if (!panelsMap.isEmpty()) {
            cardLayout.show(contenedor, panelsMap.keySet().iterator().next());
        }
    }

    private void setupDefaultConfiguration() {
        JPanel defaultPanel = new JPanel();
        defaultPanel.setLayout(new BoxLayout(defaultPanel, BoxLayout.Y_AXIS));
        defaultPanel.setOpaque(false);
        
        JButton defaultButton = createResponsiveButton("Menú Principal", Evento.GUI_PRINCIPAL);
        defaultPanel.add(defaultButton);
        
        panelsMap.put("DEFAULT", defaultPanel);
        contenedor.add(defaultPanel, "DEFAULT");
    }
    
    //Animaciones para la sidebar
    
    
    public void showSidebar() {
        if (isVisible()) return;
       
        
        // Posición inicial (fuera de pantalla)
        setLocation(-getWidth(),getBounds().y);
        setVisible(true);
        
        Timer animationTimer = new Timer(200/15, null);
        animationTimer.addActionListener(new ActionListener() {
            int step = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                step++;
                int newX = (int)(-getWidth() + (getWidth() * step / (float)15));
                setLocation(newX, getBounds().y);
                
                if (step >= 15) {
                    animationTimer.stop();
                    setLocation(0, getBounds().y);
                }
            }
        });
        animationTimer.start();
    }

    public void hideSidebar() {
        if (!isVisible()) return;
        
        
        Timer animationTimer = new Timer(200/15, null);
        animationTimer.addActionListener(new ActionListener() {
            int step = 0;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                step++;
                int newX = (int)(-getWidth() * step / (float)15);
                setLocation(newX,getBounds().y);
                
                if (step >= 15) {
                    animationTimer.stop();
                    setVisible(false);
                }
            }
        });
        animationTimer.start();
    }
}