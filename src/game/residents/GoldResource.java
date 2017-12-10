package game.residents;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import game.Board;
import game.Player;
import game.PlayerData;
import game.residents.buildings.Mine;
import game.residents.interfaces.BuildMenu;

public class GoldResource extends TileResident {

	public GoldResource() {
		URL file = getClass().getClassLoader().getResource("gold.bmp");
		try {
			image = ImageIO.read(file).getScaledInstance(Board.TILE_SIZE, Board.TILE_SIZE, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean canBuildOn() {
		return false;
	}
	
	@Override
	public Color minimapColor() {
		return new Color(255, 215, 0);
	}

	@Override
	public Image mapImage() {
		return image;
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
	public PlayerData playerData() {
		return PlayerData.noPlayer;
	}
	
	@Override
	public int damage() {
		return 0;
	}

	@Override
	public JComponent statsPanel(boolean info) {
		if (!Player.player.needPlaceBase) {
			Player.player.ownedMines();
			return new BuildMenu(new TileResident[]{new Mine(PlayerData.noPlayer)});
		} else {
			return null;
		}
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
		return false;
	}

	@Override
	public String toString() {
		return "GoldResource:";
	}

	
	
}
