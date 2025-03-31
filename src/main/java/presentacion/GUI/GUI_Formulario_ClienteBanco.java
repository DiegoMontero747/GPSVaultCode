package presentacion.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

import presentacion.Controller.Context;

public class GUI_Formulario_ClienteBanco extends JPanel implements ObservadorGUI {
    
    // Componentes de la interfaz
    private JTextField txtDni, txtNombre, txtApellidos, txtDireccion, txtTelefono;
    private JButton btnAgregar, btnCerrar;
    private JTable table;
    private DefaultTableModel tableModel;

    public GUI_Formulario_ClienteBanco() {
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
        });
    }

    public static void main(String[] args) {
        // Crear y mostrar la interfaz
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GUI_Formulario_ClienteBanco().setVisible(true);
            }
        });
    }

	@Override
	public void actualizar(Context c) {
		// TODO Auto-generated method stub
		
	}
}