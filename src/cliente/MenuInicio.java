package cliente;

import java.awt.Color;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cliente.ConexionServidor;
import cliente.InterfaceAdapter;
import comando.Comando;
import entidades.Entidad;
import paquete.PaqueteMapa;
import paquete.PaqueteSession;
import servidor.ArchivoDePropiedades;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuInicio extends JFrame {

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
	
	static final int ALTO = 21;
	static final int ANCHO = 21;
	static final int BLOCK_SIZE = 32;

	private PanelSession contentPaneSession;
	private PaqueteSession paqueteSession;
	private JTextField textField;
	private JPasswordField passwordField;

	public MenuInicio(ClienteVentanaMonstruo cliente, PaqueteSession paqueteSession) {
		this.paqueteSession = paqueteSession;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, ANCHO * BLOCK_SIZE + BLOCK_SIZE / 2, ALTO * BLOCK_SIZE + BLOCK_SIZE * 5 / 4);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Bomberman TCP");
		setSize(422, 297);
		this.setVisible(true);
		contentPaneSession = new PanelSession(cliente);
		contentPaneSession.setLayout(null);
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 418, 271);
		contentPaneSession.add(layeredPane);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(X_LB_PASS, Y_LB_PASS, ANCHO_LB_PASS, ALTO_LB_PASS);
		layeredPane.add(lblPassword);
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, TAM_TXT));
		lblPassword.setForeground(Color.BLACK);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(X_USUARIO, Y_USUARIO, ANCHO_USUARIO, ALTO_USUARIO);
		layeredPane.add(lblUsuario, new Integer(2));
		lblUsuario.setForeground(Color.BLACK);
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, TAM_TXT));

		textField = new JTextField();
//		textField.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(final ActionEvent arg0) {
//				logIn(cliente);
//			}
//		});
		textField.setBounds(X_TF, Y_TF, ANCHO_TF, ALTO_TF);
		layeredPane.add(textField, new Integer(1));
		textField.setColumns(COLUMNAS);

		passwordField = new JPasswordField();
//		passwordField.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(final ActionEvent e) {
//				logIn(cliente);
//			}
//		});
		passwordField.setBounds(X_PASS, Y_PASS, ANCHO_PASS, ALTO_PASS);
		layeredPane.add(passwordField, new Integer(1));
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, TAM_TXT_PASSWORD));
		
		Button button = new Button("Iniciar Sesi\u00F3n");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logIn(cliente);
			}
		});
		button.setBounds(191, 183, 70, 21);
		layeredPane.add(button);

		contentPaneSession.setPaqueteSession(this.paqueteSession);
		contentPaneSession.repaint();
		contentPaneSession.setVisible(true);
//		JOptionPane.showMessageDialog(null,"DESPUES DE PINTAR INICIAR SESION");
		setContentPane(contentPaneSession);
		this.repaint();
		this.setBackground(new Color(0));
		

	}
	
	private void logIn(final ClienteVentanaMonstruo cliente) {
		synchronized (cliente) {
		    cliente.setAccion(Comando.INICIAR_SESION);
		    
		    this.paqueteSession.setUsuario(textField.getText());
		    this.paqueteSession.setContraseña(String.valueOf(passwordField.getPassword()));

			cliente.notify();
			dispose();
		}
	}
}
