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
import game.residents.interfaces.buildings.HospiceDetails;

public class Hospice extends TileResident implements Upgradeable {

	public int healingBuff;
	
	private Upgrade[] upgrades;
	
	public Hospice(PlayerData playerData) {
		this.playerData = playerData;
		this.health = 5;
		this.maxHealth = this.health;
		
		Upgrade upgrade1a = new Upgrade("Healing +1", 11, new Runnable() {
			@Override
			public void run() {
				healingBuff = 1;
			}
		});
		upgrade1a.color = new Color(243, 244, 195);
		Upgrade upgrade1b = new Upgrade("Healing +2", 16, new Runnable() {
			@Override
			public void run() {
				healingBuff = 2;
			}
		});
		upgrade1a.next = upgrade1b;
		Upgrade upgrade1c = new Upgrade("Healing +3", 30, new Runnable() {
			@Override
			public void run() {
				healingBuff = 3;
			}
		});
		upgrade1b.next = upgrade1c;
		
		upgrades = new Upgrade[]{upgrade1a};
		
		URL file = getClass().getClassLoader().getResource("hospice.bmp");
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
		return new HospiceDetails(this, upgrades, info);
	}

	@Override
	public String name() {
		return "Infirmary";
	}

	@Override
	public int cost() {
		return 15;
	}

	@Override
	public String toString() {
		return "Infirmary:"+Integer.toString(this.health);
	}	
	
	@Override
	public void upgrade(int slot) {
		upgrades[slot] = upgrades[slot].run(this.playerData());
	}
	
}
