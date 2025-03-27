package presentacion.GUI_Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu_Header extends JPanel {
	public Menu_Header(){
		init();
	}
	
	private void init() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // Layout horizontal
        setPreferredSize(new Dimension(800, 50)); // Ajustado para mejor proporción
        setBackground(new Color(40, 40, 40));

        // 🔹 Crear botón con imagen
        JButton btnMenu = new JButton();
        ImageIcon icon = new ImageIcon("media/icons8-menu-64.png"); // Ruta de la imagen
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        btnMenu.setIcon(new ImageIcon(img));
        
        // Ajustar tamaño del botón al de la imagen
        btnMenu.setPreferredSize(new Dimension(50, 50));
        btnMenu.setMinimumSize(new Dimension(50, 50));
        btnMenu.setMaximumSize(new Dimension(50, 50));

        // Eliminar bordes y relleno
        btnMenu.setBorderPainted(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setFocusPainted(false);
        btnMenu.setMargin(new java.awt.Insets(0, 0, 0, 0));

        // 🔹 Botón de opciones
        JButton btnOpt = new JButton("Options");

        // Agregar componentes con espacio flexible
        add(btnMenu);
        add(Box.createHorizontalGlue());
        add(btnOpt);
	}
}
