package game.residents;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;

import game.Player;

public abstract class TileResident {
	
	public static ArrayList<TileResident> residents = new ArrayList<>();
	
	protected Player player;
	protected int health;

	public int moveRange() {
		return 0;
	}
	
	public abstract boolean canBuildOn();
	
	public boolean canMoveThrough() {
		return false;
	}
	
	public Color minimapColor() {
		return this.player.color;
	}

	public abstract Color mapColor();
		
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
	
	public Player player() {
		return this.player;
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
		
}
