package frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;
import cliente.comando.Comando;

/**
 * Menu de inicio de Sesion del cliente
 */
public class MenuInicioSesion extends JFrame {

	private static final int ALTO_MENU = 300;
	private static final int ANCHO_MENU = 450;
	private static final int Y_MENU = 100;
	private static final int X_MENU = 100;
	private static final int BORDER = 5;
	private static final int ALTO_PANE = 271;
	private static final int ANCHO_PANE = 444;
	private static final int ALTO_LB_PASS = 21;
	private static final int ANCHO_LB_PASS = 68;
	private static final int Y_LB_PASS = 118;
	private static final int X_LB_PASS = 111;
	private static final int ALTO_USUARIO = 23;
	private static final int ANCHO_USUARIO = 55;
	private static final int Y_USUARIO = 66;
	private static final int X_USUARIO = 111;
	private static final int ALTO_LB_INGRESAR = 23;
	private static final int ANCHO_LB_INGRESAR = 68;
	private static final int Y_LB_INGRESAR = 183;
	private static final int X_LB_INGRESAR = 193;
	private static final int TAM_TXT = 15;
	private static final int TAM_TXT_PASSWORD = 11;
	private static final int ALTO_TF = 20;
	private static final int ANCHO_TF = 118;
	private static final int Y_TF = 69;
	private static final int X_TF = 198;
	private static final int COLUMNAS = 10;
	private static final int ALTO_PASS = 20;
	private static final int ANCHO_PASS = 118;
	private static final int Y_PASS = 119;
	private static final int X_PASS = 198;
	private static final int ALTO_BTN_CONECTAR = 23;
	private static final int ANCHO_BTN_CONECTAR = 153;
	private static final int Y_BTN_CONECTAR = 182;
	private static final int X_BTN_CONECTAR = 141;
	private static final int ALTO_BACK = 271;
	private static final int ANCHO_BACK = 444;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * @param cliente cliente que inicia sesion
	 */
	public MenuInicioSesion(final Cliente cliente) {
//	setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/java/frames/IconoWome.png"));
//	setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
//		new ImageIcon(MenuJugar.class.getResource("/cursor.png")).getImage(), new Point(0, 0),
//		"custom cursor"));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				synchronized (cliente) {
					cliente.setAccion(Comando.SALIR);
					cliente.notify();
				}
				setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

			}
		});

		setTitle("BMB - Iniciar Sesion");
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

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(X_LB_PASS, Y_LB_PASS, ANCHO_LB_PASS, ALTO_LB_PASS);
		layeredPane.add(lblPassword);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, TAM_TXT));
		lblPassword.setForeground(Color.WHITE);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(X_USUARIO, Y_USUARIO, ANCHO_USUARIO, ALTO_USUARIO);
		layeredPane.add(lblUsuario, new Integer(2));
		lblUsuario.setForeground(Color.WHITE);
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, TAM_TXT));

		JLabel lblIngresar = new JLabel("Ingresar");
		lblIngresar.setBounds(X_LB_INGRESAR, Y_LB_INGRESAR, ANCHO_LB_INGRESAR, ALTO_LB_INGRESAR);
		layeredPane.add(lblIngresar, new Integer(2));
		lblIngresar.setForeground(Color.WHITE);
		lblIngresar.setFont(new Font("Tahoma", Font.PLAIN, TAM_TXT));

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent arg0) {
				logIn(cliente);
			}
		});
		textField.setBounds(X_TF, Y_TF, ANCHO_TF, ALTO_TF);
		layeredPane.add(textField, new Integer(1));
		textField.setColumns(COLUMNAS);

		passwordField = new JPasswordField();
		passwordField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				logIn(cliente);
			}
		});
		passwordField.setBounds(X_PASS, Y_PASS, ANCHO_PASS, ALTO_PASS);
		layeredPane.add(passwordField, new Integer(1));
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, TAM_TXT_PASSWORD));

		JButton btnConectar = new JButton("");
		btnConectar.setBounds(X_BTN_CONECTAR, Y_BTN_CONECTAR, ANCHO_BTN_CONECTAR, ALTO_BTN_CONECTAR);
		layeredPane.add(btnConectar, new Integer(1));
		btnConectar.setFocusable(false);
//		btnConectar.setIcon(new ImageIcon(MenuInicioSesion.class.getResource("/frames/BotonMenu.png")));
		btnConectar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				logIn(cliente);
			}
		});

		JLabel labelBackground = new JLabel("");
		labelBackground.setBounds(0, 0, ANCHO_BACK, ALTO_BACK);
//		labelBackground.setIcon(new ImageIcon(MenuInicioSesion.class.getResource("/frames/menuBackground.jpg")));
		layeredPane.add(labelBackground, new Integer(0));
	}

	/**
	 * @param cliente cliente que se logea
	 */
	private void logIn(final Cliente cliente) {
		synchronized (cliente) {
			cliente.setAccion(Comando.INICIOSESION);
			cliente.getPaqueteUsuario().setUsername(textField.getText());
			cliente.getPaqueteUsuario().setPassword(String.valueOf(passwordField.getPassword()));

			cliente.notify();
			dispose();
		}
	}
}
