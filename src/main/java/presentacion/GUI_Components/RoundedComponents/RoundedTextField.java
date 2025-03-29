package presentacion.GUI_Components.RoundedComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class RoundedTextField extends JTextField implements RoundedFields{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// atributos
	private String infoText;						// texto autoexplicatorio del campo de texto
    private boolean showingHint = true;				// si el campo de texto es actualmente el de informacion	
	
	// atributos de estetica
	private int arcSize = 15; 						// Radio de las esquinas

    public RoundedTextField() {};
	
    public RoundedTextField(int columns) {
        super(columns);
        
        // se ajusta el campo de texto
        setOpaque(false); 							// Hace que el fondo sea transparente 
        setBorder(new EmptyBorder(5, 10, 5, 10)); 	// Margen interno para evitar que el texto se corte
		setBackground(new Color(50, 50, 50));
		setForeground(new Color(145, 145, 145));
		setCaretColor(new Color(255, 94, 0));
        setPreferredSize(new Dimension(300, 30)); 	// Tamaño preferido más grande
        setMinimumSize(new Dimension(100, 30)); 	// Evita que se expanda demasiado
        
        // se establece el listener por defecto
        // Comportamiento por defecto del foco
        addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if(showingHint) {
					setText("");
					setForeground(Color.WHITE);
					showingHint = false;
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
                if (getText().trim().isEmpty()) {
                    setText(infoText);
                    setForeground(new Color(145, 145, 145));
                    showingHint = true;
                } else {
                    showingHint = false;
                }
			}
        });
    }
    
    @Override
    public String getText() {
        return showingHint ? "" : super.getText();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo del campo de texto
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcSize, arcSize);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Borde del campo de texto
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcSize, arcSize);

        g2.dispose();
    }
    
    // metodos setters
    public void setInfoText(String infoText) {
    	this.infoText = infoText;
    	setText(infoText);
    }
}
