package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class GameOverMenu extends JFrame {
	public JLabel gameOverLabel;
	private JButton startButton;
	
	private void initComponents(){
		this.setSize(600,600);
		this.setTitle("Projlab");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.getContentPane().setBackground(new Color(34,177,76));

		gameOverLabel = new JLabel();
		gameOverLabel.setOpaque(true);
		gameOverLabel.setPreferredSize(new Dimension(500, 100));
		gameOverLabel.setBackground(new Color(237,28,36));
		gameOverLabel.setHorizontalAlignment(JLabel.CENTER);
		gameOverLabel.setBorder(new LineBorder(Color.BLACK, 7));
		gameOverLabel.setFont(new Font("Arial", Font.PLAIN, 30));

		startButton = new JButton();
		startButton.setPreferredSize(new Dimension(500, 100));
		startButton.setText("Main Menu");
		startButton.addActionListener(new StartButtonActionListener());

		startButton.setBackground(new Color(255,242,0));
		startButton.setBorder(new LineBorder(Color.BLACK, 7));
		startButton.setFont(new Font("Arial", Font.PLAIN, 25));


		JPanel panelUp = new JPanel();
		panelUp.setBackground(new Color(34,177,76));
		JPanel panelDown = new JPanel();
		panelDown.setBackground(new Color(34,177,76));
		panelUp.add(gameOverLabel);
		panelDown.add(startButton);
		
		this.add(panelUp, BorderLayout.NORTH);
		this.add(panelDown, BorderLayout.SOUTH );

		this.setVisible(true);
	}
	
	private class StartButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
				getInstance().dispose();
				StartMenu st = new StartMenu();
				st.setVisible(true);
		}
	}
	
	GameOverMenu getInstance(){
		return GameOverMenu.this;
	}
	
	public GameOverMenu(){
		initComponents();
	}
}
