package presentacion.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import presentacion.Controller.Context;
import presentacion.GUI_Components.Menu_Header;
import presentacion.GUI_Components.Menu_Sidebar;

import java.awt.*;

public class GUI_Principal extends JPanel implements ObservadorGUI {
	//private JTable userTable;
	//private JButton addFundsButton, retireFundsButton, logoutButton, createUserButton;
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
		this.add(new Menu_Header(), BorderLayout.NORTH);

		// Este CardLayout permitira que podamos mostrar las diferentes subvistas sin
		// mucha complcacion
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		cardPanel.setOpaque(false);

		this.add(cardPanel, BorderLayout.CENTER);
		menu = new Menu_Sidebar("admin");
		this.add(menu, BorderLayout.WEST);
		this.setVisible(true);
		menu.setVisible(true);
		
	}

	@Override
	public void actualizar(Context c) {
		// TODO Auto-generated method stub

	}
}