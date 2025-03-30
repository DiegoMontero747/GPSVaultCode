package presentacion.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import presentacion.Controller.Context;
import presentacion.Controller.Evento;
import presentacion.GUI_Components.Menu_Header;
import presentacion.GUI_Components.Menu_Sidebar;

import java.awt.*;

public class GUI_Principal extends JPanel implements ObservadorGUI {
	private static final long serialVersionUID = 1L;
	// private JTable userTable;
	// private JButton addFundsButton, retireFundsButton, logoutButton,
	// createUserButton;
	private JPanel cardPanel;
	private CardLayout cardLayout;
	private Menu_Sidebar menu;

	public GUI_Principal() {
		initialize();
		// this.setVisible(true);
	}

	private void initialize() {
		this.setSize(800, 500);
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(50, 50, 50));

		// Header de Menu
		menu = new Menu_Sidebar();
		this.add(new Menu_Header(menu), BorderLayout.NORTH);

		// Este CardLayout permitira que podamos mostrar las diferentes subvistas sin
		// mucha complcacion
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		cardPanel.setOpaque(false);

		this.add(cardPanel, BorderLayout.CENTER);
		this.setVisible(true);
	}

	@Override
	public void actualizar(Context c) {
		// TODO Auto-generated method stub
		if (c.getEvento() == Evento.GUI_PRINCIPAL) {
			System.out.println(c.getDato());
			menu.init((String) c.getDato());
			this.add(menu, BorderLayout.WEST);
			menu.setVisible(false);
		}

	}
}