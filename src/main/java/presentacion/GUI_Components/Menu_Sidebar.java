package presentacion.GUI_Components;

import javax.swing.*;

import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;
import presentacion.GUI.ObservadorGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Menu_Sidebar extends JPanel implements ObservadorGUI {
    private static final long serialVersionUID = 1L;
    private final Map<String, Queue<JButton>> botonesPorRol;
    private final CardLayout cardLayout;
    private final JPanel contenedor;

    public Menu_Sidebar() {
        botonesPorRol = new HashMap<>();
        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        setLayout(new BorderLayout());
        add(contenedor, BorderLayout.CENTER);
    }

    public void init(String opcion) {
    	//ADMIN------------------------------------------
        agregarBoton("ADMIN", "Crear cuenta", Evento.GUI_CREAR_CUENTA_ADMINISTRACION);
        
        //PASIVO-----------------------------------------
        agregarBoton("PASIVO", "Nuevo cliente", Evento.GUI_CREAR_CUENTA_BANCARIA);
        
        for (Map.Entry<String, Queue<JButton>> entry : botonesPorRol.entrySet()) {
            contenedor.add(crearPanel(entry.getValue()), entry.getKey());
        }

        cardLayout.show(contenedor, opcion.toUpperCase());
        this.setVisible(true);
    }

    public void agregarBoton(String categoria, String texto, Evento evento) {
        botonesPorRol.putIfAbsent(categoria, new LinkedList<>());
        JButton boton = createStyledButton(texto);
        boton.addActionListener(e -> Controller.getInstance().handleRequest(new Context(evento, null)));
        botonesPorRol.get(categoria).add(boton);
    }

    private JPanel crearPanel(Queue<JButton> cola) {
    	 JPanel panel = new JPanel();
    	 panel.setBackground(new Color(40,40,40));
         panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Asegura que los botones se apilen verticalmente
         panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Añade margen para evitar recortes
         panel.setPreferredSize(new Dimension(200, cola.size() * 10)); // Ajusta tamaño dinámico según botones
         for (JButton boton : cola) {
             boton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centra los botones
             panel.add(Box.createVerticalStrut(10)); // Espaciado entre botones
             panel.add(boton);
         }
         return panel;
    }

    private JButton createStyledButton(String text) {
    	JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(50, 40)); // Tamaño predeterminado
        button.setBackground(new Color(255, 94, 0)); // Color de fondo
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setOpaque(true);
        return button;
    }

    @Override
    public void actualizar(Context c) {
        // TODO Auto-generated method stub
    }
}
