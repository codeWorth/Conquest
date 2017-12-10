package game.residents.buildings;

import java.awt.Image;
import java.net.URL;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import game.Board;
import game.PlayerData;
import game.residents.TileResident;
import game.residents.interfaces.buildings.FarmDetails;

public class Farm extends TileResident {
	
	public Farm(PlayerData playerData) {
		this.playerData = playerData;
		this.health = 5;	
		this.maxHealth = this.health;
		
		URL file = getClass().getClassLoader().getResource("farm.bmp");
		try {
			image = ImageIO.read(file).getScaledInstance(Board.TILE_SIZE - 2, Board.TILE_SIZE - 2, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean canBuildOn() {
		return true;
	}

	@Override
	public Image mapImage() {
		return image;
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
	public JComponent statsPanel(boolean info) {
		return new FarmDetails(this, info);
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
		return 3;
	}

	@Override
	public String toString() {
		return "Farm:"+Integer.toString(this.health);
	}
	
	

}
