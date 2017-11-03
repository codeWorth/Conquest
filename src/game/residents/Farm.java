package game.residents;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.Board;
import game.PlayerData;
import game.residents.interfaces.FarmDetails;

public class Farm extends TileResident {
	
	public Farm(PlayerData playerData) {
		this.playerData = playerData;
		this.health = 5;	
		
		File file = new File("src/graphics/Icons/farm.bmp");
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
		return 3;
	}

	@Override
	public String toString() {
		return "Farm:"+Integer.toString(this.health);
	}
	
	@Override
	public int healthIncrease() {
		return 0;
	}

	@Override
	public int damageIncrease() {
		return 0;
	}

}
