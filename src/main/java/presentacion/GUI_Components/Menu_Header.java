package presentacion.GUI_Components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu_Header extends JPanel {
	public Menu_Header(){
		init();
	}
	
	private void init() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // Layout horizontal
        setPreferredSize(new Dimension(800, 40)); // Tamaño del header
        setBackground(new Color(50, 50, 50));

        // 🔹 Crear botones
        JButton btnUsuario = new JButton("Usuario");
        JButton btnOpt = new JButton("Options");

        //Agregar componentes con espacio flexible
        add(btnUsuario);
        add(Box.createHorizontalGlue());
        add(btnOpt);
	}
}
