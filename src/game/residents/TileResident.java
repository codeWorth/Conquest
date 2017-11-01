package game.residents;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JPanel;

import game.PlayerData;

public abstract class TileResident {
	
	public static ArrayList<TileResident> residents = new ArrayList<>();
	
	protected PlayerData playerData;
	protected int health;
	protected Image image;

	public int moveRange() {
		return 0;
	}
	
	public abstract boolean canBuildOn();
	
	public boolean canMoveThrough() {
		return false;
	}
	
	public Color minimapColor() {
		return playerData.color;
	}

	public abstract Image mapImage();
		
	public boolean canSelect() {
		return true;
	}
	
	public int health() {
		return this.health;
	}
	
	public int shootRange() {
		return 0;
	}
	
	public abstract JPanel userInterface();

	public void takeDamage(int damage) {
		this.health -= damage;
	}
	
	public PlayerData playerData() {
		return this.playerData;
	}
	
	public void startOfTurnEffect() {
	}
	
	public int actionsLeft() {
		return 0;
	}
	public void setActionsLeft(int actions) {
	}
	public void reduceActionsLeft(int amount) {
	}
	
	public void attack(TileResident other) {
	}
	
	public int damage() {
		return 0;
	}
	
	public abstract JPanel statsPanel();
	
	public abstract String name();
		
	public abstract int cost();
	
	public int maxUnitsBuff() {
		return 0;
	}
		
	public String[] prereqs() {
		return new String[0];
	}
	
	public abstract String toString();
	
}
