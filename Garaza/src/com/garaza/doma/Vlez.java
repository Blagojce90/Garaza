package com.garaza.doma;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Vlez extends JDialog {

	java.util.Date dt = new java.util.Date();
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String currentTime = sdf.format(dt);

	@SuppressWarnings("unused")
	private Connection conn;

	private final JPanel contentPanel = new JPanel();
	private JTextField BrendtextField;
	private JTextField ModeltextField;
	private JTextField RegistracijatextField;
	@SuppressWarnings("unused")
	private KeyEvent e;

	/**
	 * Create the dialog.
	 * 
	 * @param conn
	 */
	
	public Vlez(Connection conn) {
		this.conn = conn;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblBrend = new JLabel("Brend");
		lblBrend.setBounds(10, 22, 46, 14);
		contentPanel.add(lblBrend);

		JLabel lblMarka = new JLabel("Model");
		lblMarka.setBounds(10, 47, 46, 14);
		contentPanel.add(lblMarka);

		JLabel lblRegistracija = new JLabel("Registracija");
		lblRegistracija.setBounds(10, 72, 78, 14);
		contentPanel.add(lblRegistracija);

		BrendtextField = new JTextField();
		BrendtextField.setBounds(151, 19, 112, 20);
		contentPanel.add(BrendtextField);
		BrendtextField.setColumns(10);

		ModeltextField = new JTextField();
		ModeltextField.setBounds(151, 44, 112, 20);
		contentPanel.add(ModeltextField);
		ModeltextField.setColumns(10);

		RegistracijatextField = new JTextField();
		RegistracijatextField.setBounds(151, 69, 112, 20);
		contentPanel.add(RegistracijatextField);
		RegistracijatextField.setColumns(10);

		JButton DODAJButton = new JButton("\u0414\u043E\u0434\u0430\u0458");
		DODAJButton.setBounds(261, 143, 93, 23);
		contentPanel.add(DODAJButton);
		DODAJButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(BrendtextField.getText().equals("") || ModeltextField.getText().equals("") || RegistracijatextField.getText().equals("") ){
					JOptionPane.showMessageDialog(null, "enter text");
				}else{
			try {
					Statement stmt = conn.createStatement();

					String sql = "INSERT INTO garaza (Brend, Model, Registracija,Status,Vreme_na_vlez) VALUES ('"
							+ BrendtextField.getText() + "','" + ModeltextField.getText() + "','"
							+ RegistracijatextField.getText() + "','"+"Vo Garaza"+"','" + currentTime + "')";
					stmt.execute(sql);
					stmt.close();
					dispose();
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
			}
		});
		DODAJButton.setActionCommand("DODAJ");
		getRootPane().setDefaultButton(DODAJButton);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			dispose();
		}
	}
	}
