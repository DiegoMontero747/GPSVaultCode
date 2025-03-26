package presentacion.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import presentacion.Controller.Context;
import presentacion.GUI_Components.Menu_Header;

import java.awt.*;

public class GUI_Principal extends JPanel implements ObservadorGUI {
    private JTable userTable;
    private JButton addFundsButton, retireFundsButton, logoutButton, createUserButton;
    
    public GUI_Principal() {
        initialize();
        this.setVisible(true);  
    }

    private void initialize() {
        this.setSize(800, 500);
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(50, 50, 50));
        
        
        //Header de Menu
        this.add(new Menu_Header(), BorderLayout.NORTH);
        
        

        // Panel lateral con opciones
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(4, 1, 10, 10));
        sidePanel.setBackground(new Color(30, 30, 30));
        sidePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        createUserButton = createStyledButton("Crear Usuario");
        addFundsButton = createStyledButton("Añadir Fondos");
        retireFundsButton = createStyledButton("Retirar Fondos");
        logoutButton = createStyledButton("Cerrar Sesión");

        sidePanel.add(createUserButton);
        sidePanel.add(addFundsButton);
        sidePanel.add(retireFundsButton);
        sidePanel.add(logoutButton);
        
        // Panel de contenido principal con tabla de usuarios
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JScrollPane scrollPane = new JScrollPane(userTable);
        
        contentPanel.add(new JLabel("Usuarios"), BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.setOpaque(false);

        this.add(sidePanel, BorderLayout.WEST);
        this.add(contentPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 87, 160));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        button.setOpaque(true);  // Asegura que el color de fondo se aplique
        return button;
    }

	@Override
	public void actualizar(Context c) {
		// TODO Auto-generated method stub
		
	}
}