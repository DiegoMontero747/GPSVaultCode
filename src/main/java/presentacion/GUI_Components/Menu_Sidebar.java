package presentacion.GUI_Components;

import javax.swing.*;
import org.json.JSONArray;
import org.json.JSONObject;
import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;

import java.awt.*;
import java.awt.event.ActionEvent;
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
    private int maxButtonWidth = 200; // Ancho máximo de los botones
    private int maxButtonHeight = 40; // Altura máxima de los botones

    public Menu_Sidebar() {
        panelsMap = new HashMap<>();
        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        setLayout(new BorderLayout());
        add(contenedor, BorderLayout.CENTER);
    }

    public void init(String opcion) {
        // Cargar configuración desde JSON
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get("config/sidebar_config.json")));
            JSONObject config = new JSONObject(jsonContent);
            
            // Crear paneles para cada sección
            createSectionPanel(config, "ADMIN", Evento.GUI_CREAR_CUENTA_ADMINISTRACION);
            //createSectionPanel(config, "SERVICIOS_GENERALES", Evento.GUI_SERVICIOS_GENERALES);
            //createSectionPanel(config, "PASIVO", Evento.GUI_PASIVO);
            //createSectionPanel(config, "RIESGO", Evento.GUI_RIESGO);
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la configuración del sidebar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            // Configuración por defecto si falla la carga
            setupDefaultConfiguration();
        }

        // Mostrar el panel según la opción
        showPanel(opcion.toUpperCase());
        this.setVisible(true);
    }

    private void createSectionPanel(JSONObject config, String sectionName, Evento defaultEvent) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        try {
            JSONArray buttons = config.getJSONObject(sectionName).getJSONArray("buttons");
            for (int i = 0; i < buttons.length(); i++) {
                JSONObject buttonConfig = buttons.getJSONObject(i);
                String text = buttonConfig.getString("text");
                String eventName = buttonConfig.optString("event", defaultEvent.name());
                
                try {
                    Evento event = Evento.valueOf(eventName);
                    JButton button = createStyledButton(text, event);
                    panel.add(Box.createRigidArea(new Dimension(0, 5))); // Espacio entre botones
                    panel.add(button);
                } catch (IllegalArgumentException e) {
                    System.err.println("Evento no válido: " + eventName);
                }
            }
        } catch (Exception e) {
            System.err.println("Error procesando sección " + sectionName + ": " + e.getMessage());
            // Botón por defecto si hay error en la configuración
            JButton defaultButton = createStyledButton(sectionName, defaultEvent);
            panel.add(defaultButton);
        }
        
        panelsMap.put(sectionName, panel);
        contenedor.add(panel, sectionName);
    }

    private JButton createStyledButton(String text, Evento event) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(maxButtonWidth, maxButtonHeight));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Estilo del botón
        button.setBackground(new Color(0, 87, 160));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 120, 215));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 87, 160));
            }
        });
        
        button.addActionListener((ActionEvent e) -> {
            Controller.getInstance().handleRequest(new Context(event, null));
        });
        
        return button;
    }

    private void showPanel(String panelName) {
        if (panelsMap.containsKey(panelName)) {
            cardLayout.show(contenedor, panelName);
        } else {
            // Mostrar el primero disponible si el solicitado no existe
            if (!panelsMap.isEmpty()) {
                cardLayout.show(contenedor, panelsMap.keySet().iterator().next());
            }
        }
    }

    private void setupDefaultConfiguration() {
        // Configuración por defecto si falla la carga del JSON
        JPanel defaultPanel = new JPanel();
        defaultPanel.setLayout(new BoxLayout(defaultPanel, BoxLayout.Y_AXIS));
        JButton defaultButton = createStyledButton("Default Button", Evento.GUI_CREAR_CUENTA_ADMINISTRACION);
        defaultPanel.add(defaultButton);
        
        panelsMap.put("DEFAULT", defaultPanel);
        contenedor.add(defaultPanel, "DEFAULT");
    }
}