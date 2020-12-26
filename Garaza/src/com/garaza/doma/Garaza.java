package com.garaza.doma;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Garaza {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/garaza";

	static final String USER = "root";
	static final String PASS = "";
	protected static final String Izleze = null;

	java.util.Date dt = new java.util.Date();
    java.text.SimpleDateFormat sdf = 
	     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   String currentTime = sdf.format(dt);

	
	private Connection conn;

	private JFrame frmGaraza;
	private JTable table;
	private JScrollPane jsp;
	protected String Status;
	protected Object getCurrentTimeStamp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Garaza window = new Garaza();
					window.frmGaraza.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Garaza() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			Class.forName(JDBC_DRIVER);
			this.conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JButton btnVlez = new JButton("Влез");

		frmGaraza = new JFrame();
		frmGaraza.setTitle("Гаража");
		frmGaraza.addWindowFocusListener(new WindowFocusListener() {
			public void windowGainedFocus(WindowEvent arg0) {
				loadTableData(btnVlez);
			}

			public void windowLostFocus(WindowEvent arg0) {
			}
		});
		frmGaraza.setBounds(100, 100, 570, 365);
		frmGaraza.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGaraza.getContentPane().setLayout(null);

		//table = new JTable();
		 //table.setBounds(33, 45, 505, 246);
		 //frmGaraza.getContentPane().add(table);

		btnVlez.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Vlez vlez = new Vlez(conn);
				vlez.setVisible(true);

			}
		});
		btnVlez.setBounds(439, 11, 89, 23);
		frmGaraza.getContentPane().add(btnVlez);

		JButton btnIzlez = new JButton("Излез");
		btnIzlez.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				

				Integer selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					return;
				}
				Integer garazaId = (Integer) table.getModel().getValueAt(selectedRow, 0);
				try {
					
					Statement stmt = conn.createStatement();
					String sql = ("UPDATE garaza SET Status = 'Izleze' WHERE garaza_id=" + garazaId);
					String sql1 = ("UPDATE garaza SET Vreme_na_izlez ='"+currentTime+"'WHERE garaza_id="
							+ garazaId);
					stmt.execute(sql1);
					stmt.execute(sql);
					loadTableData(btnVlez);
				} catch (Exception e1) {
					e1.printStackTrace();

				}
			}

		});
		btnIzlez.setBounds(439, 302, 89, 23);
		frmGaraza.getContentPane().add(btnIzlez);

	}

	private void loadTableData(JButton btnIzlez) {
		try {
			Statement stmt = this.conn.createStatement();
			String sql = "SELECT * FROM garaza";
			ResultSet rs = stmt.executeQuery(sql);

			@SuppressWarnings("unused")
			ResultSetMetaData rsmd = rs.getMetaData();
			Vector<String> columns = new Vector<String>();
			columns.addElement("гаража_ид");
			columns.addElement("Бренд");
			columns.addElement("Модел");
			columns.addElement("Регистрација");
			columns.addElement("Статус");
			columns.addElement("Време_на_влез");
			columns.addElement("Време_на_излез");
            
			Vector<Object> rows = new Vector<Object>();
			
			int numResults = 0;

			while (rs.next()) {
				Integer garaza_Id = rs.getInt("garaza_id");
				String Brend = rs.getString("Brend");
				String Model = rs.getString("Model");
				String Registracija = rs.getString("Registracija");
				String Status = rs.getString("Status");
				String Vreme_na_vlez = rs.getString("Vreme_na_vlez");
				String Vreme_na_izlez = rs.getString("Vreme_na_izlez");
                if (Vreme_na_izlez.length() == 0) {
                	numResults++;
                }
				Vector<Object> row = new Vector<Object>();
				row.add(garaza_Id);
				row.add(Brend);
				row.add(Model);
				row.add(Registracija);
				row.add(Status);
				row.add(Vreme_na_vlez);
				row.add(Vreme_na_izlez);
				rows.add(row);
			}
			if (jsp != null) {
				frmGaraza.getContentPane().remove(jsp);
			}
			
			if (numResults >= 10) {
				btnIzlez.setEnabled(false);
			} else {
				btnIzlez.setEnabled(true);
			}
		
			table = new JTable(rows, columns);
			jsp = new JScrollPane(table);
			jsp.setBounds(10, 39, 512, 246);
			frmGaraza.getContentPane().add(jsp);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
