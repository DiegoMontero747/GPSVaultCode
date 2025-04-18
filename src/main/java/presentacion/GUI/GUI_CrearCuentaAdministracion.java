package presentacion.GUI;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import negocio.ManejoSesiones.Roles;
import negocio.ManejoSesiones.TCrearCuentaAdm;
import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;
import presentacion.GUI_Components.GeneralForm;
import presentacion.GUI_Components.RoundedComponents.RoundedButton;

public class GUI_CrearCuentaAdministracion extends JPanel implements ObservadorGUI {
	/** 
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// formulario
	private GeneralForm formulario;
	private JLabel titleLabel = new JLabel("Datos del trabajador", SwingConstants.CENTER);
	private RoundedButton actionButton = new RoundedButton("Ingresar");
	private JLabel errorLabel = new JLabel("");

	public GUI_CrearCuentaAdministracion() {
		initialize();
	}

	private void initialize() {
		// en caso de necesitar que la clase sea jpanel this.setMinimumSize(new
		// Dimension(800, 600));
		this.setMinimumSize(new Dimension(800, 600));
		this.setLayout(new BorderLayout());

		// Panel de fondo con la imagen
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBackground(new Color(30, 30, 30));
		backgroundPanel.setLayout(new BorderLayout());
		// en caso de necesitar que la clase sea jpanel, anyadir
		// backgroundPanel.setPreferredSize(new Dimension(800, 600));
		this.add(backgroundPanel, BorderLayout.CENTER);

		// Panel para los componentes (con fondo transparente)
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.setOpaque(false);
		backgroundPanel.add(contentPanel, BorderLayout.CENTER); // Panel con componentes también añadido al centro

		// Titulo del formulario
		titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
		titleLabel.setForeground(new Color(255, 94, 0));

		// Etiqueta de error del formulario
		errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
		errorLabel.setPreferredSize(new Dimension(300, 40));

		// Se crea el formulario
		formulario = new GeneralForm(contentPanel, 7, titleLabel, actionButton, errorLabel);

		// Se obtienen los valores del combobox
		ArrayList<String> comboBoxArray = new ArrayList<String>();
		for (Roles item : Roles.values()) {
			String formattedItem = item.toString().replace("_", " ").toLowerCase();
			formattedItem = Character.toUpperCase(formattedItem.charAt(0)) + formattedItem.substring(1);
			comboBoxArray.add(formattedItem);
		}

		// Se ajusta el formulario
		formulario.setPassword(1); // el segundo campo de texto es de tipo contrasenya
		formulario.setComboBox(6); // el ultimo campo es un combobox
		formulario.setComboBoxInfo(6, comboBoxArray); // se rellena la informacion del combo box
		formulario.setInfoText(0, "Nombre de usuario"); // el campo de texto del usuario
		formulario.setInfoText(1, "Contraseña"); // el campo de texto de la contrasenya
		formulario.setInfoText(2, "Nombre"); // el campo de texto del nombre
		formulario.setInfoText(3, "Apellidos"); // el campo de texto de los apellidos
		formulario.setInfoText(4, "DNI"); // el campo de texto del DNI
		formulario.setInfoText(5, "Telefono"); // el campo de texto del telefono

		// Boton del formulario
		actionButton.addActionListener(e -> {
			String nombre = formulario.getText(2);
			String apellidos = formulario.getText(3);
			String password = new String(formulario.getText(1)); // Convertir password a String
			String DNI = formulario.getText(4);
			String usuario = formulario.getText(0);
			String rol = formulario.getText(6);
			String telf = formulario.getText(5);
			Controller.getInstance().handleRequest(new Context(Evento.CREAR_CUENTA_ADM,
					new TCrearCuentaAdm(nombre, apellidos, password, DNI, usuario, rol, telf)));
		});

		this.setVisible(true);
	}

	@Override
	public void actualizar(Context c) {
		Evento evento = (Evento) c.getEvento();
		if (c != null) {
			switch (evento) {
			case CREAR_CUENTA_ADM_EXITO:
				formulario.mostrarMensaje("Cuenta creada correctamente.", false);
				break;

			case CREAR_CUENTA_ADM_ERROR_DATOS_NULOS:
			case CREAR_CUENTA_ADM_ERROR_DATOS_VACIOS:
				formulario.mostrarMensaje("Rellene todos los campos.", true);
				break;

			case CREAR_CUENTA_ADM_ERROR_DNI_ENCONTRADO:
				formulario.mostrarMensaje("DNI ya existe.", true);
				break;

			case CREAR_CUENTA_ADM_ERROR_FORMATO_DNI:
				formulario.mostrarMensaje("Formato de DNI incorrecto.", true);
				break;

			case CREAR_CUENTA_ADM_ERROR_TEL_INCORRECTO:
				formulario.mostrarMensaje("Formato de teléfono incorrecto.", true);
				break;
			case CREAR_CUENTA_ADM_ERROR_PASSWD_INCORRECTA:
				formulario.mostrarMensaje("Formato contrasenya incorrecto.", true);
				break;
			}
		}

	}

	public static void main(String[] args) {
		// Crear y mostrar la interfaz
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new GUI_CrearCuentaAdministracion().setVisible(true);
			}
		});
	}
}
