package presentacion.GUI;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageInputStream;
import javax.swing.border.LineBorder;

import presentacion.Controller.Controller;
import negocio.ManejoSesiones.TSesion;
import presentacion.Controller.Context;
import presentacion.Controller.Evento;

public class GUI_InicioSesion extends JPanel implements ObservadorGUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		this.setBackground(Color.RED);
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

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;

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
		loginButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		loginButton.addActionListener(e -> {
			String username = userField.getText();
			String password = new String(passField.getPassword()); // Convertir password a String
			userField.setBorder(new LineBorder(Color.WHITE));
			passField.setBorder(new LineBorder(Color.WHITE));
			Controller.getInstance()
					.handleRequest(new Context(Evento.INICIA_CUENTA, new TSesion(username, password, null)));
		});

		// Etiqueta para mostrar errores
		errorLabel = new JLabel("");
		errorLabel.setForeground(Color.RED);
		errorLabel.setFont(new Font("Arial", Font.BOLD, 12));
		errorLabel.setPreferredSize(new Dimension(200, 25));
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL; // Permite que el JLabel de error se expanda horizontalmente
		contentPanel.add(errorLabel, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		contentPanel.add(loginButton, gbc);

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

				}
				if (evento == Evento.INICIO_SESION_ERROR_CONTRASENYA_INCORRECTA) {
					contador++;
				}
				if (contador >= 3) {
					bloquearSesion();
			}
			}
			
			
			

		}

		private class GUI_Bienvenida extends JFrame {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public GUI_Bienvenida() {
				init();
			}

			private void init() {
				try {
					File file = new File("/media/gif-ventana-bienvenida");
					ImageInputStream inputStream = ImageIO.createImageInputStream(file);
					Iterator<ImageReader> readers = ImageIO.getImageReadersBySuffix("gif");

					if (!readers.hasNext()) {
						throw new IOException("No se encontró un lector para GIF.");
					}

					ImageReader reader = readers.next();
					reader.setInput(inputStream, false);

					// Calcular la duración total del GIF
					int numFrames = reader.getNumImages(true);
					int duracionTotal = 0;

					for (int i = 0; i < numFrames; i++) {
						IIOMetadata metadata = reader.getImageMetadata(i);
						String metaFormat = metadata.getNativeMetadataFormatName();
						IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormat);

						IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");

						if (graphicsControlExtensionNode != null) {
							String delayTime = graphicsControlExtensionNode.getAttribute("delayTime");
							duracionTotal += Integer.parseInt(delayTime); // Está en centésimas de segundo
						}
					}

					// Convertir a milisegundos
					duracionTotal *= 10;

					// Mostrar el GIF en un JLabel
					ImageIcon gif = new ImageIcon("tu_gif.gif");
					JLabel label = new JLabel(gif);
					add(label);

					// Configurar JFrame
					setSize(gif.getIconWidth(), gif.getIconHeight());
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					setLocationRelativeTo(null);
					setVisible(true);

					// Cerrar la ventana automáticamente después de la duración total del GIF
					Timer timer = new Timer();
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							dispose(); // Cierra la ventana
							timer.cancel(); // Cancela la tarea
						}
					}, duracionTotal);

				} catch (Exception e) {
					mostrarMensajeError("No se pudo cargar la pantalla de bienvenida");

				}
			}

			private static IIOMetadataNode getNode(IIOMetadataNode root, String nodeName) {
				for (int i = 0; i < root.getLength(); i++) {
					if (root.item(i).getNodeName().equalsIgnoreCase(nodeName)) {
						return (IIOMetadataNode) root.item(i);
					}
				}
				return null;
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
			errorLabel.setForeground(new Color(255, 94, 0));

			errorLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Ajusta el tamaño y tipo de fuente
			errorLabel.setOpaque(false); // Asegura que el fondo sea transparente

			errorLabel.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
				@Override
				public void paint(Graphics g, JComponent c) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

					String text = ((JLabel) c).getText();
					FontMetrics fm = g2.getFontMetrics(errorLabel.getFont());
					int x = (errorLabel.getWidth() - fm.stringWidth(text)) / 2;
					int y = (errorLabel.getHeight() + fm.getAscent()) / 2 - fm.getDescent();

					// Dibujar el borde blanco (desplazando el texto en todas direcciones)
					g2.setColor(Color.BLACK);
					for (int i = -1; i <= 1; i++) {
						for (int j = -1; j <= 1; j++) {
							if (i != 0 || j != 0) {
								g2.drawString(text, x + i, y + j);
							}
						}
					}

					// Dibujar el texto rojo encima
					g2.setColor(new Color(255, 94, 0));
					g2.drawString(text, x, y);
				}
			});

			this.revalidate();
			this.repaint();
		}
	}

