package game.residents;

import java.awt.Color;

import javax.swing.JPanel;

import game.PlayerData;
import game.residents.interfaces.UnitDetails;
import game.residents.interfaces.UnitInterface;

public class Spearman extends TileResident {

	private int actionsLeft;
	private boolean canAttack = true;
	
	public Spearman(PlayerData playerData) {
		this.playerData = playerData;
		this.health = 2;
	}
	
	@Override
	public int moveRange() {
		return 2;
	}

	@Override
	public boolean canBuildOn() {
		return false;
	}

	@Override
	public Color mapColor() {
		return new Color(124, 21, 21);
	}

	@Override
	public int shootRange() {
		return 1;
	}

	@Override
	public JPanel userInterface() {
		return new UnitInterface(canAttack);
	}
	
	@Override
	public void startOfTurnEffect() {
		this.canAttack = true;
	}

	@Override
	public void attack(TileResident other) {
		other.takeDamage(this.damage());
		
		if (this.shootRange() == 1 && other.shootRange() == 1) {
			this.takeDamage(other.damage());
		}
		this.canAttack = false;
	}
	
	@Override
	public int damage() {
		return 2;
	}

	@Override
	public JPanel statsPanel() {
		return new UnitDetails(this);
	}

	@Override
	public String name() {
		return "Spearman";
	}

	@Override
	public int cost() {
		return 4;
	}

	public int actionsLeft() {
		return this.actionsLeft;
	}
	public void setActionsLeft(int actions) {
		this.actionsLeft = actions;
	}
	public void reduceActionsLeft(int amount) {
		this.actionsLeft -= amount;
		if (this.actionsLeft < 0) {
			this.actionsLeft = 0;
		}
	}
	
	@Override
	public String[] prereqs() {
		return new String[]{"Armory"};
	}
	

	@Override
	public String toString() {
		return "Base:"+Integer.toString(this.health)+","+Integer.toString(this.actionsLeft)+","+Boolean.toString(canAttack);
	}
}
