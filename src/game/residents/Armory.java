package game.residents;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.Board;
import game.PlayerData;
import game.residents.interfaces.ArmoryDetails;

/**
 * @author cHriS The BIIiGgeSt man EVeR
 *
 */

public class Armory extends TileResident {
	
	public Armory(PlayerData playerData) {
		this.playerData = playerData;
		this.health = 5;
		
		File file = new File("src/graphics/Icons/armory.bmp");
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
	public JPanel userInterface() {
		return null;
	}

	@Override
	public JPanel statsPanel() {
		return new ArmoryDetails(this);
	}

	@Override
	public String name() {
		return "Armory";
	}

	@Override
	public int cost() {
		return 6;
	}

	@Override
	public String toString() {
		return "Armory:"+Integer.toString(this.health);
	}

}
