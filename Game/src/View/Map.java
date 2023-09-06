package View;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import Model.*;

import java.util.ArrayList;

//Gère l'affichage des map
public class Map extends JPanel {
	public static int counter = 1;
	public static boolean finishTask = false;
	//Image Declaration
	public static int nbreCell = 25;
	public static int nbreCellW = 23, nbreCellH = 25;
	public static int imageWidth, imageHeight;
    private BufferedImage inventoryFull, book, lunettes, equerre, crayon, calculette, soldeInsuffisant, carapils, jupiler, penne, shopBar, shopLibrary, dialogue, xpJauge, pub, library, ground, money, stair, teacherImage, classe, school, bus, basEcran, invent, selectInvent, block, chambre, ville, shieldImage, shieldActivateImage, bagImage, enemyImageBis, playerImage, playerImageS, playerImageN, playerImageW, playerImageE, enemyImage, lifeBarImage, manaBarImage, lifeImage, manaImage, chestImage, doorImage, fireBallImage, swordImage, potionMana, potionLife;
	private ArrayList<BufferedImage> enemyImages = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> lifeBarImages = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> manaBarImages = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> xpJaugesImages = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> CoffreImages = new ArrayList<BufferedImage>(), playerFrames = new ArrayList<BufferedImage>(), enemyFrames = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> shopBarImages = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> shopLibraryImages = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> fireBallImages = new ArrayList<BufferedImage>();
	private Font f;
	private Color yellowMoney = new Color(253,217,74);
	private Color orangeXp = new Color(253,124,24);
	private int imageWidthBis, imageHeightBis;
	
	public Map() {
		//Path
		String path = "/Users/Alban/Alban/Ecole/BA2/Info/ressources/images/";
		
		this.setBackground(Color.black);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setLayout(null);
        imageWidthBis = Window.width/25;imageHeightBis = Window.height/25;
        if(Window.width<Window.height) {
            f = new Font("Arial", Font.PLAIN, imageWidthBis/2);
        }
        else {
            f = new Font("Arial", Font.PLAIN, imageHeightBis/2);
        }
		try {
			//Chargement des images
			inventoryFull = ImageIO.read(new File(path + "inventoryFull.png"));
			book = ImageIO.read(new File(path + "bookBlack.png"));
			crayon = ImageIO.read(new File(path + "crayon.png"));
			lunettes = ImageIO.read(new File(path + "lunettes.png"));
			calculette = ImageIO.read(new File(path + "calculette.png"));
			equerre = ImageIO.read(new File(path + "equerre.png"));
			soldeInsuffisant = ImageIO.read(new File(path + "soldeInsuffisant.png"));
			carapils = ImageIO.read(new File(path + "carapils.png"));
			jupiler = ImageIO.read(new File(path + "jupiler.png"));
			penne = ImageIO.read(new File(path + "penne.png"));
			pub = ImageIO.read(new File(path + "pub.png"));
			library = ImageIO.read(new File(path + "library.png"));
			dialogue = ImageIO.read(new File(path + "dialog1.png"));
			stair = ImageIO.read(new File(path + "stair.png"));
			teacherImage = ImageIO.read(new File(path + "Teacher.png"));
			classe = ImageIO.read(new File(path + "salleDeClasse.png"));
			school = ImageIO.read(new File(path + "school.png"));
			bus = ImageIO.read(new File(path + "busDEF.png"));
			basEcran = ImageIO.read(new File(path + "basEcranDef.png"));
			chambre = ImageIO.read(new File(path + "chambreTest.png"));
			ville = ImageIO.read(new File(path + "imageCity.png"));
			invent = ImageIO.read(new File(path + "invent.png"));
			selectInvent = ImageIO.read(new File(path + "selectInvent.png"));
			money = ImageIO.read(new File(path + "Money.png"));
			ground = ImageIO.read(new File(path + "Tiles.png"));
			block = ImageIO.read(new File(path + "IceBlock.jpg"));
			for(int i = 0;i<6;i++) {
				lifeBarImage = ImageIO.read(new File(path + "healthBar"+ i +".png"));;
				lifeBarImages.add(lifeBarImage);
				manaBarImage = ImageIO.read(new File(path + "manaBar"+ i +".png"));;
				manaBarImages.add(manaBarImage);
			}
			for(int i = 1;i<4;i++) {
				chestImage = ImageIO.read(new File(path + "Coffre"+i+".png"));
				CoffreImages.add(chestImage);
			}
			for(int i = 1;i<21;i++) {
				playerImage = ImageIO.read(new File(path + "Assistant"+i+".png"));
				playerFrames.add(playerImage);
				enemyImageBis = ImageIO.read(new File(path + "Player"+i+".png"));
				enemyFrames.add(enemyImageBis);
			}
			for(int i = 0;i<5;i++) {
				xpJauge = ImageIO.read(new File(path + "jaugeXp"+i+"Bis.png"));
				xpJaugesImages.add(xpJauge);
			}
			for(int i = 1;i<5;i++) {
				shopBar = ImageIO.read(new File(path + "ShopState"+i+".png"));
				shopBarImages.add(shopBar);
				
			}
			for(int i = 1;i<7;i++) {
				shopLibrary = ImageIO.read(new File(path + "ShopLibraryEtat"+i+".png"));
				shopLibraryImages.add(shopLibrary);
			}
			for(int i = 0;i<5;i++) {
				fireBallImage = ImageIO.read(new File(path + "ball"+i+".png"));
				fireBallImages.add(fireBallImage);
			}
			
			lifeImage = ImageIO.read(new File(path + "Life.png"));
			manaImage = ImageIO.read(new File(path + "/PaperSheet.png"));
			doorImage = ImageIO.read(new File(path + "Door.png"));
			swordImage = ImageIO.read(new File(path + "Pen.png"));
			potionMana = ImageIO.read(new File(path + "Book.png"));
			potionLife = ImageIO.read(new File(path + "Beer.png"));
			shieldImage = ImageIO.read(new File(path + "Shield.png"));
			shieldActivateImage = ImageIO.read(new File(path + "ShieldActivate.png"));
			bagImage = ImageIO.read(new File(path + "Bag.png"));
			
		}
		catch(IOException e1){
			e1.printStackTrace();
		}
    }
	
	public synchronized void paint(Graphics g) {
		//Affichage
		g.setFont(f);
		switch(Game.lieu) {
		case "room":
			imageWidth = Window.width/nbreCellW;imageHeight = Window.height/nbreCellH;
			g.drawImage(chambre, imageWidth*5,  0, imageWidth*13, imageHeight*12, this);
			g.drawImage(dialogue, imageWidthBis,  imageHeightBis*15, imageWidthBis*24, imageHeightBis*4, this);
			break;
		case "city":
			imageWidth = Window.width/nbreCellW;imageHeight = Window.height/nbreCellH;
			g.drawImage(ville, 0,  0, imageWidth*43, imageHeight*27, this);
			break;
		case "school":
			imageWidth = Window.width/nbreCellW;imageHeight = Window.height/nbreCellH;
			g.drawImage(school, 0,  0, imageWidth*32, imageHeight*21, this);
			break;
		case "class":
			imageWidth = Window.width/nbreCellW;imageHeight = Window.height/nbreCellH;
			g.drawImage(classe, 0,  0, imageWidth*17, imageHeight*15, this);
			break;
		case "library":
			imageWidth = Window.width/nbreCellW;imageHeight = Window.height/nbreCellH;
			g.drawImage(library, 0,  0, imageWidth*26, imageHeight*28, this);
			break;
		case "pub":
			imageWidth = Window.width/nbreCellW;imageHeight = Window.height/nbreCellH;
			g.drawImage(pub, 0,  0, imageWidth*260/19, imageHeight*11, this);
			break;
		}
		g.drawImage(basEcran, imageWidthBis, imageHeightBis*20, imageWidthBis*23,  imageHeightBis*4, this);

		synchronized(Game.objects) {
			for(GameObject object:Game.objects) {
				switch(object.getType()) {
					case "ground":
						g.drawImage(ground, object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						break;
					case "blockUnbreak":
						g.drawImage(block, object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						break;
					case "enemy":
						Enemy ennemie = (Enemy) object;
						if(!object.activate) {
							switch(ennemie.getDirection()) {
							case "N":
								if(ennemie.hurtBool) {
									g.drawImage(colorImage(deepCopy(enemyFrames.get(1))), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}
								else {
									g.drawImage(enemyFrames.get(1), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}								
								break;
							case "S":
								if(ennemie.hurtBool) {
									g.drawImage(colorImage(deepCopy(enemyFrames.get(11))), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}
								else {
									g.drawImage(enemyFrames.get(11), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}
								break;
							case "E":
								if(ennemie.hurtBool) {
									g.drawImage(colorImage(deepCopy(enemyFrames.get(6))), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}
								else {
									g.drawImage(enemyFrames.get(6), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}
								break;
							case "W":
								if(ennemie.hurtBool) {
									g.drawImage(colorImage(deepCopy(enemyFrames.get(16))), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}
								else {
									g.drawImage(enemyFrames.get(16), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}
								break;
							}
						}
						else {
							if(ennemie.hurtBool) {
								g.drawImage(colorImage(deepCopy(enemyFrames.get(object.getCounter()-1))), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
							}
							else {
								g.drawImage(enemyFrames.get(object.getCounter()-1), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
							}
						}
						
						break;
					case "life":
						g.drawImage(lifeImage, object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						break;
					case "mana":
						g.drawImage(manaImage, object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						break;
					case "sword":
						g.drawImage(swordImage, object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						break;
					case "potionMana":
						g.drawImage(potionMana, object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						break;
					case "potionLife":
						g.drawImage(potionLife, object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						break;
					case "shield":
						g.drawImage(shieldImage, object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						break;
					case "bag":
						g.drawImage(bagImage, object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						break;
					case "teacher":
						g.drawImage(teacherImage, object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						break;
					case "chest":
						if(object.activate) {
							g.drawImage(CoffreImages.get(object.getCounter()-1), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						}
						else {
							g.drawImage(CoffreImages.get(0), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						}
						break;
					case "door":
						if(Game.lieu == "game") {
							g.drawImage(doorImage, object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						}
						break;	
					case "player":
						if(!object.activate) {
							switch(Player.direction) {
							case "N":
								if(Player.hurtBool) {
									g.drawImage(colorImage(deepCopy(playerFrames.get(1))), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}
								else {
									g.drawImage(playerFrames.get(1), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}	
								break;
							case "S":
								if(Player.hurtBool) {
									g.drawImage(colorImage(deepCopy(playerFrames.get(11))), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}
								else {
									g.drawImage(playerFrames.get(11), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}								
								break;
							case "E":
								if(Player.hurtBool) {
									g.drawImage(colorImage(deepCopy(playerFrames.get(6))), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}
								else {
									g.drawImage(playerFrames.get(6), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}								
								break;
							case "W":
								if(Player.hurtBool) {
									g.drawImage(colorImage(deepCopy(playerFrames.get(16))), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}
								else {
									g.drawImage(playerFrames.get(16), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
								}								
								break;
							}
						}
						else {
							if(Player.hurtBool) {
								g.drawImage(colorImage(deepCopy(playerFrames.get(object.getCounter()-1))), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
							}
							else {
								g.drawImage(playerFrames.get(object.getCounter()-1), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
							}						
						}
						if(Player.shieldActivate) {
							g.drawImage(shieldActivateImage, object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						}
						break;
					case "shoot":
						if(Player.level>=5) {
							g.drawImage(fireBallImages.get(4), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						}
						else {
							g.drawImage(fireBallImages.get(Player.level), object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);

						}
						break;
					case "stair":
						g.drawImage(stair, object.getPosition()[0],  object.getPosition()[1], imageWidth, imageHeight, this);
						break;
					default:
						break;
					
				}
			}
		}
		
		
		//Create inventaire
		for(int j = 0; j<Player.sizeInvent;j++) {
			if(j == Player.selectInvent-1 && !Player.inventaire.isEmpty()) {
				g.drawImage(selectInvent, imageWidthBis*(j+13),  imageHeightBis*21, imageWidthBis, imageHeightBis, this);
			}
			else{
				g.drawImage(invent, imageWidthBis*(j+13),  imageHeightBis*21, imageWidthBis, imageHeightBis, this);
			}
		}
		int nbreInventaire = 0;
		for(GameObject object:Player.inventaire) {
			switch(object.getType()) {
			case "sword":
				object.setPosition(new int[] {imageWidthBis*(nbreInventaire+13), imageHeightBis*21});
				g.drawImage(swordImage, object.getPosition()[0],  object.getPosition()[1], imageWidthBis, imageHeightBis, this);
				break;
			case "potionMana":
				object.setPosition(new int[] {imageWidthBis*(nbreInventaire+13), imageHeightBis*21});
				g.drawImage(potionMana, object.getPosition()[0],  object.getPosition()[1], imageWidthBis, imageHeightBis, this);
				break;
			case "potionLife":
				object.setPosition(new int[] {imageWidthBis*(nbreInventaire+13), imageHeightBis*21});
				g.drawImage(potionLife, object.getPosition()[0],  object.getPosition()[1], imageWidthBis, imageHeightBis, this);
				break;
			case "shield":
				object.setPosition(new int[] {imageWidthBis*(nbreInventaire+13), imageHeightBis*21});
				g.drawImage(shieldImage, object.getPosition()[0],  object.getPosition()[1], imageWidthBis, imageHeightBis, this);
				break;
			case "bag":
				object.setPosition(new int[] {imageWidthBis*(nbreInventaire+13), imageHeightBis*21});
				g.drawImage(bagImage, object.getPosition()[0],  object.getPosition()[1], imageWidthBis, imageHeightBis, this);
				break;
			case "carapils":
				object.setPosition(new int[] {imageWidthBis*(nbreInventaire+13), imageHeightBis*21});
				g.drawImage(carapils, object.getPosition()[0],  object.getPosition()[1], imageWidthBis, imageHeightBis, this);
				break;
			case "jupiler":
				object.setPosition(new int[] {imageWidthBis*(nbreInventaire+13), imageHeightBis*21});
				g.drawImage(jupiler, object.getPosition()[0],  object.getPosition()[1], imageWidthBis, imageHeightBis, this);
				break;
			case "penne":
				object.setPosition(new int[] {imageWidthBis*(nbreInventaire+13), imageHeightBis*21});
				g.drawImage(penne, object.getPosition()[0],  object.getPosition()[1], imageWidthBis, imageHeightBis	, this);
				break;
			case "equerre":
				object.setPosition(new int[] {imageWidthBis*(nbreInventaire+13), imageHeightBis*21});
				g.drawImage(equerre, object.getPosition()[0],  object.getPosition()[1], imageWidthBis, imageHeightBis, this);
				break;
			case "crayon":
				object.setPosition(new int[] {imageWidthBis*(nbreInventaire+13), imageHeightBis*21});
				g.drawImage(crayon, object.getPosition()[0],  object.getPosition()[1], imageWidthBis, imageHeightBis, this);
				break;
			case "calculette":
				object.setPosition(new int[] {imageWidthBis*(nbreInventaire+13), imageHeightBis*21});
				g.drawImage(calculette, object.getPosition()[0],  object.getPosition()[1], imageWidthBis, imageHeightBis, this);
				break;
			case "lunettes":
				object.setPosition(new int[] {imageWidthBis*(nbreInventaire+13), imageHeightBis*21});
				g.drawImage(lunettes, object.getPosition()[0],  object.getPosition()[1], imageWidthBis, imageHeightBis, this);
				break;
			case "book":
				object.setPosition(new int[] {imageWidthBis*(nbreInventaire+13), imageHeightBis*21});
				g.drawImage(book, object.getPosition()[0],  object.getPosition()[1], imageWidthBis, imageHeightBis, this);
				break;
			}
			nbreInventaire++;
		}
			
		//LifeBar Affichage
		g.drawImage(lifeBarImages.get(Player.life),   imageWidthBis*3, imageHeightBis*21, imageWidthBis*5, imageHeightBis, this);
		
		//ManaBar Affichage
		g.drawImage(manaBarImages.get(Player.mana), imageWidthBis*3,  imageHeightBis*22, imageWidthBis*5, imageHeightBis, this);
		
		//Money
		g.setColor(yellowMoney);

		g.drawString(String.valueOf(Player.money), imageWidthBis*11, imageHeightBis*22);
		g.drawImage(money,  imageWidthBis*11+g.getFontMetrics().stringWidth(String.valueOf(Player.money)), imageHeightBis*21+imageHeightBis/4, imageWidthBis, imageHeightBis, this);
		
		//Xp
		g.setColor(orangeXp);

		g.drawString(String.valueOf(Player.level), imageWidthBis*9, imageHeightBis*22);
		g.drawImage(xpJaugesImages.get(Player.xp),  imageWidthBis*8+g.getFontMetrics().stringWidth(String.valueOf(Player.level))/2, imageHeightBis*21-g.getFontMetrics().stringWidth(String.valueOf(Player.level))/2, imageWidthBis*2, imageHeightBis*2, this);
		
		switch(Game.lieu) {
		case "city":
			g.drawImage(bus, imageWidth*21,  imageHeight*17, imageWidth*5, imageHeight*3, this);
			break;
		case "school":
			g.drawImage(bus, imageWidth,  imageHeight*4, imageWidth*5, imageHeight*3, this);
			break;
		case "pub":
			if(Game.shopBar.activate) {
				g.drawImage(shopBarImages.get(Game.shopBar.getSelection()), imageWidth*4,  imageHeight*4, imageWidth*5, imageHeight*6, this);
				if(Game.shopBar.notEnoughMoney) {
					g.drawImage(soldeInsuffisant, imageWidth*4,  imageHeight*4, imageWidth*5, imageHeight*6, this);
				}
				else if(Game.shopBar.inventoryfull) {
					g.drawImage(inventoryFull, imageWidth*4,  imageHeight*4, imageWidth*5, imageHeight*6, this);
				}
			}
				break;
		case "library":
			if(Game.shopLibrary.activate) {
				g.drawImage(shopLibraryImages.get(Game.shopLibrary.getSelection()), imageWidth*29/4,  imageHeight*11/2, imageWidth*12, imageHeight*15, this);
				if(Game.shopLibrary.notEnoughMoney) {
					g.drawImage(soldeInsuffisant, imageWidth*29/4,  imageHeight*11/2, imageWidth*12, imageHeight*15, this);
				}
				else if(Game.shopLibrary.inventoryfull) {
					g.drawImage(inventoryFull, imageWidth*29/4,  imageHeight*11/2, imageWidth*12, imageHeight*15, this);
				}
			}
				break;
		}
			
	}
	
	//Permet de dupliquer une image
	private static BufferedImage deepCopy(BufferedImage bi) {
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
	
	//Permet de rendre une image rouge pour l'animation quand on est blessé
	private static BufferedImage colorImage(BufferedImage image) {
	    int width = image.getWidth();
	    int height = image.getHeight();
	    WritableRaster raster = image.getRaster();

	    for (int xx = 0; xx < width; xx++) {
	      for (int yy = 0; yy < height; yy++) {
	        int[] pixels = raster.getPixel(xx, yy, (int[]) null);
	        pixels[0] = 255;
	        pixels[1] = 0;
	        pixels[2] = 0;
	        raster.setPixel(xx, yy, pixels);
	      }
	    }
	    return image;
	  }
	
	public void redraw() {
		this.repaint();
	}
}
