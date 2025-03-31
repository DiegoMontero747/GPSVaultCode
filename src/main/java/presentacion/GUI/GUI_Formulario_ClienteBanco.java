package presentacion.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import negocio.ManejoSesiones.Roles;
import presentacion.Controller.Context;
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
	
	/*
    // Componentes de la interfaz
    private JTextField txtDni, txtNombre, txtApellidos, txtDireccion, txtTelefono;
    private JButton btnAgregar, btnCerrar;
    private JTable table;
    private DefaultTableModel tableModel;
*/
	
    public GUI_Formulario_ClienteBanco() {
    	
        initialize();
    	

    	
    	/*
        // Configuración de la ventana
        setSize(400, 350);
        setLayout(new BorderLayout());
        
        // Panel para el formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(6, 2, 10, 10)); // Fila por columna

        // Etiquetas y campos de texto
        panelFormulario.add(new JLabel("DNI:"));
        txtDni = new JTextField();
        panelFormulario.add(txtDni);
        
        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);
        
        panelFormulario.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panelFormulario.add(txtApellidos);
        
        panelFormulario.add(new JLabel("Dirección Postal:"));
        txtDireccion = new JTextField();
        panelFormulario.add(txtDireccion);
        
        panelFormulario.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelFormulario.add(txtTelefono);
        
        // Botones
        btnAgregar = new JButton("Agregar");
        btnCerrar = new JButton("Cerrar");
        
        panelFormulario.add(btnAgregar);
        panelFormulario.add(btnCerrar);

        // Tabla para mostrar los datos
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"DNI", "Nombre", "Apellidos", "Dirección", "Teléfono"});
        table = new JTable(tableModel);
        
        // Agregar los componentes a la ventana
        add(panelFormulario, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        // Acciones de los botones
        btnAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Validar que los campos no estén vacíos
                if(txtDni.getText().isEmpty() || txtNombre.getText().isEmpty() || txtApellidos.getText().isEmpty() || 
                   txtDireccion.getText().isEmpty() || txtTelefono.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                } else {
                    // Agregar datos a la tabla
                    Object[] row = {
                        txtDni.getText(),
                        txtNombre.getText(),
                        txtApellidos.getText(),
                        txtDireccion.getText(),
                        txtTelefono.getText()
                    };
                    tableModel.addRow(row);
                    
                    // Limpiar los campos de texto
                    txtDni.setText("");
                    txtNombre.setText("");
                    txtApellidos.setText("");
                    txtDireccion.setText("");
                    txtTelefono.setText("");
                }
            }
        });

        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });*/
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
		formulario = new GeneralForm(contentPanel, 5, titleLabel, actionButton, errorLabel);
		
		// Se ajusta el formulario
		formulario.setInfoText(0, "Nombre");				// el campo de texto del nombre
		formulario.setInfoText(1, "Apellidos");				// el campo de texto de los apellidos
		formulario.setInfoText(2, "DNI");					// el campo de texto del DNI
		formulario.setInfoText(3, "Direccion");				// el campo de texto del usuario
		formulario.setInfoText(4, "Telefono");				// el campo de texto de la contrasenya
		
		this.setVisible(true);
    	
    }

    public static void main(String[] args) {
        // Crear y mostrar la interfaz
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI_Formulario_ClienteBanco().setVisible(true);
            }
        });
    }

	public void actualizar(Context c) {
		// TODO Auto-generated method stub
		
	}
}