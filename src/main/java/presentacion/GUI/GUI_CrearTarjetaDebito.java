package presentacion.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Tarjetas.SATarjetasImp;
import Tarjetas.TTarjeta;

import negocio.Factory.ResultContext;
import presentacion.Controller.Context;
import presentacion.Controller.Evento;

import java.awt.*;

public class GUI_CrearTarjetaDebito implements ObservadorGUI {
    private JFrame frame;
    private JTextField nombreTitularField, documentoField, entidadField, oficinaField, digitoControlField, cuentaField;
    private JComboBox<String> tipoDocumentoCombo;
    private JButton crearTarjetaButton, cancelarButton;

    public GUI_CrearTarjetaDebito() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JFrame("Crear Tarjeta de Débito - VAULTCODE");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(110, 110, 110));

        JLabel titleLabel = new JLabel("CREAR TARJETA DE DÉBITO", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        titleLabel.setForeground(Color.BLACK);
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(10, 1, 10, 10));
        contentPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        JLabel nombreTitularLabel = new JLabel("Nombre completo:");
        setLabelStyle(nombreTitularLabel);
        contentPanel.add(nombreTitularLabel);
        nombreTitularField = new JTextField();
        contentPanel.add(nombreTitularField);

        JLabel tipoDocumentoLabel = new JLabel("Tipo de documento:");
        setLabelStyle(tipoDocumentoLabel);
        contentPanel.add(tipoDocumentoLabel);
        tipoDocumentoCombo = new JComboBox<>(new String[]{"DNI", "NIE"});
        contentPanel.add(tipoDocumentoCombo);

        JLabel documentoLabel = new JLabel("Número de documento:");
        setLabelStyle(documentoLabel);
        contentPanel.add(documentoLabel);
        documentoField = new JTextField();
        contentPanel.add(documentoField);

        JLabel numeroCuentaLabel = new JLabel("Número de cuenta (IBAN):");
        setLabelStyle(numeroCuentaLabel);
        contentPanel.add(numeroCuentaLabel);

        JPanel ibanPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        entidadField = new JTextField(4);
        oficinaField = new JTextField(4);
        digitoControlField = new JTextField(2);
        cuentaField = new JTextField(10);
        ibanPanel.add(entidadField);
        ibanPanel.add(oficinaField);
        ibanPanel.add(digitoControlField);
        ibanPanel.add(cuentaField);
        contentPanel.add(ibanPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        crearTarjetaButton = createStyledButton("CREAR TARJETA");
        cancelarButton = createStyledButton("CANCELAR");
        buttonPanel.add(crearTarjetaButton);
        buttonPanel.add(cancelarButton);

        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        cancelarButton.addActionListener(e -> frame.dispose());

        crearTarjetaButton.addActionListener(e -> {
            String nombreTitular = nombreTitularField.getText().trim();
            String tipoDocumento = (String) tipoDocumentoCombo.getSelectedItem();
            String numeroDocumento = documentoField.getText().trim();
            String entidad = entidadField.getText().trim();
            String oficina = oficinaField.getText().trim();
            String digitoControl = digitoControlField.getText().trim();
            String cuenta = cuentaField.getText().trim();
            
            if (entidad.isBlank() || oficina.isBlank() || digitoControl.isBlank() || cuenta.isBlank()) {
                mensaje("Debe completar todos los campos del IBAN.", "Error", JOptionPane.ERROR_MESSAGE, false);
                return;
            }
            
            // Construimos el objeto TTarjeta
            TTarjeta tarjeta = new TTarjeta(nombreTitular, tipoDocumento, numeroDocumento, entidad+oficina+digitoControl+cuenta);

            // Llamamos al SA para crear la tarjeta
            SATarjetasImp sat = new SATarjetasImp();
            ResultContext result = sat.crearTarjetaDebito(tarjeta);

            // Evaluamos la respuesta del SA
            switch (result.getEvento()) {
            case CREAR_TARJETA_OK:
                mensaje("Tarjeta de débito creada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE, true);
                break;
            case CREAR_TARJETA_ERROR_TIPO_DOCUMENTO_INVALIDO:
                mensaje("Número de documento inválido.", "Error", JOptionPane.ERROR_MESSAGE, true);
                break;
            case CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE:
                mensaje("La cuenta IBAN ingresada no existe.", "Error", JOptionPane.ERROR_MESSAGE, false);
                break;
            case CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS:
                mensaje("Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE, false);
                break;
            case CREAR_TARJETA_ERROR_DB:
                mensaje("Error en la base de datos al registrar la tarjeta.", "Error", JOptionPane.ERROR_MESSAGE, false);
                break;
        }

        });
    }
    
    private void mensaje(String msg, String title, int msgType, boolean limpiar) {
        JOptionPane.showMessageDialog(frame, msg, title, msgType);
        if (limpiar) {
            limpiarCampos();
        }
    }


    private void limpiarCampos() {
        nombreTitularField.setText("");
        documentoField.setText("");
        entidadField.setText("");
        oficinaField.setText("");
        digitoControlField.setText("");
        cuentaField.setText("");
        tipoDocumentoCombo.setSelectedIndex(0);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(88, 88, 88));
        button.setForeground(new Color(255, 255, 255));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(30, 30, 30), 1));
        button.setPreferredSize(new Dimension(160, 40));
        button.setOpaque(true);
        return button;
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
