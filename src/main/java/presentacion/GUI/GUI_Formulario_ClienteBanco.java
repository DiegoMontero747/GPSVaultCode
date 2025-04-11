package presentacion.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import negocio.Cuentas.TCuenta;
import negocio.ManejoSesiones.Roles;
import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;
import presentacion.GUI_Components.GeneralForm;
import presentacion.GUI_Components.RoundedComponents.RoundedButton;

public class GUI_Formulario_ClienteBanco extends JPanel implements ObservadorGUI {
    /** TODO CAMBIAR A JPANEL Y ENLAZARLO AL BOTON CORRESPONDIENTE CUANDO ESTÉ
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// formulario
	private GeneralForm formulario;
	private JLabel titleLabel = new JLabel("Datos del cliente", SwingConstants.CENTER);
	private RoundedButton actionButton = new RoundedButton("Ingresar");
	private JLabel errorLabel = new JLabel("");
	
    public GUI_Formulario_ClienteBanco() {
    	
        initialize();
    	
    }
    
    private void initialize() {
		this.setMinimumSize(new Dimension(800, 600));// TODO cambiar a preferred cuando el TODO de arriba esté hecho
		this.setLayout(new BorderLayout());

        // Panel de fondo con la imagen
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(new Color(30, 30, 30));
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setPreferredSize(new Dimension(800, 600)); // TODO borrar cuando el TODO de arriba esté hecho
        this.add(backgroundPanel, BorderLayout.CENTER);

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
			 //TCuenta(String tipoDoc, String nombre, String apellidos, String direccion, String telefono, int numTarjetas)
			TCuenta c = new TCuenta(formulario.getText(0), formulario.getText(1), formulario.getText(2)
					, formulario.getText(3), formulario.getText(4), formulario.getText(5));
			Controller.getInstance().handleRequest(new Context(Evento.CREAR_CUENTA_BANCARIA, c));
		});
		
		// Etiqueta de error del formulario
		errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
		errorLabel.setPreferredSize(new Dimension(300, 40));
		
		// Se crea el formulario
		formulario = new GeneralForm(contentPanel, 6, titleLabel, actionButton, errorLabel);
		List<String> l = new ArrayList<String>();
		l.add("DNI");
		l.add("NIE");
		// Se ajusta el formulario
		formulario.setInfoText(0, "Nombre");				// el campo de texto del nombre
		formulario.setInfoText(1, "Apellidos");	// el campo de texto de los apellidos
		formulario.setComboBox(2);
		formulario.setComboBoxInfo(2, l); // el campo de texto de los apellidos
		formulario.setInfoText(3, "DNI / NIE");					// el campo de texto del DNI
		formulario.setInfoText(4, "Direccion");				// el campo de texto del usuario
		formulario.setInfoText(5, "Telefono");				// el campo de texto de la contrasenya
		
		this.setVisible(true);
    	
    }

	public void actualizar(Context c) {
		switch(c.getEvento()) {
		case CREAR_CUENTA_BANCARIA_ERROR_DATOS_NULOS:
			formulario.mostrarMensaje("Inserta datos" , true);
			break;
		case CREAR_CUENTA_BANCARIA_ERROR_DATOS_INCOMPLETOS:
			formulario.mostrarMensaje("Faltan datos por insertar", true);
			break;
		case ERROR_CADENA_NO_ALFABETICA:
			formulario.mostrarMensaje("Nombre y apellidos solo letras", true);
			break;
		case ERROR_TIPO_DOCUMENTO_INVALIDO:
			formulario.mostrarMensaje("Formato DNI/NIE invalido", true);
			break;
		case CREAR_CUENTA_BANCARIA_ERROR_DB:
			formulario.mostrarMensaje("ERROR intente mas tarde", true);
			break;
		case CREAR_CUENTA_BANCARIA_OK:
			formulario.mostrarMensaje("Cuenta creada con IBAN:", false);
			break;
		
		}
	}
}