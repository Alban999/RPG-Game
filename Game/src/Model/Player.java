package Model;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import View.Window;

public class Player extends GameObject implements Directionable, Life{
	public int[] positionPlayer;
	public static String direction = "S";
	public static int life = 5;
	public static int mana = 5;
	public static int xp = 0;
	public static int limiteNextLevel = 4;
	public static int level = 0;
	public static boolean shieldActivate = false;
	public static int shieldtime = 100;
	public static ArrayList<GameObject> inventaire = new ArrayList<GameObject>();
	public static int selectInvent = 1;
	public static int sizeInvent = 3;
	public static int money = 100;
	public static boolean hurtBool = false;
	private Timer timer = new Timer();

	//Construction de player
	public Player(int positionX, int positionY) {
		super("player", true);
		positionPlayer = new int[] {positionX, positionY};
		setPosition(positionPlayer);
	}
	
	@Override
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	@Override
	public String getDirection() {
		return direction;
	}
	
	@Override
	public void hurt(int _value) {
		hurtBool = true;
		//Timer permet de rajouter l'animation lorsque le joueur se fait toucher
		timer.schedule(new TimerTask() {
			@Override
			  public void run() {
			    hurtBool = false;
			  }
		}, 100);
		life--;
	}
	public void loseMana() {
		mana--;
	}
	//Update du level du joueur 
	public void levelUpdate() {
		xp++;
		if(xp == limiteNextLevel) {
			level++;
			xp = 0;
		}
	}
	
}
