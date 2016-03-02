package com.java.project;

/**
 * This is Airport demo project at Luna Tech.
 * This class displays welcome page to user and prompts 2 options i.e 1.Query 2.Report
 * 
 * To run this program your should add JRE and mysql connector jar files to your project.
 * Database should have 3 tables i.e. countries,airports and runways.
 * I have imported countries.csv,airports.csv and runways.csv files to MYSQL database(airportdb is the database name) countries,airports and runways
 * are the 3 tables respectively.
 * 
 * To run on your machine please change JDBC DRIVER name and DB URL in TableAirport and TableReport class.
 * 
 * On clicking the Query button user is redirected to enter country name to fetch airports and runways information.
 * On clicking Report button user can see 3 tables.First one is top 10 countries having highest number of airports second one is top 10 countries 
 * having lowest number of airports and third one is top 10 common runway latitude.
 * 
 */

import java.awt.*;
import javax.swing.*;



/**
 * 
 * @author Shanta Hosmani.
 *
 */
class AirportDemo {

	AirportDemo() {

		// Create and set JFrame,JPanel,JLbel and JButton.
		JFrame jfrm = new JFrame("Airport demo project at Lunatech");
		jfrm.setSize(500, 500);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.setVisible(true);

		JPanel panel1 = new JPanel(new GridBagLayout());
		panel1.setBackground(Color.GRAY);

		JLabel jlab = new JLabel(" Welcome ");
		jlab.setFont(new Font("Serif", Font.BOLD, 20));

		JButton qbutton = new JButton("Querry");
		JButton rbutton = new JButton("Report");

		qbutton.setBackground(Color.WHITE);
		rbutton.setBackground(Color.WHITE);
		qbutton.setOpaque(true);
		rbutton.setOpaque(true);

		/**
		 * Added ActionListener to Query and Report buttons.
		 */
		qbutton.addActionListener(new QuerryFun());
		rbutton.addActionListener(new TableReport());

		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(10, 10, 10, 10);
		c.gridx = 0;
		c.gridy = 1;
		panel1.add(qbutton, c);
		c.gridx = 0;
		c.gridy = 2;
		panel1.add(rbutton, c);
		panel1.add(jlab);
		jfrm.add(panel1);

	}

	/**
	 * 
	 * @param args
	 * main method calls AirportDemo object.
	 */
	public static void main(String args[]) {

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new AirportDemo();
			}
		});
	}
}