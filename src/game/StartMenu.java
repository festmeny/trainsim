package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class StartMenu extends JFrame {
	private JButton startButton;
	private JButton exitButton;
	
	private void initComponents(){
		this.setSize(606,629);
		this.setTitle("Projlab");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new Color(34,177,76));
		
		startButton = new JButton();
		startButton.setPreferredSize(new Dimension(500, 100));
		startButton.setText("Start");
		startButton.addActionListener(new StartButtonActionListener());
		startButton.setBackground(new Color(255,242,0));
		startButton.setBorder(new LineBorder(Color.BLACK, 7));
		startButton.setFont(new Font("Arial", Font.PLAIN, 25));
		
		exitButton = new JButton();
		exitButton.setPreferredSize(new Dimension(500, 100));
		exitButton.setText("Exit");
		exitButton.addActionListener(new ExitButtonActionListener());
		exitButton.setBackground(new Color(255,242,0));
		exitButton.setBorder(new LineBorder(Color.BLACK, 7));
		exitButton.setFont(new Font("Arial", Font.PLAIN, 25));
		
		JPanel panelUp = new JPanel();
		JPanel panelDown = new JPanel();
		panelUp.add(startButton);
		panelUp.setBackground(new Color(34,177,76));
		panelDown.add(exitButton);
		panelDown.setBackground(new Color(34,177,76));
		
		this.add(panelUp, BorderLayout.NORTH);
		this.add(panelDown, BorderLayout.SOUTH );

		this.setVisible(true);
		
	}
	private class StartButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
				dispose();
				new MapSelectMenu();
		}
	}
	private class ExitButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
		}
	}
	
	StartMenu getInstance(){
		return StartMenu.this;
	}
	
	public StartMenu(){
		initComponents();
	}
}
