package game.residents;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import game.Board;
import game.Player;
import game.PlayerData;
import game.residents.interfaces.BuildMenu;

public class EmptyResident extends TileResident {
	
	public EmptyResident() {
		Random rand = new Random();
		int index = rand.nextInt(3);
		URL file = null;
		if (index == 0) {
			file = getClass().getClassLoader().getResource("empty.bmp");
		} else if (index == 1) {
			file = getClass().getClassLoader().getResource("empty1.bmp");
		} else if (index == 2) {
			file = getClass().getClassLoader().getResource("empty2.bmp");
		}
		
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
		return new Color(114, 204, 129);
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
		return new BuildMenu(Player.player.makeOptions());
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
