package View;

//package View;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import Controller.Keyboard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.File;

import Model.Game;
import Model.Player;

public class Window implements ActionListener {
	private Map map;
	public static int width, height;
	private static JFrame window1, window;
	private static JFrame window2;
	private JPanel pan, pan2;
	private JButton startButton, startButton2;
	private JLabel titre, titre2;
	
	
	public Window(int width, int height) {
		//Limite la taille de la fenêtre
		if(width > 1280) {
			width = 1280;
		}
		if(height > 796) {
			height = 796;
		}	

		Window.width = width;
		Window.height = height;
		map = new Map();
		window = new JFrame("window");
		pan = new JPanel();
		
		JLabel background = new JLabel();
		background.setBounds(0, 0, width, height);

		titre = new JLabel();
		titre.setBounds(width/8*3, height/6, width/4, height/6);

		startButton = new JButton();
		startButton.setBounds(width/2-100, height/2-50, 200, 100);
		startButton.setBorderPainted(false); 
		startButton.setContentAreaFilled(false); 
		startButton.addActionListener(this);

		
		//Chargement des images
		try {
			String path = "../../Images/";
			Image titreUlb = ImageIO.read(new File(path+"ULBLogo.png"));
		    Image img = ImageIO.read(new File(path+"play.png"));
		    Image newImg = img.getScaledInstance(200, 100, Image.SCALE_DEFAULT);
		    Image zeldaBack = ImageIO.read(new File(path+"ULB.jpg"));
		    Image newZeldaBack = zeldaBack.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		    startButton.setIcon(new ImageIcon(newImg));
		    titre.setIcon(new ImageIcon(titreUlb.getScaledInstance(width/4, height/6, Image.SCALE_DEFAULT)));
		    background.setIcon(new ImageIcon(newZeldaBack));

		  } catch (Exception ex) {
		    System.out.println(ex);
		  }
		
		
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(width, height);
		
		pan.setFocusable(true);
		pan.requestFocusInWindow();
		pan.setLayout(null);
		pan.setBounds(0, 0, width, height);
		pan.add(startButton);
		pan.add(titre);
		pan.setVisible(true);
		
		background.setVisible(true);
		pan.add(background);
		window.add(pan);
		
		window1 = new JFrame("window");
		window1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window1.setSize(width, height);
		window1.add(map);
		
		window.setVisible(true);
		window.setSize(width, height);
		window1.setVisible(false);
		window1.setBackground(Color.black);
	}
	
	public static void visibleGameOver() {
		Game.lieu = "room";
		Game.newMap("chambreTest");
		window1.setVisible(false);
		window.setVisible(true);
	}
	
	public int[] getSizeWindow() {
		int[] array = {width, height};
		return array;
	}
	
	public void update() {
		this.map.redraw();
	}
	
	public void setKeyListener(KeyListener keyboard) {
        this.map.addKeyListener(keyboard);
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Lancer jeu
		window.setVisible(false);
		if(Game.gameOver) {
			Player.life = 5;
			Player.mana = 5;
			Game.gameOver = false;
		}
		else {
			Keyboard keyboard = new Keyboard(new Game(this));
			this.setKeyListener(keyboard);
		}
		window1.setVisible(true);

		
	}
}
