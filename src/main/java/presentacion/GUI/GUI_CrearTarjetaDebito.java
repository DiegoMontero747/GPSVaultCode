package presentacion.GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import presentacion.Controller.Context;
import java.awt.*;

public class GUI_CrearTarjetaDebito implements ObservadorGUI {
    private JFrame frame;
    private JTextField nombreTitularField, documentoField, entidadField, oficinaField, digitoControlField, cuentaField;
    private JComboBox<String> tipoDocumentoCombo, tipoCuentaCombo;
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

        JLabel tipoCuentaLabel = new JLabel("Tipo de cuenta:");
        setLabelStyle(tipoCuentaLabel);
        contentPanel.add(tipoCuentaLabel);
        tipoCuentaCombo = new JComboBox<>(new String[]{"Nómina", "Corriente", "Ahorro"});
        contentPanel.add(tipoCuentaCombo);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        crearTarjetaButton = createStyledButton("CREAR TARJETA");
        cancelarButton = createStyledButton("CANCELAR");
        buttonPanel.add(crearTarjetaButton);
        buttonPanel.add(cancelarButton);

        frame.add(contentPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
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


