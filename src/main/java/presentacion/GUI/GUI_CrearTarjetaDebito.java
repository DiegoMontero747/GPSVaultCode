package presentacion.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import negocio.Tarjetas.*;
import negocio.Factory.ResultContext;
import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;
import presentacion.GUI_Components.GeneralForm;
import presentacion.GUI_Components.RoundedComponents.RoundedButton;

import java.awt.*;

public class GUI_CrearTarjetaDebito extends JPanel implements ObservadorGUI {

	private static final long serialVersionUID = 1L;

	private GeneralForm formulario;
	private JLabel titleLabel = new JLabel("Datos del cliente", SwingConstants.CENTER);
	private RoundedButton actionButton = new RoundedButton("Ingresar");
	private JLabel errorLabel = new JLabel("");
	//
	private JFrame frame;
	private JTextField nombreField, apellidosField, documentoField, entidadField, oficinaField, digitoControlField,
			cuentaField;
	private JTextField direccionField, telefonoField, fechaNacimientoField;
	private JComboBox<String> tipoDocumentoCombo;
	private JButton crearTarjetaButton, cancelarButton;
	//

	public GUI_CrearTarjetaDebito() {
		initialize();
		// BORRAR
		frame.setVisible(true);
		//
	}

	private void initialize() {
		/*
		 * BORRAR frame = new JFrame("Crear Tarjeta de Débito - VAULTCODE");
		 * frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); frame.setSize(600,
		 * 700); frame.setLocationRelativeTo(null); frame.setLayout(new BorderLayout());
		 * frame.getContentPane().setBackground(new Color(110, 110, 110));
		 */
		this.setPreferredSize(new Dimension(800, 600));
		this.setLayout(new BorderLayout());

		// fondo
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBackground(new Color(30, 30, 30));
		backgroundPanel.setLayout(new BorderLayout());
		this.add(backgroundPanel, BorderLayout.CENTER);

		// panel para componentes (fondo transparente)
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.setOpaque(false);
		backgroundPanel.add(contentPanel, BorderLayout.CENTER);

		// titulo del formulario
		titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
		titleLabel.setForeground(new Color(255, 94, 0));

		// etiqueta de error del formulario
		errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
		errorLabel.setPreferredSize(new Dimension(300, 40));

		// crea formulario
		formulario = new GeneralForm(contentPanel, 7, titleLabel, actionButton, errorLabel);

		formulario.setInfoText(0, "Nombre");
		formulario.setInfoText(1, "Apellidos");
		formulario.setInfoText(2, "DNI");
		formulario.setInfoText(3, "IBAN");
		formulario.setInfoText(4, "Fecha de nacimiento");
		formulario.setInfoText(5, "Direccion");
		formulario.setInfoText(6, "Telefono");

		// boton del formulario
		actionButton.addActionListener(e -> {
			String nombre = formulario.getText(0);
			String apellidos = formulario.getText(1);
			String DNI = formulario.getText(2);
			String IBAN = formulario.getText(3);
			String fecha = formulario.getText(4);
			String dir = formulario.getText(5);
			String tlfn = formulario.getText(6);
			// Llamamos al SA para crear la tarjeta
			TTarjeta tarjeta = new TTarjeta(nombre, apellidos, DNI, IBAN, dir, tlfn, fecha);
			SATarjetasImp sat = new SATarjetasImp();
			ResultContext result = sat.crearTarjetaDebito(tarjeta);
			// Evaluamos la respuesta del SA
			switch (result.getEvento()) {
			case CREAR_TARJETA_OK:
				mensaje("Tarjeta de débito creada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE, true);
				break;
			case ERROR_TIPO_DOCUMENTO_INVALIDO:
				mensaje("Número de documento inválido.", "Error", JOptionPane.ERROR_MESSAGE, true);
				break;
			case CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE:
				mensaje("La cuenta IBAN ingresada no existe.", "Error", JOptionPane.ERROR_MESSAGE, false);
				break;
			case CREAR_TARJETA_ERROR_TARJETA_NULL:
				mensaje("La tarjeta es null.", "Error", JOptionPane.ERROR_MESSAGE, false);
				break;
			case CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS:
				mensaje("Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE, false);
				break;
			case CREAR_TARJETA_ERROR_DATOS_NULOS:
				mensaje("Hay campos a null, todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE,
						false);
				break;
			case CREAR_TARJETA_ERROR_DB:
				mensaje("Error en la base de datos al registrar la tarjeta.", "Error", JOptionPane.ERROR_MESSAGE,
						false);
				break;
			}
		});

		this.setVisible(true);

		/*
		 * BORRAR JLabel titleLabel = new JLabel("CREAR TARJETA DE DÉBITO",
		 * JLabel.CENTER); titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		 * titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
		 * titleLabel.setForeground(Color.BLACK); frame.add(titleLabel,
		 * BorderLayout.NORTH);
		 * 
		 * JPanel contentPanel = new JPanel(new GridLayout(14, 1, 10, 10));
		 * contentPanel.setBorder(new EmptyBorder(20, 50, 20, 50));
		 * 
		 * contentPanel.add(createLabeledField("Nombre:", nombreField = new
		 * JTextField())); contentPanel.add(createLabeledField("Apellidos:",
		 * apellidosField = new JTextField()));
		 * 
		 * contentPanel.add(createLabeledCombo("Tipo de documento:", tipoDocumentoCombo
		 * = new JComboBox<>(new String[] { "DNI", "NIE" })));
		 * contentPanel.add(createLabeledField("Número de documento:", documentoField =
		 * new JTextField()));
		 * 
		 * contentPanel.add(createLabeledField("Dirección:", direccionField = new
		 * JTextField())); contentPanel.add(createLabeledField("Teléfono:",
		 * telefonoField = new JTextField())); contentPanel
		 * .add(createLabeledField("Fecha de nacimiento (YYYY-MM-DD):",
		 * fechaNacimientoField = new JTextField()));
		 * 
		 * contentPanel.add(new JLabel("Número de cuenta (IBAN):")); JPanel ibanPanel =
		 * new JPanel(new GridLayout(1, 4, 5, 5)); entidadField = new JTextField(4);
		 * oficinaField = new JTextField(4); digitoControlField = new JTextField(2);
		 * cuentaField = new JTextField(10); ibanPanel.add(entidadField);
		 * ibanPanel.add(oficinaField); ibanPanel.add(digitoControlField);
		 * ibanPanel.add(cuentaField); contentPanel.add(ibanPanel);
		 * 
		 * JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		 * crearTarjetaButton = createStyledButton("CREAR TARJETA"); cancelarButton =
		 * createStyledButton("CANCELAR"); buttonPanel.add(crearTarjetaButton);
		 * buttonPanel.add(cancelarButton);
		 * 
		 * frame.add(contentPanel, BorderLayout.CENTER); frame.add(buttonPanel,
		 * BorderLayout.SOUTH);
		 * 
		 * cancelarButton.addActionListener(e -> frame.dispose());
		 * 
		 * crearTarjetaButton.addActionListener(e -> crearTarjeta());
		 */
	}

	/*
	 * private void crearTarjeta() { String nombre = nombreField.getText().trim();
	 * String apellidos = apellidosField.getText().trim(); String numeroDocumento =
	 * documentoField.getText().trim(); String direccion =
	 * direccionField.getText().trim(); String telefono =
	 * telefonoField.getText().trim(); String fechaNacimiento =
	 * fechaNacimientoField.getText().trim(); String entidad =
	 * entidadField.getText().trim(); String oficina =
	 * oficinaField.getText().trim(); String digitoControl =
	 * digitoControlField.getText().trim(); String cuenta =
	 * cuentaField.getText().trim();
	 * 
	 * if (entidad.isBlank() || oficina.isBlank() || digitoControl.isBlank() ||
	 * cuenta.isBlank()) { mensaje("Debe completar todos los campos del IBAN.",
	 * "Error", JOptionPane.ERROR_MESSAGE, false); return; }
	 * 
	 * // Construcción del objeto TTarjeta TTarjeta tarjeta = new TTarjeta(nombre,
	 * apellidos, tipoDocumento, numeroDocumento, entidad + oficina + digitoControl
	 * + cuenta, direccion, telefono, fechaNacimiento);
	 * 
	 * // Llamamos al SA para crear la tarjeta SATarjetasImp sat = new
	 * SATarjetasImp(); ResultContext result = sat.crearTarjetaDebito(tarjeta);
	 * 
	 * // Evaluamos la respuesta del SA switch (result.getEvento()) { case
	 * CREAR_TARJETA_OK: mensaje("Tarjeta de débito creada con éxito.", "Éxito",
	 * JOptionPane.INFORMATION_MESSAGE, true); break; case
	 * ERROR_TIPO_DOCUMENTO_INVALIDO: mensaje("Número de documento inválido.",
	 * "Error", JOptionPane.ERROR_MESSAGE, true); break; case
	 * CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE:
	 * mensaje("La cuenta IBAN ingresada no existe.", "Error",
	 * JOptionPane.ERROR_MESSAGE, false); break; case
	 * CREAR_TARJETA_ERROR_TARJETA_NULL: mensaje("La tarjeta es null.", "Error",
	 * JOptionPane.ERROR_MESSAGE, false); break; case
	 * CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS:
	 * mensaje("Todos los campos son obligatorios.", "Error",
	 * JOptionPane.ERROR_MESSAGE, false); break; case
	 * CREAR_TARJETA_ERROR_DATOS_NULOS:
	 * mensaje("Hay campos a null, todos los campos son obligatorios.", "Error",
	 * JOptionPane.ERROR_MESSAGE, false); break; case CREAR_TARJETA_ERROR_DB:
	 * mensaje("Error en la base de datos al registrar la tarjeta.", "Error",
	 * JOptionPane.ERROR_MESSAGE, false); break; } }
	 */

	private void mensaje(String msg, String title, int msgType, boolean limpiar) {
		JOptionPane.showMessageDialog(frame, msg, title, msgType);
		if (limpiar) {
			limpiarCampos();
		}
	}

	private void limpiarCampos() {
		nombreField.setText("");
		apellidosField.setText("");
		documentoField.setText("");
		direccionField.setText("");
		telefonoField.setText("");
		fechaNacimientoField.setText("");
		entidadField.setText("");
		oficinaField.setText("");
		digitoControlField.setText("");
		cuentaField.setText("");
		tipoDocumentoCombo.setSelectedIndex(0);
	}

	private JButton createStyledButton(String text) {
		JButton button = new JButton(text);
		button.setBackground(new Color(88, 88, 88));
		button.setForeground(Color.WHITE);
		button.setFont(new Font("Arial", Font.BOLD, 16));
		return button;
	}

	private JPanel createLabeledField(String labelText, JTextField textField) {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		JLabel label = new JLabel(labelText);
		setLabelStyle(label);
		panel.add(label);
		panel.add(textField);
		return panel;
	}

	private JPanel createLabeledCombo(String labelText, JComboBox<String> comboBox) {
		JPanel panel = new JPanel(new GridLayout(2, 1));
		JLabel label = new JLabel(labelText);
		setLabelStyle(label);
		panel.add(label);
		panel.add(comboBox);
		return panel;
	}

	private void setLabelStyle(JLabel label) {
		label.setFont(new Font("Arial", Font.BOLD, 14));
		label.setForeground(Color.BLACK);
	}

	@Override
	public void actualizar(Context c) {
		// TODO: Implementación de actualización
	}
}
