package presentacion.GUI;

import java.awt.*;

import javax.swing.*;
import presentacion.Controller.Context;

public class GUI_CrearCuentaAdministracion implements ObservadorGUI {
    private JFrame frame;
    private JLabel errorLabel;

    private JTextField userField = new JTextField(15);
    private JPasswordField passField = new JPasswordField(15);
    private JPasswordField repeatPassField = new JPasswordField(15);
    private JTextField nameField = new JTextField(15);
    private JTextField surnameField = new JTextField(15);
    private JTextField DNIField = new JTextField(15);
    private JTextField telfField = new JTextField(15);
    private JComboBox<String> rolComboBox = new JComboBox<String>();
    
    private JButton checkButton;
    
    public GUI_CrearCuentaAdministracion() {
        initialize();
    }

    private void initialize() {
    	frame = new JFrame("VAULTCODE - Crear cuenta desde administración");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 350);
        frame.setLocationRelativeTo(null);

        // Panel de fondo con la imagen
        ImagePanel backgroundPanel = new ImagePanel("media/background.png");
        backgroundPanel.setLayout(new BorderLayout());

        // Panel para los componentes (con fondo transparente)
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false); // Para que no tape la imagen de fondo
        contentPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Datos del trabajador", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(255, 94, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(titleLabel, gbc);

        JLabel userLabel = new JLabel("Nombre de usuario:");
        userLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        contentPanel.add(userLabel, gbc);

        userField.setBackground(new Color(50, 50, 50));
        userField.setForeground(Color.WHITE);
        userField.setCaretColor(new Color(255, 94, 0));
        gbc.gridx = 1;
        contentPanel.add(userField, gbc);
        
        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        contentPanel.add(passLabel, gbc);

        passField.setBackground(new Color(50, 50, 50));
        passField.setForeground(Color.WHITE);
        passField.setCaretColor(new Color(255, 94, 0));
        gbc.gridx = 1;
        contentPanel.add(passField, gbc);
        
        JLabel repeatPassLabel = new JLabel("Repita la contaseña:");
        repeatPassLabel.setForeground(Color.WHITE);
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        contentPanel.add(repeatPassLabel, gbc);

        repeatPassField.setBackground(new Color(50, 50, 50));
        repeatPassField.setForeground(Color.WHITE);
        repeatPassField.setCaretColor(new Color(255, 94, 0));
        gbc.gridx = 1;
        contentPanel.add(repeatPassField, gbc);
        
        JLabel nameLabel = new JLabel("Nombre:");
        nameLabel.setForeground(Color.WHITE);
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        contentPanel.add(nameLabel, gbc);

        nameField.setBackground(new Color(50, 50, 50));
        nameField.setForeground(Color.WHITE);
        nameField.setCaretColor(new Color(255, 94, 0));
        gbc.gridx = 1;
        contentPanel.add(nameField, gbc);

        JLabel surnamesLabel = new JLabel("Apellidos:");
        surnamesLabel.setForeground(Color.WHITE);
        gbc.gridy = 5;
        gbc.gridx = 0;
        contentPanel.add(surnamesLabel, gbc);

        surnameField.setBackground(new Color(50, 50, 50));
        surnameField.setForeground(Color.WHITE);
        surnameField.setCaretColor(new Color(255, 94, 0));
        gbc.gridx = 1;
        contentPanel.add(surnameField, gbc);
        
        JLabel DNILabel = new JLabel("DNI:");
        DNILabel.setForeground(Color.WHITE);
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        contentPanel.add(DNILabel, gbc);

        DNIField.setBackground(new Color(50, 50, 50));
        DNIField.setForeground(Color.WHITE);
        DNIField.setCaretColor(new Color(255, 94, 0));
        gbc.gridx = 1;
        contentPanel.add(DNIField, gbc);
        
        JLabel telfLabel = new JLabel("Teléfono:");
        telfLabel.setForeground(Color.WHITE);
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        contentPanel.add(telfLabel, gbc);

        telfField.setBackground(new Color(50, 50, 50));
        telfField.setForeground(Color.WHITE);
        telfField.setCaretColor(new Color(255, 94, 0));
        gbc.gridx = 1;
        contentPanel.add(telfField, gbc);
        
        JLabel rolLabel = new JLabel("Rol:");
        rolLabel.setForeground(Color.WHITE);
        gbc.gridy = 8;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        contentPanel.add(rolLabel, gbc);

        rolComboBox.setBackground(new Color(50, 50, 50));
        rolComboBox.setForeground(Color.WHITE);
        // TODO esto sería mejor que cogiese los roles de algún lugar aparte y los recorriera (TP2)
        rolComboBox.addItem("Administrador");
        rolComboBox.addItem("Servicios centrales");
        rolComboBox.addItem("Pasivo");
        rolComboBox.addItem("Riesgo");
        gbc.gridx = 1;
        contentPanel.add(rolComboBox, gbc);

        checkButton = new JButton("Comprobar trabajador");
        checkButton.setBackground(new Color(255, 94, 0));
        checkButton.setForeground(Color.BLACK);
        checkButton.setFocusPainted(false);
        checkButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        checkButton.addActionListener(e -> {
        	// TODO coger toda la información y pasarla como un transfer a la funcion correcta
        });

        // Etiqueta para mostrar errores
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 12));
        errorLabel.setPreferredSize(new Dimension(200, 25));
        gbc.gridy = 10;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Permite que el JLabel de error se expanda horizontalmente
        contentPanel.add(errorLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        contentPanel.add(checkButton, gbc);

        backgroundPanel.add(contentPanel, BorderLayout.CENTER);
        frame.setContentPane(backgroundPanel);
        frame.setVisible(true);
    }
    
	
    // Panel personalizado para el fondo con imagen
    private static class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
    
	@Override
	public void actualizar(Context c) {
		// TODO Auto-generated method stub
		
	}

}
