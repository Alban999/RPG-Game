package Model;
import java.util.*;

public class Enemy extends GameObject implements Directionable, Life{
	//Genère des vies aléatoire de l'ennemi
	private Random randomGenerator = new Random();
	public int life = randomGenerator.nextInt(6)+1;
	private String direction = "S";
	public boolean hurtBool = false;
	private Timer timer = new Timer();
	
	//Définition de l'ennemi
	public Enemy() {
		super("enemy", true);
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
	public void hurt(int degats) {
		hurtBool = true;
		//Timer permet l'animation lorsqu'on est blessé
		timer.schedule(new TimerTask() {
			@Override
			  public void run() {
			    hurtBool = false;
			  }
		}, 100);
		life -= degats;
		if(life<0) {
			life = 0;
		}
	}
	
	public boolean getHurtBool() {
		return hurtBool;
	}
	
	public int getLife() {
		return life;
	}

}
