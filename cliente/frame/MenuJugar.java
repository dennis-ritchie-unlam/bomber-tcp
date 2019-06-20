package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;
import cliente.comando.Comando;

/**
 * Menu Jugar
 */
public class MenuJugar extends JFrame {

	private static final int ALTO_MENU = 300;
	private static final int ANCHO_MENU = 450;
	private static final int Y_MENU = 100;
	private static final int X_MENU = 100;
	private static final int ALTO_PANE = 271;
	private static final int ANCHO_PANE = 444;
	private static final int ALTO_LB_REG = 23;
	private static final int ANCHO_LB_REG = 82;
	private static final int Y_LB_REG = 162;
	private static final int X_LB_REG = 181;
	private static final int ALTO_LB_INICIO = 23;
	private static final int ANCHO_LB_INICIO = 91;
	private static final int Y_LB_INICIO = 91;
	private static final int X_LB_INICIO = 175;
	private static final int BORDER = 5;
	private static final int TAM_TXT = 15;
	private static final int ALTO_BNT_REG = 23;
	private static final int ANCHO_BTN_REG = 191;
	private static final int Y_BTN_REG = 162;
	private static final int X_BTN_REG = 121;
	private static final int ALTO_BTN_INICIAR = 23;
	private static final int ANCHO_BTN_INICIAR = 191;
	private static final int Y_BTN_INICIAR = 92;
	private static final int X_BTN_INICIAR = 121;
	private static final int ALTO_BACK = 271;
	private static final int ANCHO_BACK = 444;
	private JPanel contentPane;

	/**
	 * Iniciador del menu
	 * 
	 * @param cliente cliente que ingresa al menu jugar
	 */
	public MenuJugar(final Cliente cliente) {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					MenuInicioSesion menuInicioSesion = new MenuInicioSesion(cliente);
					menuInicioSesion.setVisible(true);
					dispose();
				}
			}
		});
//		setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/java/frames/IconoWome.png"));
//		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
//				new ImageIcon(MenuJugar.class.getResource("/cursor.png")).getImage(), new Point(0, 0),
//				"custom cursor"));

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// En caso de cerrar la ventana
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				synchronized (cliente) {
					cliente.setAccion(Comando.SALIR);
					cliente.notify();
				}
				dispose();
			}
		});

		// Propiedades de la ventana
		setTitle("WOME - World Of the Middle Earth");
		setBounds(X_MENU, Y_MENU, ANCHO_MENU, ALTO_MENU);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, ANCHO_PANE, ALTO_PANE);
		contentPane.add(layeredPane);

		// Boton Registrarse
		JLabel lblRegistrarse = new JLabel("Registrarse");
		lblRegistrarse.setBounds(X_LB_REG, Y_LB_REG, ANCHO_LB_REG, ALTO_LB_REG);
		layeredPane.add(lblRegistrarse, new Integer(2));
		lblRegistrarse.setForeground(Color.WHITE);
		lblRegistrarse.setEnabled(true);
		lblRegistrarse.setFont(new Font("Tahoma", Font.PLAIN, TAM_TXT));
		lblRegistrarse.setBackground(Color.WHITE);

		// Boton Iniciar sesion
		JLabel lblIniciarSesion = new JLabel("Iniciar Sesion");
		lblIniciarSesion.setBounds(X_LB_INICIO, Y_LB_INICIO, ANCHO_LB_INICIO, ALTO_LB_INICIO);
		layeredPane.add(lblIniciarSesion, new Integer(2));
		lblIniciarSesion.setForeground(Color.WHITE);
		lblIniciarSesion.setFont(new Font("Tahoma", Font.PLAIN, TAM_TXT));

//		JButton btnRegistrar = new JButton("Registrarse");
//		btnRegistrar.setBounds(X_BTN_REG, Y_BTN_REG, ANCHO_BTN_REG, ALTO_BNT_REG);
//		layeredPane.add(btnRegistrar, new Integer(1));
//		btnRegistrar.setFocusable(false);
//		btnRegistrar.setIcon(new ImageIcon(MenuJugar.class.getResource("/frames/BotonMenu.png")));
//		btnRegistrar.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(final ActionEvent e) {
//				MenuRegistro menuRegistro = new MenuRegistro(cliente);
//				menuRegistro.setVisible(true);
//				dispose();
//			}
//		});

		JButton btnIniciarSesion = new JButton("Iniciar Sesion");
		btnIniciarSesion.setBounds(X_BTN_INICIAR, Y_BTN_INICIAR, ANCHO_BTN_INICIAR, ALTO_BTN_INICIAR);
		layeredPane.add(btnIniciarSesion, new Integer(1));
		btnIniciarSesion.setFocusable(false);
//		btnIniciarSesion.setIcon(new ImageIcon(MenuJugar.class.getResource("/frames/BotonMenu.png")));
		btnIniciarSesion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				MenuInicioSesion menuInicioSesion = new MenuInicioSesion(cliente);
				menuInicioSesion.setVisible(true);
				dispose();
			}
		});

		JLabel lblBackground = new JLabel("");
		lblBackground.setBounds(0, 0, ANCHO_BACK, ALTO_BACK);
//		lblBackground.setIcon(new ImageIcon(MenuJugar.class.getResource("/frames/menuBackground.jpg")));
		layeredPane.add(lblBackground, new Integer(0));
	}
}
