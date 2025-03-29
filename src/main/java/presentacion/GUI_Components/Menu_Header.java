package presentacion.GUI_Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;

public class Menu_Header extends JPanel {
	public Menu_Header(){
		init();
	}
	
	private void init() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // Layout horizontal
        setPreferredSize(new Dimension(800, 50)); // Ajustado para mejor proporción
        setBackground(new Color(40, 40, 40));

        //boton menu
        JButton btnMenu = new JButton();
        ImageIcon icon = new ImageIcon("media/icons8-menu-64.png"); // Ruta de la imagen
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        btnMenu.setIcon(new ImageIcon(img));
        btnMenu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Controller.getInstance().handleRequest(new Context(Evento.BOTON_MENU, null));
			}
		});
        
        //Boton de usuario
        JButton btnOpt = new JButton();
        icon = new ImageIcon("media/icons8-user-64.png"); // Ruta de la imagen
        img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        btnOpt.setIcon(new ImageIcon(img));
        
        // Ajustar tamaño del botón al de la imagen
        btnMenu.setPreferredSize(new Dimension(50, 50));
        btnMenu.setMinimumSize(new Dimension(50, 50));
        btnMenu.setMaximumSize(new Dimension(50, 50));
        
        btnOpt.setPreferredSize(new Dimension(50, 50));
        btnOpt.setMinimumSize(new Dimension(50, 50));
        btnOpt.setMaximumSize(new Dimension(50, 50));

        // Eliminar bordes y relleno
        btnMenu.setBorderPainted(false);
        btnMenu.setContentAreaFilled(false);
        btnMenu.setFocusPainted(false);
        btnMenu.setMargin(new java.awt.Insets(0, 0, 0, 0));
        
        btnOpt.setBorderPainted(false);
        btnOpt.setContentAreaFilled(false);
        btnOpt.setFocusPainted(false);
        btnOpt.setMargin(new java.awt.Insets(0, 0, 0, 0));

        // Agregar componentes con espacio flexible
        add(btnMenu);
        add(Box.createHorizontalGlue());
        add(btnOpt);
	}
}
