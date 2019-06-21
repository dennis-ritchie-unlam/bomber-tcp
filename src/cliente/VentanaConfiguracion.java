package cliente;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class VentanaConfiguracion extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField tfHost;
	private JTextField tfPuerto;

	public VentanaConfiguracion(JFrame padre) {
		super(padre, "Configuracion inicial", true);

		JLabel lbHost = new JLabel("Host:");
		JLabel lbPuerto = new JLabel("Puerto:");

		tfHost = new JTextField("localhost");
		tfPuerto = new JTextField("1234");

		JButton btAceptar = new JButton("Aceptar");
		btAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});

		Container c = this.getContentPane();
		c.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(20, 20, 0, 20);

		gbc.gridx = 0;
		gbc.gridy = 0;

		gbc.gridx = 0;
		gbc.gridy = 1;
		c.add(lbHost, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		c.add(lbPuerto, gbc);

		gbc.ipadx = 100;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 1;
		gbc.gridy = 0;

		gbc.gridx = 1;
		gbc.gridy = 1;
		c.add(tfHost, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		c.add(tfPuerto, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.insets = new Insets(20, 20, 20, 20);
		c.add(btAceptar, gbc);

		this.pack(); 
		this.setLocation(450, 200);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
	}

	public String getHost() {
		return this.tfHost.getText();
	}

	public int getPuerto() {
		return Integer.parseInt(this.tfPuerto.getText());
	}

}

