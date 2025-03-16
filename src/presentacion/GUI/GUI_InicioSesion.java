package presentacion.GUI;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.swing.border.LineBorder;

import presentacion.Controller.Controller;
import negocio.ManejoSesiones.TSesion;
import presentacion.Controller.Context;
import presentacion.Controller.Evento;

public class GUI_InicioSesion implements ObservadorGUI {
    private JFrame frame;
    private JLabel errorLabel;
    private int contador = 0;
    private static final int TIEMPO_BLOQUEO = 60000; // 1 minuto de bloqueo
    private Timer timer;

    JButton loginButton;
    private JTextField userField = new JTextField(15);
    private JPasswordField passField = new JPasswordField(15);
    
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

        loginButton = new JButton("Ingresar");
        loginButton.setBackground(new Color(255, 94, 0));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword()); // Convertir password a String
            userField.setBorder(new LineBorder(Color.WHITE));
            passField.setBorder(new LineBorder(Color.WHITE));
            Controller.getInstance().handleRequest(new Context(Evento.INICIA_CUENTA, new TSesion(username, password, null)));
        });

        // Etiqueta para mostrar errores
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Permite que el JLabel de error se expanda horizontalmente
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
		if (c == null) return;

	    switch (evento) {
	        case INICIO_SESION_OK:
	            JOptionPane.showMessageDialog(frame, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	            contador = 0;
	            break;

	        case INICIO_SESION_ERROR_USUARIO_INEXISTENTE:
	            mostrarMensajeError("Usuario no encontrado.");
	            break;

	        case INICIO_SESION_ERROR_CONTRASENYA_INCORRECTA:
	            mostrarMensajeError("Contraseña incorrecta.");
	            break;

	        case INICIO_SESION_ERROR_CONTRASENYA_INCOMPLETA:
	            mostrarMensajeError("Debe ingresar una contraseña.");
	            break;

	        case INICIO_SESION_ERROR_USUARIO_INCOMPLETO:
	            mostrarMensajeError("Debe ingresar un usuario.");
	            break;

	        default:
	            mostrarMensajeError("Error desconocido.");
	            break;
	    }
	    if(evento != Evento.INICIO_SESION_OK) {
	    	contador++;
	    }
	    if(contador >= 3) {
	    	bloquearSesion();
	    }
        
	}
	
	 private void bloquearSesion() {
	        // Deshabilitar el botón de inicio de sesión
	        loginButton.setEnabled(false);
	        
	        // Mostrar mensaje informando que la cuenta está bloqueada
	        errorLabel.setText("Intente en 1 minuto.");
	        
	        // Crear un temporizador para habilitar el botón después de 3 minutos
	        timer = new Timer();
	        timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	                // Habilitar el botón de inicio de sesión y restablecer el contador
	                SwingUtilities.invokeLater(() -> {
	                    loginButton.setEnabled(true);
	                    errorLabel.setText("");
	                    contador = 0;
	                });
	            }
	        }, TIEMPO_BLOQUEO); // 3 minutos de bloqueo
	    }
	
	private void mostrarMensajeError(String mensaje) {
		errorLabel.setText(mensaje);
	    errorLabel.setForeground(Color.RED);
	    frame.revalidate();
	    frame.repaint();
	}
}
