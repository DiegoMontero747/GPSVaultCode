package presentacion.GUI;

import javax.swing.*;
import java.awt.*;
import presentacion.Controller.Context;
import presentacion.Controller.Evento;

public class GUI_MenuAdmin implements ObservadorGUI {
    private JFrame frame;

    public GUI_MenuAdmin() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Admin Menu");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.add(new JLabel("Bienvenido, Admin!"));
        
        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actualizar(Context context) {
        if (context.getEvento() == Evento.GUI_MENU_ADMIN) {
            frame.setVisible(true);
        }
    }
}