package Model;

import View.Map;

//Définition des objets
public class GameObject implements Positionable {
	public int[] position;
	public String type; 
	public boolean obstacle = false;
	public boolean activate = false;
	private int counter = 1;
	private boolean toucher = false;
	
	public GameObject(String type, boolean obstacle) {
		this.type = type;
		this.obstacle = obstacle;
	}
	
	@Override
	public void setPosition(int[] positionGive) {
		position = positionGive;
	}
	
	@Override
	public int[] getPosition() {
		return position;
	}
	
	public String getType() {
		return type;
	}
	
	public void setActivate(boolean value) {
		activate = value;
	}
	
	 public boolean isAtPosition(int x, int y) {
		 toucher = false;
		 if(x == position[0]) {
				if(y > position[1]){
					if(y-Map.imageHeight+1 <= position[1]) {
						toucher = true;
					}
				}
				else {
					if(y+Map.imageHeight-1 >= position[1]) {
						toucher = true;
					}
				}
			}
			else if(y == position[1]) {
				if(x > position[0]){
					if(x-Map.imageWidth+1 <= position[0]) {
						toucher = true;
					}
				}
				else {
					if(x+Map.imageWidth-1 >= position[0]) {
						toucher = true;
					}
				}
			}
		 return toucher;
	 }
	 
	 //Regarde si sur la position devant l'objet GameObjet se trouve un autre objet
	 public boolean isAtPositionInFrontOf(int x, int y) {
		 toucher = false;
		 if(x == position[0]) {
				if(y > position[1]){
					if(y-Map.imageHeight <= position[1]) {
						toucher = true;
					}
				}
				else {
					if(y+Map.imageHeight >= position[1]) {
						toucher = true;
					}
				}
			}
			else if(y == position[1]) {
				if(x > position[0]){
					if(x-Map.imageWidth <= position[0]) {
						toucher = true;
					}
				}
				else {
					if(x+Map.imageWidth >= position[0]) {
						toucher = true;
					}
				}
			}
		 return toucher;

	 }
	 
	 public boolean isAtPositionShoot(int x, int y) {
	        return this.position[0] == x && this.position[1] == y;
	 }
	 
	 public void setCounter(int counter) {
		 this.counter = counter;
	 }
	 
	 public int getCounter() {
		 return counter;
	 }
}
