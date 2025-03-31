package presentacion.GUI_Components;

import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;
import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;

import java.awt.*;
import java.awt.event.ActionEvent;
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
    private static final int MIN_WIDTH = 10;
    private static final int MAX_WIDTH = 150;
    private static final int BUTTON_HEIGHT = 40;
    private static final int BUTTON_WIDTH = 200;
    
    // Colores
    private static final Color BACKGROUND_COLOR = new Color(20, 20, 20, 180); 
    private static final Color BUTTON_COLOR = new Color(0, 87, 160, 150);
    private static final Color BUTTON_HOVER_COLOR = new Color(0, 120, 215, 180);
    private static final Color BUTTON_TEXT_COLOR = Color.WHITE;

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
        
        
        adjustButtonStyles();
        
        // Revalidar y repintar
        
        revalidate();
        repaint();
    }

    private int calculateOptimalWidth(int currentWidth) {
 
        
            // Escalar entre MIN_WIDTH y MAX_WIDTH basado en el ancho disponible
            float scaleFactor = Math.min(1.0f, (currentWidth) / 1000.0f);
            return MIN_WIDTH + (int)((MAX_WIDTH - MIN_WIDTH) * scaleFactor);
        
    }

    private void adjustButtonStyles() {
        for (JPanel panel : panelsMap.values()) {
            for (Component comp : panel.getComponents()) {
                if (comp instanceof JButton) {
                    JButton button = (JButton) comp;
                    
                    // Ajustar tamaño y fuente 
                    
                    button.setFont(new Font("Arial", Font.BOLD, 13));
                    button.setMargin(new Insets(5, 15, 5, 15));
                    
                }
            }
        }
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
        JButton button = new JButton(text) {
        	//redibujar los botones para evitar bug al hacer el hover
        	@Override
            protected void paintComponent(Graphics g) {
                // 1. Limpiar completamente el área del botón
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setComposite(AlphaComposite.Clear);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                // 2. Dibujar fondo con transparencia
                g2.setComposite(AlphaComposite.SrcOver);
                if (getModel().isPressed()) {
                    g2.setColor(new Color(0, 87, 160, 200)); // Más opaco al presionar
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(0, 120, 215, 180)); // Hover
                } else {
                    g2.setColor(new Color(0, 87, 160, 150)); // Normal
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // 3. Dibujar texto
                g2.setColor(Color.WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                Rectangle2D textBounds = fm.getStringBounds(getText(), g2);
                
                int textX = (int) ((getWidth() - textBounds.getWidth()) / 2);
                int textY = (int) ((getHeight() - textBounds.getHeight()) / 2 + fm.getAscent());
                
                g2.drawString(getText(), textX, textY);
                g2.dispose();
            }
            
         
        };
        
        // Configuración responsive del botón
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
       
       
        
        // Estilo del botón
        button.setBackground(BUTTON_COLOR);
        button.setForeground(BUTTON_TEXT_COLOR);
        button.setFont(new Font("Arial", Font.BOLD, 13));
     
     
        setupButtonHoverEffects(button);
        
        // Acción del botón
        button.addActionListener((ActionEvent e) -> {
            Controller.getInstance().handleRequest(new Context(event, null));
        });
        
        return button;
    }
    
    private void setButtonStyle(JButton button) {
    	button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT)); // Tamaño predeterminado
        button.setBackground(new Color(255, 94, 0)); // Color de fondo
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setOpaque(true);
	}

    private void setupButtonHoverEffects(JButton button) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
                button.repaint();
                //button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
                button.repaint();
                //button.setCursor(Cursor.getDefaultCursor());
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
}