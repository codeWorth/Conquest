package game.residents;

import java.awt.Color;

import javax.swing.JPanel;

import game.Player;
import game.residents.interfaces.BaseDetails;

public class Base extends TileResident {
		
	public Base(Player player) {
		this.player = player;
		this.health = 6;
	}
	
	@Override
	public boolean canBuildOn() {
		return true;
	}

	@Override
	public Color mapColor() {
		return new Color(150, 111, 51);
	}

	@Override
	public JPanel userInterface() {
		return null;
	}

	@Override
	public void takeDamage(int damage) {
		this.health -= damage;
		
		if (this.health <= 0) {
			System.out.println("Player " + this.player.color.toString() + " lost!");
			System.exit(0);
		}
	}

	@Override
	public void startOfTurnEffect() {
		this.player.money += 1;
	}

	@Override
	public int damage() {
		return 0;
	}

	@Override
	public JPanel statsPanel() {
		return new BaseDetails(this);
	}

	@Override
	public String name() {
		return "Village";
	}

	@Override
	public int cost() {
		return 0;
	}

	@Override
	public int maxUnitsBuff() {
		return 3;
	}

	@Override
	public String toString() {
		return "Base:"+Integer.toString(this.health);
	}

}
