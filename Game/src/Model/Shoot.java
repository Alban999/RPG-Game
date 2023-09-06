package Model;
import View.Map;
import View.Window;

public class Shoot extends GameObject implements Runnable{
	public int[] positionBall;
	public static boolean activateFire = false;
	private Window window;
	private Thread thread;
	private int horizontal, vertical;
	private GameObject removeElement = null;
	public static boolean rechargeShoot = true;
	private boolean toucher = false;
	private String direction;
	
	//Créer le shoot
	public Shoot(GameObject player, int horizontal, int vertical, Window window) {
		super("shoot", false);
		this.thread = new Thread(this); 
		this.window = window;
		this.horizontal = horizontal;
		this.vertical = vertical;		
		positionBall = new int[] {player.getPosition()[0],player.getPosition()[1]};
		thread.start();
		this.activateFire = true;
		
	}
	
	public synchronized void run() { 
		//Lancer l'animation du shoot
		try{
			boolean removeShoot = false;
			if(horizontal>0) {
				positionBall[0] += Map.imageWidth;
				direction = "E";
			}
			else if(horizontal<0) {
				positionBall[0] -= Map.imageWidth;
				direction = "W";
			}
			else if(vertical>0) {
				positionBall[1] += Map.imageHeight;
				direction = "S";
			}
			else {
				positionBall[1] -= Map.imageHeight;
				direction = "N";
			}
			
			for(int i = 0; i<100; i++) {
				setPosition(positionBall);

				synchronized(Game.objects) {
					for(GameObject object:Game.objects) {
						if(object.obstacle && !object.getType().equals("player") && !object.getType().equals("enemy")) {
							switch(direction) {
							case "E":
								if(object.isAtPositionShoot(positionBall[0]+Map.imageWidth-1, positionBall[1])) {
									removeShoot = true;
								}
								break;
							case "W":
								if(object.isAtPositionShoot(positionBall[0]-Map.imageWidth+1, positionBall[1])) {
									removeShoot = true;
								}
								break;
							case "S":
								if(object.isAtPositionShoot(positionBall[0], positionBall[1]+Map.imageHeight-1)) {
									removeShoot = true;
								}
								break;
							case "N":
								if(object.isAtPositionShoot(positionBall[0], positionBall[1]+1)) {
									removeShoot = true;
								}
								break;
						}
						}
						if(object.isAtPosition(positionBall[0], positionBall[1])) {

							if(object.getType().equals("enemy")) {
								toucher = true;
								removeShoot = true;
							}
	
						}
						if(toucher) {
							toucher = false;
							Enemy ennemi = (Enemy) object;
							switch(Player.level) {
									case 0:
										ennemi.hurt(2);
										break;
									case 1:
										ennemi.hurt(3);
	
										break;
									case 2:
										ennemi.hurt(4);
	
										break;
									case 3:
										ennemi.hurt(5);
										break;
									default:
										ennemi.hurt(10);
										break;
							}
							window.update();
							if(ennemi.getLife()==0) {
								removeElement = ennemi;
							}
						}
					}
					if(removeElement != null) {
						Game.Loot(removeElement);
						removeElement = null;
					}
					if(removeShoot) {
						rechargeShoot = true;
						removeShoot = false;
						Game.objects.remove(this);
						break;
					}
				}
				
				window.update();
				Thread.sleep(10);
				if(i == 50) {
					//Permet de pas tirer autant de shoot d'affiler
					rechargeShoot = true;
				}
				switch(direction) {
					case "E":
						positionBall[0] += 1;
						break;
					case "W":
						positionBall[0] -= 1;
						break;
					case "S":
						positionBall[1] += 1;
						break;
					case "N":
						positionBall[1] -= 1;
						break;
				}
			}
			synchronized(Game.objects) {
				Game.objects.remove(this);
				window.update();
			}
			
		}
		catch (Exception e) {};
	}
}
