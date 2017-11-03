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
	protected boolean dead = false;

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
		if (this.dead) {
			return 0;
		} else if (this.health + this.healthIncrease() <= 0) {
			this.health = 1 - this.healthIncrease();
		}
		return this.health + this.healthIncrease();
	}
	
	public int shootRange() {
		return 0;
	}
	
	public abstract JPanel userInterface();
	
	public abstract int healthIncrease();
	public abstract int damageIncrease();

	public void takeDamage(int damage) {
		this.health -= damage;
		
		if (this.health() <= 0) {
			this.dead = true;
		}
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
