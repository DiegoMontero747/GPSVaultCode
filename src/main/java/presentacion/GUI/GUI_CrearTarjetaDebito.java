package presentacion.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.bson.Document;

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
	private String numeroTarjeta;

	public GUI_CrearTarjetaDebito() {
		initialize();
	}

	private void initialize() {
		this.setMinimumSize(new Dimension(800, 600));
		this.setLayout(new BorderLayout());

		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setBackground(new Color(30, 30, 30));
		backgroundPanel.setLayout(new BorderLayout());
		this.add(backgroundPanel, BorderLayout.CENTER);

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new GridBagLayout());
		contentPanel.setOpaque(false);
		backgroundPanel.add(contentPanel, BorderLayout.CENTER);

		titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
		titleLabel.setForeground(new Color(255, 94, 0));

		errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
		errorLabel.setPreferredSize(new Dimension(300, 40));

		// Solo 2 campos: DNI y IBAN
		formulario = new GeneralForm(contentPanel, 2, titleLabel, actionButton, errorLabel);
		formulario.setInfoText(0, "DNI");
		formulario.setInfoText(1, "IBAN");

		actionButton.addActionListener(e -> {
			String DNI = formulario.getText(0);
			String numeroCuenta = formulario.getText(1);

			/*
			 * esto debería estar en la SA if (DNI.isEmpty() || numeroCuenta.isEmpty()) {
			 * formulario.mostrarMensaje("Rellene todos los campos.", true); return; }
			 */

			// esto deberia estar en la SA
			// numeroTarjeta = generarNumeroTarjetaUnico();

			TTarjeta tarjeta = new TTarjeta(DNI, numeroCuenta, "Debito");
			// tarjeta.setNumeroCuenta(numeroCuenta);

			Controller.getInstance().handleRequest(new Context(Evento.CREAR_TARJETA_DEBITO, tarjeta));
		});

		this.setVisible(true);
	}

	/*
	 * esto deberia estar en la SA private String generarNumeroTarjetaUnico() { long
	 * numero = (long) (Math.random() * 10000000000000000L); return
	 * String.format("%016d", numero); }
	 */

	@Override
	public void actualizar(Context c) {
		switch (c.getEvento()) {
		case CREAR_TARJETA_OK:
			Document docTarjeta = (Document) c.getDato();
			String numTarjeta = docTarjeta.getString("Num_Tarjeta");
			String mensaje = "Tarjeta creada con éxito: " + numeroTarjeta;
			formulario.mostrarMensaje(mensaje, false);
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
		case CREAR_TARJETA_ERROR_IBANDNI_NOASOCIADOS:
			formulario.mostrarMensaje("La cuenta no pertenece al cliente ingresado", true);
			break;
		}
	}
}