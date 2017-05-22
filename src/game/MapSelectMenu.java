package game;

import stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class MapSelectMenu extends JFrame{
	private JButton map1Button;
	private JButton map2Button;
	private JButton map3Button;
	private JButton map4Button;
	
	private void initComponents(){
		this.setSize(606,629);
		this.setTitle("Projlab");
		this.setLayout(new GridLayout(4,1));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBackground(new Color(34,177,76));
		
		map1Button = new JButton();
		map1Button.setText("Normal map");
		map1Button.addActionListener(new Map1ButtonActionListener());
		map1Button.setBackground(new Color(255,242,0));
		map1Button.setBorder(new LineBorder(Color.BLACK, 7));
		map1Button.setFont(new Font("Arial", Font.PLAIN, 25));
		
		map2Button = new JButton();
		map2Button.setText("Cross map");
		map2Button.addActionListener(new Map2ButtonActionListener());
		map2Button.setBackground(new Color(255,242,0));
		map2Button.setBorder(new LineBorder(Color.BLACK, 7));
		map2Button.setFont(new Font("Arial", Font.PLAIN, 25));
		
		map3Button = new JButton();
		map3Button.setText("Switch map");
		map3Button.addActionListener(new Map3ButtonActionListener());
		map3Button.setBackground(new Color(255,242,0));
		map3Button.setBorder(new LineBorder(Color.BLACK, 7));
		map3Button.setFont(new Font("Arial", Font.PLAIN, 25));

		map4Button = new JButton();
		map4Button.setText("Station map");
		map4Button.addActionListener(new Map4ButtonActionListener());
		map4Button.setBackground(new Color(255,242,0));
		map4Button.setBorder(new LineBorder(Color.BLACK, 7));
		map4Button.setFont(new Font("Arial", Font.PLAIN, 25));
		
		this.add(map1Button);
		this.add(map2Button);
		this.add(map3Button);
		this.add(map4Button);

		this.setVisible(true);
	}
	private class Map1ButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			getInstance().dispose();
			Stage stage = new Stage();

			//stage.init();
			//stage.save("maps/cross.dat");

			stage.load("maps/normal.dat");
			stage.generateTrain(new Random().nextInt(4)+1);
			stage.startSimulation();
		}
	}
	private class Map2ButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			getInstance().dispose();
			Stage stage = new Stage();

			stage.load("maps/cross.dat");
			stage.generateTrain(new Random().nextInt(4)+1);
			stage.startSimulation();
		}	
	}
	private class Map3ButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			getInstance().dispose();
			Stage stage = new Stage();

			stage.load("maps/switch.dat");

			stage.generateTrain(new Random().nextInt(4)+1);
			stage.startSimulation();
		}	
	}
	private class Map4ButtonActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			getInstance().dispose();
			Stage stage = new Stage();

			stage.load("maps/station.dat");
			stage.init();
			stage.generateTrain(new Random().nextInt(4)+1);
			stage.startSimulation();
		}	
	}
	
	MapSelectMenu getInstance(){
		return MapSelectMenu.this;
	}
	public MapSelectMenu(){
		initComponents();
	}
}
