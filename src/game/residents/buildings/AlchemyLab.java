package game.residents.buildings;

import java.awt.Color;
import java.awt.Image;
import java.net.URL;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;

import game.Board;
import game.PlayerData;
import game.residents.TileResident;
import game.residents.Upgrade;
import game.residents.Upgradeable;
import game.residents.interfaces.buildings.AlchemyLabDetails;

public class AlchemyLab extends TileResident implements Upgradeable {

	public int areaOfEffectBuff;
	
	private Upgrade[] upgrades;
	
	public AlchemyLab(PlayerData playerData) {
		this.playerData = playerData;
		this.health = 7;
		this.maxHealth = this.health;
		
		Upgrade upgrade1a = new Upgrade("AoE +1", 9, new Runnable() {
			@Override
			public void run() {
				areaOfEffectBuff = 1;
			}
		});
		upgrade1a.color = new Color(242, 133, 106);
		Upgrade upgrade1b = new Upgrade("AoE +2", 15, new Runnable() {
			@Override
			public void run() {
				areaOfEffectBuff = 2;
			}
		});
		upgrade1a.next = upgrade1b;
		Upgrade upgrade1c = new Upgrade("AoE +3", 25, new Runnable() {
			@Override
			public void run() {
				areaOfEffectBuff = 3;
			}
		});
		upgrade1b.next = upgrade1c;
		
		upgrades = new Upgrade[]{upgrade1a};
		
		URL file = getClass().getClassLoader().getResource("alchemy.bmp");
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
	public JComponent statsPanel(boolean info) {
		return new AlchemyLabDetails(this, upgrades, info);
	}

	@Override
	public String name() {
		return "Laboratory";
	}

	@Override
	public int cost() {
		return 11;
	}

	@Override
	public String toString() {
		return "Laboratory:"+Integer.toString(this.health);
	}
	
	@Override
	public void upgrade(int slot) {
		upgrades[slot] = upgrades[slot].run(this.playerData());
	}
	
}
