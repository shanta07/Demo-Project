package com.java.project;

/**
 * This class is been called when user types country name and clicks on enter button.
 * It displays airports and runways of that particular country.
 * 
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * 
 * @author Shanta Hosmani.
 *
 */
public class TableAirport implements ActionListener {

	String name_in, code_in;

	TableAirport(String cname, String ccode) {
		this.name_in = cname;
		this.code_in = ccode;
	}

	public void actionPerformed(ActionEvent er) {

		System.out.println("till here its right 1" + name_in);
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		final String DB_URL = "jdbc:mysql://localhost/airportdb?useSSL=false";

		// Database credentials
		final String USER = "root";
		final String PASS = "root";
		Connection conn = null;
		Statement stmt = null;
		// String country_in = "US";

		try {

			System.out.println("till here its right 2");
			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a querry
			stmt = conn.createStatement();
			ResultSet rs;

			/**
			 * This query results the airports and runways of user entered
			 * country name.
			 */
			String sql;
			sql = "select airports.name,runways.surface from airports,runways where airports.ident = runways.airport_ident"
					+ " and airports.iso_country in (select countries.code from countries where countries.name = '"
					+ name_in + "' " + " OR countries.code = '" + code_in + "') order by airports.name limit 60;";
			rs = stmt.executeQuery(sql);

			System.out.println("till here its right 3");

			ResultSetMetaData rsmt = rs.getMetaData();
			int c = rsmt.getColumnCount();
			Vector column = new Vector(c);

			for (int i = 1; i <= c; i++) {
				column.add(rsmt.getColumnName(i));
			}

			Vector data = new Vector();
			Vector row = new Vector();

			// Extract data from result set
			while (rs.next()) {

				row = new Vector(c);
				for (int i = 1; i <= c; i++) {
					row.add(rs.getString(i));
				}

				data.add(row);
			}

			System.out.println("till here its right 4");
			System.out.println(name_in);
			JFrame frame = new JFrame("Airport demo project at Lunatech");
			frame.setSize(500, 500);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			JPanel panel = new JPanel();
			panel.setBackground(Color.GRAY);
			JTable table = new JTable(data, column);
			table.setBackground(Color.CYAN);
			JScrollPane jsp = new JScrollPane(table);
			JButton back_button = new JButton("back");
			back_button.setBackground(Color.GREEN);

			panel.setLayout(new BorderLayout());
			panel.add(jsp, BorderLayout.CENTER);
			panel.add(back_button, BorderLayout.PAGE_END);

			back_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent r) {
					frame.setVisible(false);
					new AirportDemo();
				}

			});
			// panel.add(exit_button,BorderLayout.AFTER_LAST_LINE);
			frame.setContentPane(panel);
			frame.setVisible(true);

			// Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			// e.printStackTrace();
			JOptionPane.showMessageDialog(null, "ERROR");
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			} // nothing we can do
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				// se.printStackTrace();
				JOptionPane.showMessageDialog(null, "ERROR");
			} // end finally try

		}
	}
}