package presentacion.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;

public class GUI_Principal implements ObservadorGUI {

	private JFrame _frame;
	private JButton _menuButton;
	private SlidingWindow slidingWindow;

	public GUI_Principal() {
		init();
	}

	private void init() {
		_frame = new JFrame("Ventana principal");

		// Obtener el tamaño completo de la pantalla
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle screenBounds = ge.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
		_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		_frame.setSize(screenBounds.width, screenBounds.height);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setLayout(null);

		try {
			Image backgroundImage = ImageIO.read(new File("media/Fondo GUI Principal.jpg"));
			_frame.setContentPane(new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            });
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		_menuButton = new JButton("Abrir Ventana");
		_menuButton.setBounds(0, 10, 150, 30);
		_menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (slidingWindow == null || !slidingWindow.isVisible()) {
					slidingWindow = new SlidingWindow(_frame, _menuButton.getWidth());
					slidingWindow.startSliding();
				}
				else {
					slidingWindow.slideBackwards();
				}
			}
		});

		_frame.add(_menuButton);
		_frame.setVisible(true);
	}
	
	private class SlidingWindow extends JWindow {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int targetX;
		private int startX;
		private int yPos;

		public SlidingWindow(JFrame mainFrame, int buttonWidth) {
			setSize(buttonWidth, mainFrame.getHeight());
			setLayout(new BorderLayout());
			
			Color customColor = new Color(255, 140, 0);
			getContentPane().setBackground(customColor); // Color de fondo naranja

			JLabel label = new JLabel("Nueva Ventana", SwingConstants.CENTER);
			add(label, BorderLayout.CENTER);

			this.yPos = mainFrame.getY(); // Alinear con la altura de la ventana principal
			this.startX = mainFrame.getX() - getWidth(); // Iniciar dentro del borde izquierdo de la ventana principal
			this.targetX = mainFrame.getX(); // Alinear con el borde izquierdo de la ventana principal

			// mainFrame.add(this);
		}


		
		public void slideBackwards() {
	        new Timer(5, new ActionListener() {
	            int x = targetX;
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (x > startX) {
	                    x -= 10;
	                    setLocation(x, yPos);
	                } else {
	                    setVisible(false);
	                    ((Timer) e.getSource()).stop();
	                }
	            }
	        }).start();
	    }
		
		public void startSliding() {
			setLocation(startX, yPos);
			setVisible(true);

			new Timer(5, new ActionListener() {
				int x = startX;

				@Override
				public void actionPerformed(ActionEvent e) {
					if (x < targetX) {
						x += 10;
						setLocation(x, yPos);
					} else {
						((Timer) e.getSource()).stop();
					}
				}
			}).start();
		}
	}
	
	@Override
	public void actualizar(Context c) {
		// TODO Auto-generated method stub

	}

	public static void main(String args[]) {
		Controller.getInstance().handleRequest(new Context(Evento.VISTA_PRINCIPAL, null));
	}
}
