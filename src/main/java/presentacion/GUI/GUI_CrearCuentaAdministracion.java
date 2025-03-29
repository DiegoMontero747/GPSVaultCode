package presentacion.GUI;

import java.awt.*;

import javax.swing.*;

import negocio.ManejoSesiones.TSesion;
import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;
import presentacion.GUI_Components.GeneralForm;
import presentacion.GUI_Components.RoundedComponents.RoundedButton;
import presentacion.GUI_Components.RoundedComponents.RoundedComboBox;

public class GUI_CrearCuentaAdministracion extends JFrame implements ObservadorGUI {
    /** TODO CAMBIAR A JPANEL Y ENLAZARLO AL BOTON CORRESPONDIENTE CUANDO ESTÉ
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
			//TODO
		});
		
		// Etiqueta de error del formulario
		errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
		errorLabel.setPreferredSize(new Dimension(300, 40));
		
		// Se crea el formulario
		formulario = new GeneralForm(contentPanel, 7, titleLabel, actionButton, errorLabel);
		
		// Se ajusta el formulario
		formulario.setPassword(1);							// el segundo campo de texto es de tipo contrasenya
		formulario.setComboBox(6);							// el ultimo campo es un combobox
		formulario.setInfoText(0, "Nombre de usuario");		// el campo de texto del usuario
		formulario.setInfoText(1, "Contraseña");			// el campo de texto de la contrasenya
		formulario.setInfoText(2, "Nombre");				// el campo de texto del nombre
		formulario.setInfoText(3, "Apellidos");				// el campo de texto de los apellidos
		formulario.setInfoText(4, "DNI");					// el campo de texto del DNI
		formulario.setInfoText(5, "Telefono");				// el campo de texto de la contrasenya
		
		this.setVisible(true);
    }
    
	@Override
	public void actualizar(Context c) {
		// TODO Auto-generated method stub
		
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
