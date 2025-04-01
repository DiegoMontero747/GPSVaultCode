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
	private JLabel titleLabel = new JLabel("Ingresar datos de la tarjeta", SwingConstants.CENTER);
	private RoundedButton actionButton = new RoundedButton("Ingresar");
	private JLabel errorLabel = new JLabel("");
	

	public GUI_CrearTarjetaDebito() {
		initialize();
		
		
	}

	private void initialize() {
		
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
		// crea formulario
		formulario = new GeneralForm(contentPanel, 8, titleLabel, actionButton, errorLabel);

		formulario.setInfoText(0, "Nombre");
		formulario.setInfoText(1, "Apellidos");
		formulario.setInfoText(2, "DNI");
		formulario.setInfoText(3, "Numero de tarjeta");
		formulario.setInfoText(4, "IBAN");
		formulario.setInfoText(5, "Fecha de nacimiento");
		formulario.setInfoText(6, "Direccion");
		formulario.setInfoText(7, "Telefono");

		// boton del formulario
		actionButton.addActionListener(e -> {
			String nombre = formulario.getText(0);
			String apellidos = formulario.getText(1);
			String DNI = formulario.getText(2);
			String numeroTarjeta = formulario.getText(3);
			String numeroCuenta = formulario.getText(4);
			String fecha = formulario.getText(5);
			String dir = formulario.getText(6);
			String tlfn = formulario.getText(7);
			// Llamamos al SA para crear la tarjeta
			TTarjeta tarjeta = new TTarjeta(nombre, apellidos, DNI, numeroTarjeta, dir, tlfn, fecha);
			tarjeta.setNumeroCuenta(numeroCuenta);
			Controller.getInstance().handleRequest(new Context(Evento.CREAR_TARJETA_DEBITO, tarjeta));
			
			
		});

		this.setVisible(true);

		
	}



	@Override
	public void actualizar(Context c) {
		switch (c.getEvento()) {
		case CREAR_TARJETA_OK:
			
			formulario.mostrarMensaje("Tarjeta de débito creada con éxito.", false);
			break;
		case ERROR_TIPO_DOCUMENTO_INVALIDO:
			
			formulario.mostrarMensaje("Número de documento inválido.", true);
			break;
		case CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE:
			
			formulario.mostrarMensaje("La cuenta IBAN ingresada no existe.", true);
			break;
		case CREAR_TARJETA_ERROR_TARJETA_NULL:
			
			formulario.mostrarMensaje("La tarjeta es null.", true);
			break;
		case CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS:
			
			formulario.mostrarMensaje("Rellene todos los campos.", true);
			break;
		case CREAR_TARJETA_ERROR_DATOS_NULOS:
			
			formulario.mostrarMensaje("Rellene todos los campos.", true);
			break;
		case CREAR_TARJETA_ERROR_DB:
			
			formulario.mostrarMensaje("Error en la base de datos al registrar la tarjeta.", true);
			break;
		case ERROR_NUMERO_TELEFONO_INVALIDO:
			
			formulario.mostrarMensaje("Número de teléfono inválido.", true);
			break;
		case ERROR_CADENA_NO_ALFABETICA:
			
			formulario.mostrarMensaje("El nombre solo puede contener letras.", true);
			break;
		}
	}
}
