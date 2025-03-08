package presentacion.GUI;

import java.awt.EventQueue;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.*;

public class GUI_InicioSesion {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_InicioSesion window = new GUI_InicioSesion();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI_InicioSesion() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("Iniciar Sesión");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Crear una textura de metal cepillado
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color baseColor = new Color(100, 100, 100);
                g2d.setPaint(new GradientPaint(0, 0, baseColor, width, height, Color.DARK_GRAY, true));
                g2d.fillRect(0, 0, width, height);
            }
        };
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(255, 140, 0)); // Naranja fuerte
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(userLabel, gbc);

        JTextField userField = new JTextField(15);
        userField.setBackground(new Color(40, 40, 40));
        userField.setForeground(Color.WHITE);
        userField.setCaretColor(Color.ORANGE);
        gbc.gridx = 1;
        panel.add(userField, gbc);

        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passLabel, gbc);

        JPasswordField passField = new JPasswordField(15);
        passField.setBackground(new Color(40, 40, 40));
        passField.setForeground(Color.WHITE);
        passField.setCaretColor(Color.ORANGE);
        gbc.gridx = 1;
        panel.add(passField, gbc);

        JButton loginButton = new JButton("Ingresar");
        loginButton.setBackground(new Color(255, 140, 0));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        frame.add(panel);
        frame.setVisible(true);
	}

}
