package Controller;

import Model.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Keyboard implements KeyListener{
	private Game game;
    
    public Keyboard(Game game) {
        this.game = game;
    }
    
    @Override
    public void keyPressed(KeyEvent event) {    
    	//Si le mode Shop n'est pas activée  
    	if(!(game.shopBar.activate || game.shopLibrary.activate)) {
    		switch (event.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                game.movePlayer("E");
                break;
            case KeyEvent.VK_LEFT:
            	game.movePlayer("W");
                break;
            case KeyEvent.VK_DOWN:
            	game.movePlayer("S");
                break;
            case KeyEvent.VK_UP:
            	game.movePlayer("N");
                 break;
            case KeyEvent.VK_SPACE:
            	game.fire();
                 break;
            case KeyEvent.VK_T:
             	game.taper();
                 break;
            case KeyEvent.VK_O:
             	game.open();
                  break;
            case KeyEvent.VK_1:
            	 game.choiceInvent(1);
                  break;
            case KeyEvent.VK_2:
            	game.choiceInvent(2);
                break;
            case KeyEvent.VK_3:
            	game.choiceInvent(3);
                break;
            case KeyEvent.VK_4:
            	game.choiceInvent(4);
                break;
            case KeyEvent.VK_5:
            	game.choiceInvent(5);
                break;
            case KeyEvent.VK_6:
            	game.choiceInvent(6);
                break;
            case KeyEvent.VK_7:
            	game.choiceInvent(7);
                break;
            case KeyEvent.VK_8:
            	game.choiceInvent(8);
                break;
            case KeyEvent.VK_9:
            	game.choiceInvent(9);
                break;
            case KeyEvent.VK_0:
            	game.choiceInvent(10);
                break;
            case KeyEvent.VK_U:
            	game.use();
                break;
            case KeyEvent.VK_S:
            	game.select();
                break;
            case KeyEvent.VK_J:
            	game.garbage();
                break;
            case KeyEvent.VK_ESCAPE:
            	//Permet de sortir de shop ou du mode de jeu
            	game.backClass();
            	break;
            }
    	}
    	else {
    		//Lorsqu'on est dans le mode Shop
    		switch (event.getKeyCode()) {
            case KeyEvent.VK_DOWN:
            	if(Game.shopBar.activate) {
            		Game.shopBar.augmenteSelection();
            	}
            	else {
            		Game.shopLibrary.augmenteSelection();
            	}
                break;
            case KeyEvent.VK_UP:
            	if(Game.shopBar.activate) {
            		Game.shopBar.diminueSelection();
            	}
            	else {
            		Game.shopLibrary.diminueSelection();
            	}
                 break;
            case KeyEvent.VK_ESCAPE:
            	if(Game.shopBar.activate) {
            		Game.shopBar.setActivate(false);
            		Game.shopBar.notEnoughMoney = false;
            		Game.shopBar.inventoryfull = false;
            	}
            	else {
            		Game.shopLibrary.setActivate(false);
            		Game.shopLibrary.notEnoughMoney = false;
            		Game.shopLibrary.inventoryfull = false;
            	}
            	break;
            case KeyEvent.VK_1:
            	 game.choiceInvent(1);
                  break;
            case KeyEvent.VK_2:
            	game.choiceInvent(2);
                break;
            case KeyEvent.VK_3:
            	game.choiceInvent(3);
                break;
            case KeyEvent.VK_4:
            	game.choiceInvent(4);
                break;
            case KeyEvent.VK_5:
            	game.choiceInvent(5);
                break;
            case KeyEvent.VK_6:
            	game.choiceInvent(6);
                break;
            case KeyEvent.VK_7:
            	game.choiceInvent(7);
                break;
            case KeyEvent.VK_8:
            	game.choiceInvent(8);
                break;
            case KeyEvent.VK_9:
            	game.choiceInvent(9);
                break;
            case KeyEvent.VK_0:
            	game.choiceInvent(10);
                break;
            case KeyEvent.VK_U:
            	game.use();
                break;
            case KeyEvent.VK_S:
            	game.select();
                break;
            }
    	}
    	
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void keyReleased(KeyEvent event) {
    }
}
