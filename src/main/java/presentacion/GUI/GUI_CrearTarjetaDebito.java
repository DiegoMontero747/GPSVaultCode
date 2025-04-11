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
    private String numeroTarjeta;

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
        this.add(backgroundPanel, BorderLayout.CENTER);

        // Panel para los componentes (con fondo transparente)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setOpaque(false);
        backgroundPanel.add(contentPanel, BorderLayout.CENTER);

        // Titulo del formulario
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(new Color(255, 94, 0));

        // Etiqueta de error del formulario
        errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        errorLabel.setPreferredSize(new Dimension(300, 40));

        // Crear formulario
        formulario = new GeneralForm(contentPanel, 7, titleLabel, actionButton, errorLabel); // Cambié el número de campos a 7 (sin tarjeta de débito)

        formulario.setInfoText(0, "Nombre");
        formulario.setInfoText(1, "Apellidos");
        formulario.setInfoText(2, "DNI");
        formulario.setInfoText(3, "IBAN");
        formulario.setInfoText(4, "Fecha de nacimiento");
        formulario.setInfoText(5, "Dirección");
        formulario.setInfoText(6, "Teléfono");

        // Botón del formulario
        actionButton.addActionListener(e -> {
            // Obtener los datos del formulario
            String nombre = formulario.getText(0);
            String apellidos = formulario.getText(1);
            String DNI = formulario.getText(2);
            String numeroCuenta = formulario.getText(3);
            String fecha = formulario.getText(4);
            String dir = formulario.getText(5);
            String tlfn = formulario.getText(6);

            // Verificar que todos los campos obligatorios estén completos
            if (nombre.isEmpty() || apellidos.isEmpty() || DNI.isEmpty() || numeroCuenta.isEmpty() || fecha.isEmpty() || dir.isEmpty() || tlfn.isEmpty()) {
                formulario.mostrarMensaje("Rellene todos los campos.", true);
                return;
            }

            // Generar el número de tarjeta único
            numeroTarjeta = generarNumeroTarjetaUnico(); // Asignamos el número de tarjeta a la variable de instancia

            // Crear la tarjeta con el número generado
            TTarjeta tarjeta = new TTarjeta(nombre, apellidos, DNI, numeroTarjeta, dir, tlfn, fecha); // Ahora pasamos el número de tarjeta

            tarjeta.setNumeroCuenta(numeroCuenta); // Asignamos el número de cuenta

            // Enviar el evento al controlador
            Controller.getInstance().handleRequest(new Context(Evento.CREAR_TARJETA_DEBITO, tarjeta));
        });

        this.setVisible(true);
    }

    // Método para generar un número de tarjeta único
    private String generarNumeroTarjetaUnico() {
        long numero = (long) (Math.random() * 10000000000000000L);
        return String.format("%016d", numero);
    }

    @Override
    public void actualizar(Context c) {
        switch (c.getEvento()) {
        case CREAR_TARJETA_OK:
            // Ahora podemos usar numeroTarjeta porque es una variable de instancia
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
        }
    }
}