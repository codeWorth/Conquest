package game.residents;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.Board;
import game.Player;
import game.PlayerData;
import game.residents.interfaces.MineDetails;

public class Mine extends TileResident {

	public int moneyPerTurn = 3;
	private int cost = 7;
	
	public Mine(PlayerData playerData, int cost) {
		this.playerData = playerData;
		this.cost = cost;
		this.health = 4;	
		
		File file = new File("src/graphics/Icons/goldmine.bmp");
		try {
			image = ImageIO.read(file).getScaledInstance(Board.TILE_SIZE - 2, Board.TILE_SIZE - 2, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Mine(PlayerData playerData) {
		this(playerData, 7);
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
	public void startOfTurnEffect() {
		Player.player.money += this.moneyPerTurn;
	}

	@Override
	public JPanel statsPanel() {
		return new MineDetails(this);
	}

	@Override
	public String name() {
		return "Gold Mine";
	}

	@Override
	public int cost() {
		return this.cost;
	}

	@Override
	public int maxUnitsBuff() {
		return 0;
	}
	

	@Override
	public String toString() {
		return "Mine:"+Integer.toString(this.health)+","+Integer.toString(cost);
	}

}
