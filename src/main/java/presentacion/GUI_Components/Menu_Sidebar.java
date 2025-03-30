package presentacion.GUI_Components;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Menu_Sidebar extends JPanel {
	private static final long serialVersionUID = 1L;
	private Queue<JButton> colaAdmin;
	private Queue<JButton> colaServiciosGenerales;
	private Queue<JButton> colaPasivo;
	private Queue<JButton> colaRiesgo;
	private CardLayout cardLayout;
	private JPanel contenedor;
	private JButton crear_cuenta;
	
	public Menu_Sidebar() {
		contenedor = new JPanel(cardLayout);
		//setPreferredSize(new Dimension(100, 100));
	}

	public void init(String opcion) {
		// Inicializar las colas
		colaAdmin = new LinkedList<>();
		colaServiciosGenerales = new LinkedList<>();
		colaPasivo = new LinkedList<>();
		colaRiesgo = new LinkedList<>();
		
		crear_cuenta = createStyledButton("Crear cuenta");
		colaAdmin.add(crear_cuenta);
		
		cardLayout = new CardLayout();
		contenedor = new JPanel(cardLayout);
		
		// Crear paneles para cada cola
		contenedor.add(crearPanel(colaAdmin), "ADMIN");
		contenedor.add(crearPanel(colaServiciosGenerales), "SERVICIOS GENERALES");
		contenedor.add(crearPanel(colaPasivo), "PASIVO");
		contenedor.add(crearPanel(colaRiesgo), "RIESGO");
		
		setLayout(new BorderLayout());
		add(contenedor, BorderLayout.CENTER);
		
		// Mostrar el panel según la opción
		switch (opcion.toUpperCase()) {
		case "ADMIN":
			cardLayout.show(contenedor, "ADMIN");
			break;
		case "ADMINISTRACION":
			cardLayout.show(contenedor, "ADMIN");
			break;
		case "SERVICIOS GENERALES":
			cardLayout.show(contenedor, "SERVICIOS GENERALES");
			break;
		case "RIESGO":
			cardLayout.show(contenedor, "RIESGO");
			break;
		case "PASIVO":
			cardLayout.show(contenedor, "PASIVO");
			break;
		default:
			break;
		}
		this.setVisible(true);
	}

	private JPanel crearPanel(Queue<JButton> cola) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(cola.size(), 1));
		for (JButton boton : cola) {
			panel.add(boton);
		}
		return panel;
	}
	
	private JButton createStyledButton(String text) {
		JButton button = new JButton(text);
		button.setBackground(new Color(0, 87, 160));
		button.setForeground(Color.BLACK);
		button.setFont(new Font("Arial", Font.BOLD, 14));
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		button.setOpaque(true); // Asegura que el color de fondo se aplique
		return button;
	}
}
