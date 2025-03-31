package presentacion.GUI_Components.RoundedComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

public class RoundedButton extends JButton {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int arcSize = 15; // Radio de las esquinas
    private Color rolloverBackground = new Color(255, 120, 40); // Color cuando el mouse pasa por encima
    private Color pressedBackground = new Color(220, 80, 0); // Color cuando se presiona
    
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); // Importante para que no se pinte el área por defecto
        setFocusPainted(false); // Elimina el borde de enfoque
        
        // Configuración de colores por defecto
        setBackground(new Color(255, 94, 0));
        setForeground(Color.BLACK);
        setFont(new Font("Arial", Font.BOLD, 14));
        setPreferredSize(new Dimension(100, 30));
        
        // Efectos de hover y press
        addChangeListener(e -> {
            if (getModel().isPressed()) {
                setBackground(pressedBackground);
            } else if (getModel().isRollover()) {
                setBackground(rolloverBackground);
            } else {
                setBackground(new Color(255, 94, 0));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Pintar fondo redondeado
        if (getModel().isArmed()) {
            g2.setColor(pressedBackground);
        } else if (getModel().isRollover()) {
            g2.setColor(rolloverBackground);
        } else {
            g2.setColor(getBackground());
        }
        
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcSize, arcSize));
        
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
    	// necesario, no se quiere borde
    }

    // Métodos para personalizar
    public void setArcSize(int arcSize) {
        this.arcSize = arcSize;
        repaint();
    }
    
    public void setRolloverBackground(Color rolloverBackground) {
        this.rolloverBackground = rolloverBackground;
    }
    
    public void setPressedBackground(Color pressedBackground) {
        this.pressedBackground = pressedBackground;
    }
}
