package Model;

import View.Window;
import View.Map;

//Classe qui permet l'animation, comme par exemple le déplacement du personnage et de l'ennemi d'une manière fluide
public class BougerAuto implements Runnable {
	private GameObject object; 
	private Player player; 
	private Thread thread;
	private Window window;
	private int counter = 0;
	private Enemy ennemi;
	private boolean move = false;
	private int[] positionDef;
	
	//Définition de la classe
	public BougerAuto(GameObject object, Player player, Window window){ 
		this.object = object;
		this.player = player;
		
		//On crée et lance le thread
		this.thread = new Thread(this); 
		this.window = window;
		thread.start();
		if(object != null) {
			//On prend le nombre d'images qu'a besoin l'animation
			counter = object.getCounter();
		}
	}
	
	//Cette fonction permet de savoir si l'ennemi peut atteindre le joueur par la méthode d'approche fonctionMove()
	private boolean boucle() {
		boolean go = true;
		positionDef = new int[] {ennemi.getPosition()[0],ennemi.getPosition()[1]};
		for(int i = 0; i<30 ;i++) {
			if(!fonctionMove()) {
				go = false;
				break;
			}
		}
		return go;
	}
	
	private boolean fonctionMove() {
		//Fonction de déplacement de l'ennemi
		int[] positionTest = new int[] {positionDef[0], positionDef[1]};
		int difX = Math.abs(player.getPosition()[0]-positionTest[0]);
		int difY = Math.abs(player.getPosition()[1]-positionTest[1]);
		boolean continuer = true;
		move = false;
		if(difX>difY) {
			if(positionTest[0] < player.getPosition()[0]) {
				positionTest = new int[] {positionDef[0]+Map.imageWidth, positionDef[1]};
			}
			else {
				positionTest = new int[] {positionDef[0]-Map.imageWidth, positionDef[1]};
			}
		}
		else {
			if(positionTest[1] < player.getPosition()[1]) {
				positionTest = new int[] {positionDef[0], positionDef[1]+Map.imageHeight};
			}
			else {
				positionTest = new int[] {positionDef[0], positionDef[1]-Map.imageHeight};
				
			}
		}
			
		for(GameObject element : Game.objects) {
			if(element.isAtPosition(positionTest[0], positionTest[1])) {
				if(element.obstacle) {
					if(element.getType().equals("player")) {
						continuer = true;
					}
					else {
						continuer = false;
					}
				}
			}				
		}
		if(continuer) {
			positionDef = new int[] {positionTest[0], positionTest[1]};
		}
		return continuer;
}
	//Fonction lancer lors du thread
	public synchronized void run() { 
		try{
			if(object == null) {
				//Appliquer le thread qui permet d'activer un bouclier pendant un certain temps
				Thread.sleep(Player.shieldtime);
				Player.shieldActivate = false;
				window.update();
			}
			if(object.getType().equals("enemy")) {
				//Transformer le GameObject ennemi en Enemi 
				ennemi = (Enemy) object;
			}
			if(object.getType().equals("enemy") && !object.activate) {
				//Faire bouger l'ennemi
				boolean move = false;
				boolean frontOfPlayer = false;
				int[] position = new int[] {object.getPosition()[0],object.getPosition()[1]};
				int[] positionBegin = new int[] {0,0};
				String directionMemory = "";
				
				//Tant que le jeu contient le joueur et l'ennemi, la thread continue
				while(Game.objects.contains(player) && Game.objects.contains(object)){ 
					/*Calcul de la trajectoire de l'enemi en calculant la plus grande distance qui le joueur et l'ennemi.
					Si leur différence d'abscisses est la plus grande. L'ennemi se déplacera en x et inversément. */
					int difX = Math.abs(player.getPosition()[0]-object.getPosition()[0]);
					int difY = Math.abs(player.getPosition()[1]-object.getPosition()[1]);
					move = false;
					positionBegin = new int[] {object.getPosition()[0],object.getPosition()[1]};
					
					//Verification selon la fonction boucle sinon l'ennemi se déplace en longeant les murs 
					if(boucle() || frontOfPlayer) {
						if(difX>difY) {
							if(object.getPosition()[0] < player.getPosition()[0]) {
								position = new int[] {object.getPosition()[0]+Map.imageWidth, object.getPosition()[1]};
								ennemi.setDirection("E");
							}
							else {
								position = new int[] {object.getPosition()[0]-Map.imageWidth, object.getPosition()[1]};
								ennemi.setDirection("W");
							}
						}
						else {
							if(object.getPosition()[1] < player.getPosition()[1]) {
								position = new int[] {object.getPosition()[0], object.getPosition()[1]+Map.imageHeight};
								ennemi.setDirection("S");
							}
							else {
								position = new int[] {object.getPosition()[0], object.getPosition()[1]-Map.imageHeight};
								ennemi.setDirection("N");
								
							}
						}
					}
					else {
						if(directionMemory.equals("")) {
							switch(ennemi.getDirection()) {
							case "E":
								position = new int[] {positionBegin[0], positionBegin[1]+Map.imageHeight};
								ennemi.setDirection("S");
								break;
							case "W":
								position = new int[] {positionBegin[0], positionBegin[1]-Map.imageHeight};
								ennemi.setDirection("N");
								break;
							case "S":
								position = new int[] {positionBegin[0]-Map.imageWidth, positionBegin[1]};
								ennemi.setDirection("W");
								break;
							case "N":
								position = new int[] {positionBegin[0]+Map.imageWidth, positionBegin[1]};
								ennemi.setDirection("E");
								break;
							default:
								break;
							}
							directionMemory = "libre";
						}
						else if(directionMemory!=ennemi.getDirection()) {
							switch(directionMemory) {
								case "E":
									position = new int[] {positionBegin[0]+Map.imageWidth, positionBegin[1]};
									ennemi.setDirection("E");
									break;
								case "W":
									position = new int[] {positionBegin[0]-Map.imageWidth, positionBegin[1]};
									ennemi.setDirection("W");
									break;
								case "S":
									position = new int[] {positionBegin[0], positionBegin[1]+Map.imageHeight};
									ennemi.setDirection("S");
									break;
								case "N":
									position = new int[] {positionBegin[0], positionBegin[1]-Map.imageHeight};
									ennemi.setDirection("N");
									break;
								default:
									break;
							}
						}
					}
				frontOfPlayer = false;
				while(!move && !frontOfPlayer) {
					move = true;
					//Parcours les objets qui se trouvznt sur la trajectoire de l'ennemi
					for(GameObject element : Game.objects) {
						if(element.isAtPosition(position[0], position[1])) {
							if(element.obstacle) {
								move = false;
								if(element.getType().equals("player")) {
									frontOfPlayer = true;
									ennemi.activate = true;
									Game.removeElement = object;
									if(Player.life>0 && !Player.shieldActivate) {
										player.hurt(1);
										window.update();
									}
								}
								else {
									directionMemory = ennemi.getDirection();
									switch(ennemi.getDirection()) {
									case "E":
										position = new int[] {positionBegin[0], positionBegin[1]-Map.imageHeight};
										ennemi.setDirection("N");
										break;
									case "W":
										position = new int[] {positionBegin[0], positionBegin[1]+Map.imageHeight};
										ennemi.setDirection("S");
										break;
									case "S":
										position = new int[] {positionBegin[0]+Map.imageWidth, positionBegin[1]};
										ennemi.setDirection("E");
										break;
									case "N":
										position = new int[] {positionBegin[0]-Map.imageWidth, positionBegin[1]};
										ennemi.setDirection("W");
										break;
									}
								}
							}
							else {
								move = true;									
							}
						}				
					}
				}

				if(ennemi.getDirection()==directionMemory) {
					directionMemory = "";
				}
				
				if(Player.life == 0) {
					Game.objects.remove(player);	
					Window.visibleGameOver();
					Game.gameOver = true;
					Thread.interrupted();
				}
				if(move) {
					ennemi.setCounter(5);
					ennemi.activate = true;
					new BougerAuto(ennemi, player, window);

				}
				else {
					ennemi.activate = false;
				}
				Thread.sleep(600);	
				}
			}
			if(object.activate) {
				//Animation : Déplacement fluide du joueur
				if(object.getType().equals("player")) {
					int debut = 1;
					int positionX = player.getPosition()[0]; 
					int positionY = player.getPosition()[1]; 
					int positionXBis = player.getPosition()[0]; 
					int positionYBis = player.getPosition()[1]; 
					int[] position = {positionX, positionY};
					int horizontal = Game.horizontal;
					int vertical = Game.vertical;
					switch(player.getDirection()) {
						case "N":
							debut = 1;
							break;
						case "S":
							debut = 11;
							break;
						case "W":
							debut = 16;
							break;
						case "E":
							debut = 6;
							break;
						default:
							break;
					}
					
					for(int u = debut;u<counter+debut;u++) {
						positionX += horizontal/counter;
						positionY += vertical/counter;
						position = new int[] {positionX, positionY};
						object.setPosition(position);
						object.setCounter(u);
						window.update();
						Thread.sleep(40);
					}
					position = new int[] {positionXBis+horizontal, positionYBis+vertical};
					object.setPosition(position);
					window.update();
					Game.finishMove = true;
					}
					else if(object.getType().equals("enemy")){
						//Animation : Déplacement fluide dé l'ennemi
						int debut = 1;
						int positionX = ennemi.getPosition()[0]; 
						int positionY = ennemi.getPosition()[1];
						int positionXBis = ennemi.getPosition()[0]; 
						int positionYBis = ennemi.getPosition()[1];
						int[] position = {positionX, positionY};
						int deplacementH = 0;
						int deplacementV = 0;
						switch(ennemi.getDirection()) {
							case "N":
								debut = 1;
								deplacementV = -Map.imageHeight;
								break;
							case "S":
								debut = 11;
								deplacementV = Map.imageHeight;
								break;
							case "W":
								debut = 16;
								deplacementH = -Map.imageWidth;
								break;
							case "E":
								debut = 6;
								deplacementH = Map.imageWidth;
								break;
							default:
								break;
						}
						for(int u = debut;u<counter+debut;u++) {
							positionX += deplacementH/counter;
							positionY += deplacementV/counter;
							position = new int[] {positionX, positionY};
							object.setPosition(position);
							object.setCounter(u);
							window.update();
							Thread.sleep(80);
						}
						position = new int[] {positionXBis+deplacementH, positionYBis+deplacementV};
						object.setPosition(position);
						window.update();
					}
					else if(object.getType().equals("chest")){
						//Animation du coffre
						for(int u = 1;u<counter;u++) {
							object.setCounter(u);
							window.update();
							Thread.sleep(300);
						}
						object.setCounter(1);
						Game.animationChest = false;
						Game.Loot(object);
					}
					Thread.interrupted();

				}
				

		}
				
		catch (Exception e) {};
	}
}