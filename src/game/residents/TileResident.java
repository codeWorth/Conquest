package game.residents;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;

import game.BoardTile;
import game.OT;
import game.PlayerData;
import main.World;

public abstract class TileResident {
	
	public static ArrayList<TileResident> residents = new ArrayList<>();
	
	protected PlayerData playerData;
	protected int health;
	protected int maxHealth;
	protected Image image;
	protected boolean dead = false;
	
	public ArrayList<OT> ots = new ArrayList<>();
	public BoardTile location;

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
	
	public int healthIncrease() {
		return 0;
	}

	public int damageIncrease() {
		return 0;
	}

	public void takeDamage(int damage) {
		this.health -= damage;
		
		if (this.health + this.healthIncrease() <= 0) {
			this.dead = true;
			World.board.setResident(this.location.x, this.location.y, new EmptyResident());
		}
		
		if (this.health() > this.maxHealth + this.healthIncrease()) {
			this.health = this.maxHealth;
		}
	}
	
	public PlayerData playerData() {
		return this.playerData;
	}
	
	protected void turnStart() {
	}
	public void startOfTurnEffect() {
		for (OT ot : this.ots) {
			ot.startOfTurnTick();
		}
		turnStart();
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
	
	public JComponent statsPanel() {
		return statsPanel(false);
	}
	public abstract JComponent statsPanel(boolean info);
	
	public abstract String name();
		
	public abstract int cost();
	
	public int maxUnitsBuff() {
		return 0;
	}
		
	public String[] prereqs() {
		return new String[0];
	}
	
	public abstract String toString();
	
	public int distanceTo(TileResident other) {
		int dX = Math.abs(other.location.x - this.location.x);
		int dY = Math.abs(other.location.y - this.location.y);
		return dX + dY;
	}
	
	public boolean canTarget(TileResident resident) {
		return resident.health() > 0 && resident.playerData() != PlayerData.me;
	}
	
}
