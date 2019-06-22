package panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;
import comando.Comando;
import entidades.Bomba;
import entidades.Bomber;
import entidades.Mapa;
import entidades.Obstaculo;
import paquete.PaqueteSession;
import javax.swing.JButton;
import javax.swing.JFrame;

public class PanelSession extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private JTextField textField;
	private JPasswordField passwordField;
	private Cliente cliente;
	private PaqueteSession paqueteSession;

	public PanelSession(final Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void setPaqueteSession(PaqueteSession paqueteSession) {
		this.paqueteSession = paqueteSession; 
	}

	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);

		dibujarSesion(g);

		this.setBorder(new EmptyBorder(BORDER, BORDER, BORDER, BORDER));
	}

	private synchronized void dibujarSesion(Graphics g) {
		this.setLayout(null);
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, ANCHO_PANE, ALTO_PANE);
		this.add(layeredPane);

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
	}

	/**
	 * @param cliente cliente que se logea
	 */
	private void logIn(final Cliente cliente) {
		synchronized (cliente) {
		    cliente.setAccion(Comando.INICIAR_SESION);
		    
		    this.paqueteSession.setUsuario(textField.getText());
		    this.paqueteSession.setContraseña(String.valueOf(passwordField.getPassword()));

			cliente.notify();
		}
	}
}
