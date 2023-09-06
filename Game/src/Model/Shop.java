package Model;

import View.Window;;

public class Shop {
	public boolean activate = false;
	public String[] products;
	public String type;
	public int selection = 0;
	public boolean notEnoughMoney = false;
	public boolean inventoryfull = false;
	public Window window;
	
	//Création du Shop
	public Shop(String[] products, boolean activate, String type, Window window) {
		this.products = products;
		this.activate = activate;
		this.type = type;
		this.window = window;
	}
	
	public void augmenteSelection() {
		if(selection+1 < products.length) {
			selection++;
		}
		window.update();
	}
	
	public void diminueSelection() {
		if(selection-1 >= 0) {
			selection--;
		}
		window.update();
	}
	
	public int getSelection() {
		return selection;
	}
	
	public String getType() {
		return type;
	}
	
	public void setActivate(boolean activate) {
		this.activate = activate;
		window.update();
	}
}
