package presentacion.GUI;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import presentacion.Controller.Controller;
import negocio.ManejoSesiones.TSesion;
import presentacion.Controller.Context;
import presentacion.Controller.Evento;

public class GUI_InicioSesion implements ObservadorGUI {
    private JFrame frame;
    private JLabel errorLabel;

    private JTextField userField = new JTextField(15);
    private JPasswordField passField = new JPasswordField(15);
    
    public static void main(String[] args) {
    	GUI_InicioSesion sesion = new GUI_InicioSesion();
    }
    
    public GUI_InicioSesion() {
        initialize();
    }

    private void initialize() {
    	frame = new JFrame("VAULTCODE - Iniciar Sesión");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 350);
        frame.setLocationRelativeTo(null);

        // Panel de fondo con la imagen
        ImagePanel backgroundPanel = new ImagePanel("media/low-poly-grid-haikei.png");
        backgroundPanel.setLayout(new BorderLayout());

        // Panel para los componentes (con fondo transparente)
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false); // Para que no tape la imagen de fondo
        contentPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("VAULTCODE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(255, 94, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(titleLabel, gbc);

        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPanel.add(userLabel, gbc);

        userField.setBackground(new Color(50, 50, 50));
        userField.setForeground(Color.WHITE);
        userField.setCaretColor(new Color(255, 94, 0));
        gbc.gridx = 1;
        contentPanel.add(userField, gbc);

        JLabel passLabel = new JLabel("Contraseña:");
        passLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(passLabel, gbc);

        passField.setBackground(new Color(50, 50, 50));
        passField.setForeground(Color.WHITE);
        passField.setCaretColor(new Color(255, 94, 0));
        gbc.gridx = 1;
        contentPanel.add(passField, gbc);

        JButton loginButton = new JButton("Ingresar");
        loginButton.setBackground(new Color(255, 94, 0));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
     // ActionListener del botón
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword()); // Convertir password a String
            
            // los bordes de los cuadros de texto vuelven al color normal
       	 	userField.setBorder(new LineBorder(Color.WHITE));
       	 	passField.setBorder(new LineBorder(Color.WHITE));

            Controller.getInstance().handleRequest(new Context(Evento.INICIA_CUENTA, new TSesion(username, password, null)));
        });
        
        //etiqueta para mostrar errores
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridy = 4;
        contentPanel.add(errorLabel, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        contentPanel.add(loginButton, gbc);

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
		Evento evento = (Evento) c.getEvento();
        switch (evento) {
            case INICIO_SESION_OK:
                errorLabel.setText(""); // Borrar errores previos
                JOptionPane.showMessageDialog(frame, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
               // abrirSesionCorrecta();
                break;

            case INICIO_SESION_ERROR_USUARIO_INEXISTENTE:
            	JOptionPane.showMessageDialog(frame, "Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
               // errorLabel.setText("Usuario no encontrado");
                break;

            case INICIO_SESION_ERROR_CONTRASENYA_INCORRECTA:
            	JOptionPane.showMessageDialog(frame, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            	// errorLabel.setText("Contraseña incorrecta");
                break;
            case INICIO_SESION_ERROR_USUARIO_INCOMPLETO:
           	 	JOptionPane.showMessageDialog(frame, "Faltan datos", "Error", JOptionPane.ERROR_MESSAGE);
           	 	// errorLabel.setText("Contraseña incorrecta");
           	 	userField.setBorder(new LineBorder(Color.RED));
               break;
            case INICIO_SESION_ERROR_CONTRASENYA_INCOMPLETA:
           	 	JOptionPane.showMessageDialog(frame, "Faltan datos", "Error", JOptionPane.ERROR_MESSAGE);
           	 	// errorLabel.setText("Contraseña incorrecta");
           	 	passField.setBorder(new LineBorder(Color.RED));
           	 	break;
            default:
            	JOptionPane.showMessageDialog(frame, "Error desconocido", "Error", JOptionPane.ERROR_MESSAGE);
                //errorLabel.setText("Error desconocido, inténtelo de nuevo.");
                break;
        }
        
	}
	
	private void mostrarMensajeError(String mensaje) {
	    errorLabel.setText(mensaje);  // Cambia el texto del JLabel
	    errorLabel.setForeground(Color.RED);
	    errorLabel.setVisible(true);
	}
}
