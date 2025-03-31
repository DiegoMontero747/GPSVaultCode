package presentacion.GUI_Components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import presentacion.GUI_Components.RoundedComponents.RoundedButton;
import presentacion.GUI_Components.RoundedComponents.RoundedComboBox;
import presentacion.GUI_Components.RoundedComponents.RoundedFields;
import presentacion.GUI_Components.RoundedComponents.RoundedPasswordField;
import presentacion.GUI_Components.RoundedComponents.RoundedTextField;

public class GeneralForm {

	// constantes
	private final int TEXT_FIELD_WIDTH = 32;
	
	// atributos
	private JPanel contentPanel;													// panel sobre el que se pinta el formulario
	private int rows;																// numero de filas de cuadros de texto
	private JLabel titleLabel;														// etiqueta con el titulo del formulario
	private List<RoundedFields> textFields = new ArrayList<RoundedFields>();		// array con todos los textfields
	private RoundedButton actionButton;												// boton de accion
	private JLabel errorLabel;														// etiqueta de error
	private GridBagConstraints gbc = new GridBagConstraints();						// layout constraints
	
	public GeneralForm(JPanel contentPanel, int rows, JLabel titleLabel, RoundedButton actionButton, JLabel errorLabel) {
		this.contentPanel = contentPanel;
		this.rows = rows;
		this.titleLabel = titleLabel;
		this.actionButton = actionButton;
		this.errorLabel = errorLabel;
		
		// se pinta el formulario
		this.draw();
	}
	
	// printa el formulario
	private void draw() {
		// Restricciones del panel con contenido
		gbc.insets = new Insets(10, 20, 10, 20);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		
		// Se anyade la etiqueta de titulo al principio del todo
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		contentPanel.add(titleLabel, gbc);
		
		int i;
		// Se anyaden los campos de texto necesarios, inicialmente son textFields
		for(i = 1; i < rows + 1; i++) {
			// se crea el campo de texto y se guarda en el array interno
			RoundedTextField rtf = new RoundedTextField(TEXT_FIELD_WIDTH);
			textFields.add(rtf);
			
			// se muestra en el panel
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.gridwidth = 2;
			contentPanel.add(rtf, gbc);
		}
		
		// Se anyade el boton de accion
		gbc.gridx = 0;
		gbc.gridy = i;
		i++;
		gbc.gridwidth = 2;
		contentPanel.add(actionButton, gbc);
		
		// Por ultimo, se anyade la etiqueta de error
		gbc.gridx = 0;
		gbc.gridy = i;
		gbc.gridwidth = 2;
		contentPanel.add(errorLabel, gbc);
	}
	
	// permite mostrar el mensaje de error del formulario
	public void mostrarMensajeError(String mensaje) {
		errorLabel.setText(mensaje);
		errorLabel.setForeground(new Color(255, 94, 0));

		errorLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Ajusta el tamaño y tipo de fuente
		errorLabel.setOpaque(false); // Asegura que el fondo sea transparente

		errorLabel.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
			@Override
			public void paint(Graphics g, JComponent c) {
				Graphics2D g2 = (Graphics2D) g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				String text = ((JLabel) c).getText();
				FontMetrics fm = g2.getFontMetrics(errorLabel.getFont());
				int x = (errorLabel.getWidth() - fm.stringWidth(text)) / 2;
				int y = (errorLabel.getHeight() + fm.getAscent()) / 2 - fm.getDescent();

				// Dibujar el borde blanco (desplazando el texto en todas direcciones)
				g2.setColor(Color.BLACK);
				for (int i = -1; i <= 1; i++) {
					for (int j = -1; j <= 1; j++) {
						if (i != 0 || j != 0) {
							g2.drawString(text, x + i, y + j);
						}
					}
				}

				// Dibujar el texto rojo encima
				g2.setColor(new Color(255, 94, 0));
				g2.drawString(text, x, y);
			}
		});

		contentPanel.revalidate();
		contentPanel.repaint();
	}
	
	// metodos de adaptacion
	// cambia el texto de informacion del campo
	public void setInfoText(int row, String infoText) {
		textFields.get(row).setInfoText(infoText);
	}
	
	// transforma el textfield normal a uno de tipo contrasenya
	public void setPassword(int row) {
		// se actualiza el componente
		contentPanel.remove((JComponent) textFields.get(row));
		RoundedPasswordField rpf = new RoundedPasswordField(TEXT_FIELD_WIDTH);
		textFields.set(row, rpf);
		
		// se muestra
		gbc.gridx = 0;
		gbc.gridy = row + 1;
		gbc.gridwidth = 2;
		contentPanel.add(rpf, gbc);
	}
	
	// transforma el textfield normal a uno de tipo combobox
	public void setComboBox(int row) {
		// se actualiza el componente
		contentPanel.remove((JComponent) textFields.get(row));
		RoundedComboBox<String> rcb = new RoundedComboBox<String>();
		textFields.set(row, rcb);
		
		// se muestra
		gbc.gridx = 0;
		gbc.gridy = row + 1;
		gbc.gridwidth = 2;
		contentPanel.add(rcb, gbc);
	}
	
	// anyade la informacion de tipo cadena al combobox especificado
	@SuppressWarnings("unchecked")
	public void setComboBoxInfo(int row, List<String> list) {
		if (textFields.get(row) instanceof JComboBox) {
			for(String item : list) {
				((JComboBox<String>)textFields.get(row)).addItem(item);
			}
		}
	}
	
	// metodos getters
	@SuppressWarnings("unchecked")
	public String getText(int row) {
		if(textFields.get(row) instanceof JTextField) return ((JTextField) textFields.get(row)).getText();
		else if (textFields.get(row) instanceof JComboBox) return ((JComboBox<String>) textFields.get(row)).getSelectedItem().toString();
		return null;
	}
}
