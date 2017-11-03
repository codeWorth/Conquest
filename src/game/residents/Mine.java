package game.residents;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.Board;
import game.Player;
import game.PlayerData;
import game.residents.interfaces.MineDetails;

public class Mine extends TileResident implements Upgradeable {

	public int moneyPerTurn = 3;
	private int cost = 7;
	private Upgrade[] upgrades;
	
	public Mine(PlayerData playerData, int cost) {
		this.playerData = playerData;
		this.cost = cost;
		this.health = 4;	
		
		Upgrade upgrade1a = new Upgrade("Money +1", 10, new Runnable() {
			@Override
			public void run() {
				moneyPerTurn = 4;
			}
		});
		upgrade1a.color = new Color(242, 133, 106);
		Upgrade upgrade1b = new Upgrade("Money +2", 15, new Runnable() {
			@Override
			public void run() {
				moneyPerTurn = 5;
			}
		});
		upgrade1a.next = upgrade1b;
		Upgrade upgrade1c = new Upgrade("Money +4", 20, new Runnable() {
			@Override
			public void run() {
				moneyPerTurn = 7;
			}
		});
		upgrade1b.next = upgrade1c;
		Upgrade upgrade1d = new Upgrade("Money +6", 25, new Runnable() {
			@Override
			public void run() {
				moneyPerTurn = 9;
			}
		});
		upgrade1c.next = upgrade1d;
		
		this.upgrades = new Upgrade[]{upgrade1a};
		
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
		return new MineDetails(this, this.upgrades);
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

	@Override
	public int healthIncrease() {
		return 0;
	}

	@Override
	public int damageIncrease() {
		return 0;
	}

	@Override
	public void upgrade(int slot) {
		upgrades[slot] = upgrades[slot].run();
	}
	
}
