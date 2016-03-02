package com.java.project;

/**
 * This class is been called when user clicks on Report button in welcome page.
 * It displays 3 tables.First table displays Top 10 common runways latitude.Second table displays Top 10 countries with lease number of airports
 * Third table displays Top 10 countries with highest number of airports.
 */
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;
import javax.swing.*;

/**
 * 
 * @author Shanta Hosmani.
 *
 */
public class TableReport implements ActionListener {
	public void actionPerformed(ActionEvent aee) {

		// Create and set JFrame,JPanel,JLabel and JButton.
		JFrame frame = new JFrame("Airport demo  project at Lunatech");
		JOptionPane.showMessageDialog(frame, "please wait!!Reports is generating");
		JLabel label1 = new JLabel("Countries with highest no.of airports");
		JLabel label2 = new JLabel("Countries with least no. of airports");

		JButton back = new JButton("Back");
		back.setBackground(Color.green);

		frame.setSize(900, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);

		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		final String DB_URL = "jdbc:mysql://localhost/airportdb?useSSL=false";

		// Database credentials
		final String USER = "root";
		final String PASS = "root";
		Connection conn = null;
		Statement stmt = null;

		try {

			// Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			// Execute a query
			stmt = conn.createStatement();
			ResultSet rs, rs2, rs3;

			/**
			 * This query results top 10 countries with highest number of
			 *  airports with count of each airports.
			 */
			
			String sql;
			sql = "select countries.name,airports.iso_country,count(*) from countries,"
					+ "airports where countries.code = airports.iso_country group by "
					+ "airports.iso_country order by count(*)  desc limit 10;";
			rs = stmt.executeQuery(sql);

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

			/**
			 * This query results top 10 countries with lowest number of
			 * airports with count of each airports.
			 */
			
			String sql2 = "select countries.name,airports.iso_country,count(*) from countries,"
					+ "airports where countries.code = airports.iso_country group by "
					+ "airports.iso_country order by count(*)  asc limit 10;";
			rs2 = stmt.executeQuery(sql2);

			ResultSetMetaData rsmt2 = rs2.getMetaData();
			int c1 = rsmt2.getColumnCount();
			Vector lcolumn = new Vector();
			Vector ldata = new Vector();
			Vector lrow = new Vector();
			for (int j = 1; j <= c1; j++) {
				lcolumn.add(rsmt2.getColumnName(j));
			}

			while (rs2.next()) {

				lrow = new Vector(c1);
				for (int j = 1; j <= c1; j++) {
					lrow.add(rs2.getString(j));

				}
				ldata.add(lrow);

			}

			// This query results in top 10 common runway latitude with count.
			String sql3 = "select airports.name,runways.le_ident,count(*) from airports,runways "
					+ "where airports.ident = runways.airport_ident group by runways.le_ident order by count(*) desc limit 10;";
			rs3 = stmt.executeQuery(sql3);

			ResultSetMetaData rsmt3 = rs3.getMetaData();
			int c2 = rsmt.getColumnCount();
			Vector rcolumn = new Vector();
			Vector rdata = new Vector();
			Vector rrow = new Vector();
			for (int j = 1; j <= c2; j++) {
				rcolumn.add(rsmt3.getColumnName(j));
			}

			while (rs3.next()) {

				rrow = new Vector(c2);
				for (int j = 1; j <= c2; j++) {
					rrow.add(rs3.getString(j));

				}
				rdata.add(rrow);

			}

			System.out.println("successfull");

			JTable table = new JTable(data, column);
			table.setBackground(Color.LIGHT_GRAY);
			JScrollPane jsp = new JScrollPane(table);
			JTable ltable = new JTable(ldata, lcolumn);
			ltable.setBackground(Color.CYAN);
			JScrollPane jsp2 = new JScrollPane(ltable);
			JTable rtable = new JTable(rdata, rcolumn);
			rtable.setBackground(Color.LIGHT_GRAY);
			JScrollPane jsp3 = new JScrollPane(rtable);
			panel.setLayout(new BorderLayout());
			panel.add(label1, BorderLayout.EAST);
			panel.add(jsp, BorderLayout.EAST);
			panel.add(label2, BorderLayout.CENTER);
			panel.add(jsp2, BorderLayout.CENTER);
			panel.add(jsp3, BorderLayout.WEST);
			panel.add(back, BorderLayout.PAGE_END);
			frame.setContentPane(panel);
			frame.setVisible(true);

			back.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					frame.setVisible(false);
					new AirportDemo();

				}
			});

			// Clean-up environment
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			// Handle errors for JDBC
			// se.printStackTrace();
			JOptionPane.showMessageDialog(null, "ERROR");
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

		} // end try

	}

}
