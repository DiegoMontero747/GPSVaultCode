package presentacion.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GUI_VistaRolAdmin {
    private JFrame frame;
    private JTable userTable;
    private JButton addUserButton, deleteUserButton, logoutButton;
    
    public GUI_VistaRolAdmin() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Panel de Administración - VAULTCODE");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Panel lateral con opciones
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(4, 1, 10, 10));
        sidePanel.setBackground(new Color(30, 30, 30));
        sidePanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        addUserButton = createStyledButton("Agregar Usuario");
        deleteUserButton = createStyledButton("Eliminar Usuario");
        logoutButton = createStyledButton("Cerrar Sesión");
        
        sidePanel.add(addUserButton);
        sidePanel.add(deleteUserButton);
        sidePanel.add(logoutButton);
        
        // Panel de contenido principal con tabla de usuarios
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JScrollPane scrollPane = new JScrollPane(userTable);
        
        contentPanel.add(new JLabel("Usuarios"), BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(sidePanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);
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
}