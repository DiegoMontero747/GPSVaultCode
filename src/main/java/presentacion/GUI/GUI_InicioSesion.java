package presentacion.GUI;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;

import presentacion.Controller.Controller;
import negocio.ManejoSesiones.TSesion;
import presentacion.Controller.Context;
import presentacion.Controller.Evento;
import presentacion.GUI_Components.GeneralForm;
import presentacion.GUI_Components.RoundedComponents.RoundedButton;

public class GUI_InicioSesion extends JPanel implements ObservadorGUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int contador = 0;
	private static final int TIEMPO_BLOQUEO = 60000; // 1 minuto de bloqueo
	private Timer timer;

	// formulario
	private GeneralForm formulario;
	private JLabel titleLabel = new JLabel("VAULTCODE", SwingConstants.CENTER);
	private RoundedButton actionButton = new RoundedButton("Ingresar");
	private JLabel errorLabel = new JLabel("");

	public GUI_InicioSesion() {
		initialize();
	}

	private void initialize() {
		this.setPreferredSize(new Dimension(800, 600));
		this.setLayout(new BorderLayout());

		// Panel de fondo con la imagen
		ImagePanel backgroundPanel = new ImagePanel("media/background.png");
		backgroundPanel.setLayout(new BorderLayout());
		this.add(backgroundPanel, BorderLayout.CENTER); // Fondo añadido en el centro

		// Panel para los componentes (con fondo transparente)
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.setOpaque(false);
		backgroundPanel.add(contentPanel, BorderLayout.CENTER); // Panel con componentes también añadido al centro

		// Titulo del formulario
		titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
		titleLabel.setForeground(new Color(255, 94, 0));
		
		// Boton del formulario
		actionButton.addActionListener(e -> {
			String username = formulario.getText(0);
			String password = new String(formulario.getText(1)); // Convertir password a String
			Controller.getInstance()
					.handleRequest(new Context(Evento.INICIA_CUENTA, new TSesion(username, password, null)));
		});
		
		// Etiqueta de error del formulario
		errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
		errorLabel.setPreferredSize(new Dimension(300, 40));
		
		// Se crea el formulario
		formulario = new GeneralForm(contentPanel, 2, titleLabel, actionButton, errorLabel);
		
		// Se ajusta el formulario
		formulario.setPassword(1);					// el segundo campo de texto es de tipo contrasenya
		formulario.setInfoText(0, "Usuario");		// el campo de texto del usuario
		formulario.setInfoText(1, "Contraseña");	// el campo de texto de la contrasenya
		
		this.setVisible(true);
	}

	// Panel personalizado para el fondo con imagen
	private static class ImagePanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Image backgroundImage;

		// Panel personalizado para el fondo con imagen
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
		if (c != null) {
			switch (evento) {
				case INICIO_SESION_OK:
					Controller.getInstance().handleRequest(new Context(Evento.GUI_PRINCIPAL, c.getDato()));
					contador = 0;
					break;

				case INICIO_SESION_ERROR_USUARIO_INEXISTENTE:
					formulario.mostrarMensaje("Usuario no encontrado.", true);
					break;

				case INICIO_SESION_ERROR_CONTRASENYA_INCORRECTA:
					formulario.mostrarMensaje("Contraseña incorrecta.", true);
					break;

				case INICIO_SESION_ERROR_CONTRASENYA_INCOMPLETA:
					formulario.mostrarMensaje("Debe ingresar una contraseña.", true);
					break;

				case INICIO_SESION_ERROR_USUARIO_INCOMPLETO:
					formulario.mostrarMensaje("Debe ingresar un usuario.", true);
					break;
			}
			if (evento == Evento.INICIO_SESION_ERROR_CONTRASENYA_INCORRECTA) {
				contador++;
			}
			if (contador >= 3) {
				bloquearSesion();
			}
		}
	}
	
	private void bloquearSesion() {
		// Deshabilitar el botón de inicio de sesión
		actionButton.setEnabled(false);

		// Mostrar mensaje informando que la cuenta está bloqueada
		errorLabel.setText("Intente en 1 minuto.");

		// Crear un temporizador para habilitar el botón después de 3 minutos
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// Habilitar el botón de inicio de sesión y restablecer el contador
				SwingUtilities.invokeLater(() -> {
					actionButton.setEnabled(true);
					errorLabel.setText("");
					contador = 0;
				});
			}
		}, TIEMPO_BLOQUEO); // 3 minutos de bloqueo
	}
}
