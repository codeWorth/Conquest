package game.residents;

import java.awt.Color;

import javax.swing.JPanel;

import game.PlayerData;
import game.residents.interfaces.FarmDetails;

public class Farm extends TileResident {
	
	public Farm(PlayerData playerData) {
		this.playerData = playerData;
		this.health = 5;	
	}

	@Override
	public boolean canBuildOn() {
		return true;
	}

	@Override
	public Color mapColor() {
		return new Color(238, 229, 201);
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
	public int damage() {
		return 0;
	}

	@Override
	public JPanel statsPanel() {
		return new FarmDetails(this);
	}

	@Override
	public String name() {
		return "Farm";
	}

	@Override
	public int cost() {
		return 5;
	}

	@Override
	public int maxUnitsBuff() {
		return 2;
	}

	@Override
	public String toString() {
		return "Farm:"+Integer.toString(this.health);
	}

}
