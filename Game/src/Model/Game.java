package Model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import View.Map;
import View.Window;

public class Game{
	private static Window window;
	private static Player player;
	public static int horizontal = 0, vertical = 0;
	public static Shoot ballFire;
	public static ArrayList<Shoot> fireBalls = new ArrayList<>();
	public static ArrayList<GameObject> objects = new ArrayList<GameObject>();
	private boolean bouger = true;
	public static GameObject removeElement = null;
	public static boolean animationChest = false;
	private static boolean changeMap = false;
	private boolean ramasser = false;
	public static Boolean finishMove = true;
	public static Shop shopBar;
	public static Shop shopLibrary;
	private static String sol = "0";
	public static String lieu = "room";
	private String aller = "city";
	public static int numeroMap = 1;
	public static boolean gameOver = false;
	private static String path = "/Users/Alban/Alban/Ecole/BA2/Info/ressources/maps/";
	
	public Game(Window window) {
		this.window = window;
		shopBar = new Shop(new String[]{"carapils", "jupiler", "potionLife", "penne"}, false, "bar", window);
		shopLibrary = new Shop(new String[]{"book", "potionMana", "equerre", "crayon", "calculette", "lunettes"}, false, "library", window);
		//Lecture de fichier création de la map
		int ordonnee = 0, abscisse = 0;
		try{
			InputStream flux=new FileInputStream(path + "chambreTest.txt"); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String ligne;
			Map.nbreCellW = 23; Map.nbreCellH = 25;

			Map.imageWidth = Window.width/Map.nbreCellW;Map.imageHeight = Window.height/Map.nbreCellH;
			while ((ligne=buff.readLine())!=null){
				abscisse = 0;
				String[] line = ligne.split("");
				for(String a:line) {
					if(!a.equals("/")) {
						switch(a) {
							case "0":
								GameObject ground = new GameObject("ground", false);
								ground.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
								
								objects.add(ground);
								sol = a;
								break;
						}
					}
					if(a.equals("1")) {
						GameObject blockUnbreakable = new GameObject("blockUnbreak", true);
						blockUnbreakable.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						
						objects.add(blockUnbreakable);
					}
					else if(a.equals("2")) {
						player = new Player(Map.imageWidth*abscisse, Map.imageHeight*ordonnee);
						objects.add(player);
					}
					else if(a.equals("3")) {
						Enemy enemy = new Enemy();
						enemy.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(enemy);
					}
					else if(a.equals("4")) {
						GameObject chest = new GameObject("chest", true);
						chest.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(chest);
					}
					else if(a.equals("5")) {
						GameObject door = new GameObject("door", true);
						door.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(door);
					}
					else if(a.equals("c")) {
						GameObject door = new GameObject("door1", true);
						door.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(door);
					}
					else if(a.equals("d")) {
						GameObject door = new GameObject("door2", true);
						door.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(door);
					}
					else if(a.equals("e")) {
						GameObject door = new GameObject("door3", true);
						door.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(door);
					}
					else if(a.equals("b")) {
						GameObject ground = new GameObject("rien", true);
						ground.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(ground);
					}
					else if(a.equals("t")) {
						GameObject teacher = new GameObject("teacher", true);
						teacher.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(teacher);
					}
					else if(a.equals("s")) {
						GameObject shop = new GameObject("shop", true);
						shop.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(shop);
					}
					abscisse++;
				}
				ordonnee++;
			}
			buff.close(); 
			}		
			catch (Exception e){
				System.out.println(e.toString());
			}
		
		//Triage de la liste pour permettre d'afficher le player après les objets ground
		int v = 0, index = 0;
		for(GameObject object:objects) {
			index = objects.indexOf(object);
			if(object.getType().contains("ground")) {
				do {
					if(!objects.get(v).getType().contains("ground")) {
						objects.set(index, objects.get(v));
						objects.set(v, object);
					}
					v++;
				}
				while(objects.get(v).getType().contains("ground") && v < objects.size()-1);
				
			}
			else if(object.getType().equals("enemy")) {
				moveEnemy(object, player);
			}

		}
	}
	
	//Fonction qui crée l'animation de l'ennemie
	public static void moveEnemy(GameObject object, Player player) {
		new BougerAuto(object, player, window);
	}
	
	public void movePlayer(String direction) {
		removeElement = null;
		bouger = true;
		horizontal = 0;
		vertical = 0;
		switch(direction){
			case "E":
				horizontal = Map.imageWidth;
				break;
			case "W":
				horizontal = -Map.imageWidth;
				break;
			case "S":
				vertical = Map.imageHeight;
				break;
			case "N":
				vertical = -Map.imageHeight;
				break;
			default:
				break;
		}
		player.setDirection(direction);

		int positionX = player.getPosition()[0]+horizontal; 
		int positionY = player.getPosition()[1]+vertical; 

		//Check les objets sur le chemin
		synchronized(objects) {
			boolean removeLoot=  false;
			for(GameObject object:Game.objects) {
				if(object.isAtPositionInFrontOf(positionX, positionY)) {
					if(object.getType().equals("enemy") || object.getType().equals("chest") || object.getType().equals("teacher") || object.getType().equals("shop")) {
						removeElement = object;
					}
				}
				if(object.isAtPosition(positionX, positionY)) {
					if(object.obstacle) {
						bouger = false;
						if(object.getType().equals("door") || object.getType().equals("door1") || object.getType().equals("door2") || object.getType().equals("door3")) {
							ramasser = true;
						}
						if(object.getType().equals("enemy") || object.getType().equals("chest") || object.getType().equals("teacher") || object.getType().equals("shop")) {
							removeElement = object;
						}
					}
					else {
						ramasser = true;
					}
				}
				
				//Si l'objet qui se trouve sur le chemin s eramasse
				if(ramasser) {
					ramasser =  false;
					switch(object.getType()) {
					case "life":
						removeLoot = true;
						if(Player.life<5) {
							Player.life++;
						}
						break;
					case "mana":
						removeLoot = true;
						if(Player.mana<5) {
							Player.mana++;
						}
						break;
					case "door":
						changeMap = true;
						aller = "city";
						break;
					case "door1":
						changeMap = true;
						switch(lieu) {
						case "city":
							aller = "room";
							break;
						case "school":
							aller = "class";
							break;
						case "pub":
							aller = "school";
							break;
						}
						break;
					case "door2":
						changeMap = true;
						switch(lieu) {
						case "school":
							aller = "library";
							break;
						}
						break;
					case "door3":
						changeMap = true;
						switch(lieu) {
						case "school":
							aller = "pub";
							break;
						}
						break;
					case "stair":
						changeMap = true;
						aller = "game";
						break;
					case "sword":
						if(Player.inventaire.size() < Player.sizeInvent) {
							removeLoot = true;
							Player.inventaire.add(object);
						}
						break;
					case "potionMana":
						if(Player.inventaire.size() < Player.sizeInvent) {
							removeLoot = true;
							Player.inventaire.add(object);
						}
						break;
					case "potionLife":
						if(Player.inventaire.size() < Player.sizeInvent) {
							removeLoot = true;
							Player.inventaire.add(object);
						}
						break;
					case "shield":
						if(Player.inventaire.size() < Player.sizeInvent) {
							removeLoot = true;
							Player.inventaire.add(object);
						}
						break;
					case "bag":
						if(Player.inventaire.size() < Player.sizeInvent) {
							removeLoot = true;
							Player.inventaire.add(object);
						}
						break;
					}
					removeElement = object;
				}
			}
			if(removeLoot) {
				removeLoot = false;
				objects.remove(removeElement);
			}	
		}
		if(bouger && !changeMap && finishMove) {
			finishMove = false;
			player.setCounter(5);
			player.activate = true;
			//Lancer l'animation du player
			new BougerAuto(player, player, window);
		}
		if(!bouger) {
			player.activate = false;
		}
		
		//Si il marche sur une porte on change de map
		if(changeMap) {
			switch(lieu) {
				case "room":
					lieu = "city";
					newMap("villeMap");
					break;
				case "city":
					if(aller.equals("room")){
						lieu = "room";
						newMap("chambreTestBis");
					}
					else {
						lieu = "school";
						newMap("schoolTest");
					}
					break;
				case "school":
					if(aller.equals("class")){
						lieu = "class";
						newMap("classTest");
					}
					else if(aller.equals("library")) {
						lieu = "library";
						newMap("libraryTest");
					}
					else if(aller.equals("pub")) {
						lieu = "pub";
						newMap("barTest");
					}
					else {
						lieu = "city";
						newMap("villeMap1");
					}
					break;
				case "class":
					if(aller.equals("game")) {
						lieu = "game";
						newMap("Map1");
					}
					else {
						lieu = "school";
						newMap("schoolTest1");
					}
					break;
				case "library":
					lieu = "school";
					newMap("schoolTest2");
					break;
				case "pub":
					lieu = "school";
					newMap("schoolTest");
					break;
				case "game":
					lieu = "game";
					newMap("Map"+numeroMap);
					break;
			}
			
		}
		window.update();

	}
	
	public static void newMap(String name){
		objects = new ArrayList<GameObject>();
		int ordonnee = 0, abscisse = 0;
		switch(lieu) {
		case "room":
			Map.nbreCellW = 23; Map.nbreCellH = 25;
			break;
		case "city":
			Map.nbreCellW = 41; Map.nbreCellH = 35;
			break;
		case "school":
			Map.nbreCellW = 31; Map.nbreCellH = 27;
			break;
		case "library":
			Map.nbreCellW = 26; Map.nbreCellH = 35;
			break;
		case "pub":
			Map.nbreCellW = 13; Map.nbreCellH = 15;
			break;
		case "class":
			Map.nbreCellW = 17; Map.nbreCellH = 20;
			break;
		case "game":
			Map.nbreCellW = 25; Map.nbreCellH = 25;
			break;
		}
		Map.imageWidth = Window.width/Map.nbreCellW;Map.imageHeight = Window.height/Map.nbreCellH;
		try{
			InputStream flux=new FileInputStream(path+name+".txt"); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String ligne;
			while ((ligne=buff.readLine())!=null){
				abscisse = 0;
				String[] line = ligne.split("");
				
				for(String a:line) {
					if(!a.equals("/")) {
						switch(a) {
							case "0":
								GameObject ground = new GameObject("ground", false);
								ground.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
								
								objects.add(ground);
								sol = a;
								break;
							default:
								if(lieu == "game") {
									switch(sol) {
									case "0":
										ground = new GameObject("ground", false);
										ground.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
										
										objects.add(ground);
										break;
								}
								break;
							}
						}
					}
					if(a.equals("1")) {
						GameObject blockUnbreakable = new GameObject("blockUnbreak", true);
						blockUnbreakable.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						
						objects.add(blockUnbreakable);
					}
					else if(a.equals("2")) {
						player = new Player(Map.imageWidth*abscisse, Map.imageHeight*ordonnee);
						objects.add(player);
					}
					else if(a.equals("3")) {
						Enemy enemy = new Enemy();
						enemy.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(enemy);
					}
					else if(a.equals("4")) {
						GameObject chest = new GameObject("chest", true);
						chest.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(chest);
					}
					else if(a.equals("5")) {
						GameObject door = new GameObject("door", true);
						door.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(door);
					}
					else if(a.equals("c")) {
						GameObject door = new GameObject("door1", true);
						door.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(door);
					}
					else if(a.equals("d")) {
						GameObject door = new GameObject("door2", true);
						door.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(door);
					}
					else if(a.equals("e")) {
						GameObject door = new GameObject("door3", true);
						door.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(door);
					}
					else if(a.equals("b")) {
						GameObject ground = new GameObject("rien", true);
						ground.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(ground);
					}
					else if(a.equals("t")) {
						GameObject teacher = new GameObject("teacher", true);
						teacher.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(teacher);
					}
					else if(a.equals("s")) {
						GameObject shop = new GameObject("shop", true);
						shop.setPosition(new int[] {Map.imageWidth*abscisse, Map.imageHeight*ordonnee});
						objects.add(shop);
					}
					abscisse++;
				}
				ordonnee++;
			}
			buff.close(); 
			}		
			catch (Exception e){
				System.out.println(e.toString());
			}
		int v = 0, index = 0;
		for(GameObject object:objects) {
			index = objects.indexOf(object);
			if(object.getType().contains("ground")) {
				do {
					if(!objects.get(v).getType().contains("ground")) {
						objects.set(index, objects.get(v));
						objects.set(v, object);
					}
					v++;
				}
				while(objects.get(v).getType().contains("ground") && v < objects.size()-1);
				
			}
			else if(object.getType().equals("enemy")) {
				moveEnemy(object, player);
			}

		}
		window.update();
		changeMap = false;
	}
	
	public void taper() {
		if(removeElement != null && removeElement.getType().equals("enemy")) {
			Enemy ennemie = (Enemy) removeElement;
			if(Player.inventaire.size() != 0 && Player.inventaire.size() >= Player.selectInvent) {
				switch(Player.inventaire.get(Player.selectInvent-1).getType()) {
					case "sword":
						ennemie.hurt(2);
						break;
					case "equerre":
						ennemie.hurt(5);
						break;
					case "crayon":
						ennemie.hurt(10);
						break;
					default:
						ennemie.hurt(1);
						break;
				}
				window.update();
			}
			else {
				ennemie.hurt(1);
				window.update();
			}
			
			if(ennemie.getLife() == 0) {
				Loot(removeElement);
				removeElement = null;
			}
		}
	}
	
	public static void Loot(GameObject object) {
		Player.money += 5;
		player.levelUpdate();
		objects.remove(object);
		//Generate loot
		String type = "&";
		boolean obstacle = false;
		Random randomGenerator = new Random();
		switch(randomGenerator.nextInt(7)) {
			case 0:
				type = "mana";
				obstacle = false;
				break;
			case 1:
				type = "life";
				obstacle = false;
				break;
			case 2:
				type = "sword";
				obstacle = false;
				break;
			case 3:
				type = "potionMana";
				obstacle = false;
				break;
			case 4:
				type = "potionLife";
				obstacle = false;
				break;
			case 5:
				type = "shield";
				obstacle = false;
				break;
			case 6:
				type = "bag";
				obstacle = false;
				break;
		}
		GameObject loot = new GameObject(type,obstacle);
		loot.setPosition(object.getPosition());
		objects.add(loot);
		if(Game.lieu == "game") {
			boolean finishMap = true;
			for(GameObject element:objects) {
				if(element.getType().equals("enemy")) {
					finishMap = false;
				}
			}
			if(finishMap) {
				numeroMap++;
				if(numeroMap == 6) {
					numeroMap = 1;
				}
				GameObject door = new GameObject("door", true);
				door.setPosition(new int[] {Map.imageWidth*11, Map.imageHeight*10});
				objects.add(door);
			}
		}
		window.update();
	}
	
	public void open() {
		if(removeElement != null) {
			if(removeElement.getType().equals("chest") && !removeElement.activate) {
				removeElement.activate = true;
				animationChest = true;
				removeElement.setCounter(4);
				new BougerAuto(removeElement, player, window);
				removeElement = null;
			}
		}
	}
	
	public void choiceInvent(int choice) {
		//Choix de l'objet dans l'inventaire
		if(choice>Player.sizeInvent) {
			Player.selectInvent = Player.sizeInvent;
		}
		else {
			Player.selectInvent = choice;
			window.update();
		}
		window.update();
	}
	
	//Utilisation des objets par le player
	public void use() {
		if(Player.selectInvent<=Player.inventaire.size()) {
			switch(Player.inventaire.get(Player.selectInvent-1).getType()) {
			case "potionMana":
				Player.mana = 5;
				Player.inventaire.remove(Player.selectInvent-1);
				break;
			case "potionLife":
				Player.life = 5;
				Player.inventaire.remove(Player.selectInvent-1);
				break;
			case "carapils":
				Player.life++;
				if(Player.life>5) {
					Player.life = 5;
				}
				Player.inventaire.remove(Player.selectInvent-1);
				break;
			case "jupiler":
				Player.life+=2;
				if(Player.life>5) {
					Player.life = 5;
				}
				Player.inventaire.remove(Player.selectInvent-1);
				break;
			case "penne":
				Player.level++;
				if(Player.life>5) {
					Player.life = 5;
				}
				Player.inventaire.remove(Player.selectInvent-1);
				break;
			case "shield":
				Player.shieldActivate = true;
				Player.shieldtime = 3000;
				new BougerAuto(null, player, window);
				Player.inventaire.remove(Player.selectInvent-1);
				break;
			case "calculette":
				Player.shieldActivate = true;
				Player.shieldtime = 5000;

				new BougerAuto(null, player, window);
				Player.inventaire.remove(Player.selectInvent-1);
				break;
			case "lunettes":
				Player.shieldActivate = true;
				Player.shieldtime = 10000;

				new BougerAuto(null, player, window);
				Player.inventaire.remove(Player.selectInvent-1);
				break;
			case "book":
				Player.mana+=2;
				if(Player.mana>5) {
					Player.mana = 5;
				}
				Player.inventaire.remove(Player.selectInvent-1);
				break;
			case "bag":
				if(Player.sizeInvent < 10) {
					Player.sizeInvent++;
				}
				Player.inventaire.remove(Player.selectInvent-1);
				break;
			}
			window.update();
		}
	}
	
	//Jette les objets dans l'inventaire
	public void garbage() {
		if(Player.selectInvent<=Player.inventaire.size()) {
			Player.inventaire.remove(Player.selectInvent-1);
			window.update();
		}
	}
	
	//Selectionne des objest comme le shop le teacher (parler  au teacher)
	public void select() {
		if(shopBar.activate || shopLibrary.activate) {
			int prix = 0;

			switch(Game.lieu) {
			case"pub":
				switch(shopBar.products[shopBar.getSelection()]) {
				case "carapils":
					prix = 5;
					break;
				case "jupiler":
					prix = 10;
					break;
				case "potionLife":
					prix = 15;
					break;
				case "penne":
					prix = 20;
					break;
				}
				if(!(Player.inventaire.size()<Player.sizeInvent)) {
					Game.shopBar.inventoryfull = true;
				}
				if(Player.money>=prix && !Game.shopBar.inventoryfull) {
					shopBar.notEnoughMoney = false;
					Player.money -= prix;
					GameObject achat = new GameObject(shopBar.products[shopBar.getSelection()], false);
					Player.inventaire.add(achat);
				}
				if(Player.money<prix) {
					shopBar.notEnoughMoney = true;
				}
				window.update();
				break;
			case "library":
				switch(shopLibrary.products[shopLibrary.getSelection()]) {
				case "book":
					prix = 4;
					break;
				case "potionMana":
					prix = 8;
					break;
				case "equerre":
					prix = 10;
					break;
				case "crayon":
					prix = 20;
					break;
				case "calculette":
					prix = 30;
					break;
				case "lunettes":
					prix = 40;
					break;
				}
				if(!(Player.inventaire.size()<Player.sizeInvent)) {
					Game.shopLibrary.inventoryfull = true;
				}
				if(Player.money>=prix && !Game.shopLibrary.inventoryfull) {
					shopLibrary.notEnoughMoney = false;
					Player.money -= prix;
					GameObject achat = new GameObject(shopLibrary.products[shopLibrary.getSelection()], false);
					Player.inventaire.add(achat);
				}
				if(Player.money<prix) {
					shopLibrary.notEnoughMoney = true;
				}
				window.update();
				break;
			}
		}
		else if(removeElement != null) {
			switch(removeElement.getType()){
				case "teacher":
					GameObject stair = new GameObject("stair", false);
					stair.setPosition(new int[]{Map.imageWidth*13, Map.imageHeight*5});
					Game.objects.add(stair);
					break;
				case "shop":
					switch(Game.lieu) {
						case "pub":
							shopBar.setActivate(true);
							break;
						case "library":
							shopLibrary.setActivate(true);
							break;
					}
					break;
			}
			window.update();

		}
	}
	
	public void backClass() {
		Game.lieu = "class";
		newMap("classTest");
	}
	
	public void fire() {
		if(Player.mana>0 && Shoot.rechargeShoot) {
			Shoot.rechargeShoot = false;
			ballFire = new Shoot(player, horizontal, vertical, window); 
			objects.add(ballFire);
			Player.mana--;
		}
	}
}
