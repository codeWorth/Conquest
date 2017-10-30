package game.residents;

import java.awt.Color;

import javax.swing.JPanel;

import game.Player;
import game.residents.interfaces.BuildMenu;

public class EmptyResident extends TileResident {
	
	@Override
	public boolean canBuildOn() {
		return false;
	}
	
	@Override
	public Color minimapColor() {
		return new Color(114, 204, 129);
	}

	@Override
	public Color mapColor() {
		return new Color(114, 204, 129);
	}

	@Override
	public int health() {
		return 0;
	}

	@Override
	public int shootRange() {
		return 0;
	}

	@Override
	public JPanel userInterface() {
		return null;
	}

	@Override
	public void takeDamage(int damage) {
	}

	@Override
	public Player player() {
		return null;
	}
	
	@Override
	public int damage() {
		return 0;
	}

	@Override
	public JPanel statsPanel() {
		return new BuildMenu(Player.currentPlayer.makeOptions());
	}

	@Override
	public String name() {
		return "Something went wrong!!";
	}

	@Override
	public int cost() {
		return 0;
	}

	@Override
	public int maxUnitsBuff() {
		return 0;
	}
	
	@Override
	public boolean canMoveThrough() {
		return true;
	}

	@Override
	public String toString() {
		return "EmptyResident:";
	}
}
