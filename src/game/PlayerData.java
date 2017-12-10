package game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import game.residents.buildings.Academy;
import game.residents.buildings.Armory;
import main.World;

public class PlayerData {
	
	public static ArrayList<Color> colors;
	
	public static PlayerData[] players = null;
	public static int currentIndex = -1;
	
	public static PlayerData me;
	public static PlayerData noPlayer = new PlayerData("", new Color(0, 0, 0, 0));

	public String name;
	public Color color;
	
	public int lightHealthIncrease;
	public int heavyHealthIncrease;
	public int lightDamageIncrease;
	public int heavyDamageIncrease;
	public int magicDamageIncrease;
	public int magicHealthIncrease;
	
	public PlayerData(String name) {
		this.name = name;
		Random rand = new Random();
		int index = rand.nextInt(colors.size());
		this.color = colors.get(index);
		colors.remove(index);
	}
	
	public PlayerData(String name, Color color) {
		this.name = name;
		this.color = color;
		if (colors != null) {
			for (int i = 0; i < colors.size(); i++) {
				Color setColor = colors.get(i);
				if (setColor.getRGB() == color.getRGB()) {
					colors.remove(i);
					return;
				}
			}
		}
	}
	
	public void setBuffs() {
		this.lightHealthIncrease = 0;
		this.lightDamageIncrease = 0;
		this.heavyHealthIncrease = 0;
		this.heavyDamageIncrease = 0;
		this.magicDamageIncrease = 0;
		this.magicHealthIncrease = 0;
		
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile currentTile = World.board.tiles[i][j];
				if (currentTile.resident().playerData() == PlayerData.me) {
					if (currentTile.resident() instanceof Armory) {
						Armory armory = (Armory)currentTile.resident();
						this.lightHealthIncrease += armory.lightHealthBuff;
						this.lightDamageIncrease += armory.lightDamageBuff;
						this.heavyHealthIncrease += armory.heavyHealthBuff;
						this.heavyDamageIncrease += armory.lightHealthBuff;
					} else if (currentTile.resident() instanceof Academy) {
						Academy acedemy = (Academy)currentTile.resident();
						this.magicDamageIncrease += acedemy.magicDamageBuff;
						this.magicHealthIncrease += acedemy.magicHealthBuff;
					}
				}
			}
		}
	}
	
}
