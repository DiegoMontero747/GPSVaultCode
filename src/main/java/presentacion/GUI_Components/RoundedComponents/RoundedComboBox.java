package presentacion.GUI_Components.RoundedComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class RoundedComboBox<E> extends JComboBox<E> implements RoundedFields {
    private static final long serialVersionUID = 1L;
    private int arcSize = 15; // Radio de las esquinas 

    public RoundedComboBox() {
        super();
        configureAppearance();
    }

    public RoundedComboBox(E[] items) {
        super(items);
        configureAppearance();
    }

    private void configureAppearance() {
        // Configuración básica
        setOpaque(false);
        setBorder(new EmptyBorder(5, 5, 5, 10)); // Cambiado de (5, 10, 5, 10) a (5, 5, 5, 10)
        setBackground(new Color(50, 50, 50));
        setForeground(Color.WHITE);
        setPreferredSize(new Dimension(300, 30));
        setMinimumSize(new Dimension(100, 30));

        // Configuración crítica para el fondo
        UIManager.put("ComboBox.background", new Color(50, 50, 50));
        UIManager.put("ComboBox.buttonBackground", new Color(50, 50, 50));
        UIManager.put("ComboBox.selectionBackground", new Color(255, 94, 0));
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);

        // Configurar el editor
        if (isEditable()) {
            JTextField editor = (JTextField) getEditor().getEditorComponent();
            editor.setOpaque(false);
            editor.setBackground(new Color(50, 50, 50));
            editor.setForeground(Color.WHITE);
            editor.setBorder(new EmptyBorder(0, 0, 0, 0));
        }

        // Personalizar el renderer
        setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                setBackground(isSelected ? new Color(255, 94, 0) : new Color(50, 50, 50));
                setForeground(Color.WHITE);
                setBorder(new EmptyBorder(5, 5, 5, 10)); // Cambiado de (5, 10, 5, 10) a (5, 5, 5, 10)
                
                return this;
            }
        });
        
        // Configurar el editor
        if (isEditable()) {
            JTextField editor = (JTextField) getEditor().getEditorComponent();
            editor.setOpaque(false);
            editor.setBackground(new Color(50, 50, 50));
            editor.setForeground(Color.WHITE);
            editor.setBorder(new EmptyBorder(0, 5, 0, 0)); // Cambiado de (0, 0, 0, 0) a (0, 5, 0, 0)
        }

        // Configurar el botón desplegable con UI personalizada
        setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        
                        // Fondo del botón
                        g2.setColor(new Color(50, 50, 50));
                        g2.fillRect(0, 0, getWidth(), getHeight());
                        
                        // Dibujar flecha manualmente
                        g2.setColor(Color.WHITE);
                        int x = getWidth() / 2 - 4;
                        int y = getHeight() / 2 - 2;
                        int[] xPoints = {x, x + 8, x + 4};
                        int[] yPoints = {y, y, y + 6};
                        g2.fillPolygon(xPoints, yPoints, 3);
                        
                        g2.dispose();
                    }
                };
                
                button.setContentAreaFilled(false);
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setPreferredSize(new Dimension(20, 20));
                
                return button;
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(50, 50, 50));
                g2.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, arcSize, arcSize);
                g2.dispose();
            }
        });
    }

    @Override
    public void updateUI() {
        super.updateUI();
        configureAppearance(); // Reaplica los estilos cuando el UI cambie
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Fondo principal
        g2.setColor(new Color(50, 50, 50));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcSize, arcSize);
        
        // Pintar el borde
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcSize, arcSize);
        
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Borde redondeado
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcSize, arcSize);

        g2.dispose();
    }

	@Override
	public void setInfoText(String infoText) {
		// TODO Auto-generated method stub
		
	}
}