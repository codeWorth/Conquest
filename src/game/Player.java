package game;

import java.awt.Color;
import java.util.ArrayList;

import game.residents.TileResident;
import game.residents.buildings.Academy;
import game.residents.buildings.AlchemyLab;
import game.residents.buildings.Armory;
import game.residents.buildings.Base;
import game.residents.buildings.Farm;
import game.residents.buildings.Hospice;
import game.residents.buildings.Mine;
import game.residents.buildings.Tower;
import game.residents.units.Alchemist;
import game.residents.units.Archer;
import game.residents.units.Cavalry;
import game.residents.units.Cleric;
import game.residents.units.Fire_Mage;
import game.residents.units.Footman;
import game.residents.units.Ice_Mage;
import game.residents.units.Medic;
import game.residents.units.Spearman;
import game.residents.units.Warlock;
import game.residents.units.Wizard;
import main.World;

public class Player {
	
	public static Player player;

	public String name = "Andrew";
	public boolean needPlaceBase = true;
	public int baseLevel = 0;
	public int money = 14;
	public int ownedMines = 0;
	private ArrayList<TileResident> limitedSupply = new ArrayList<>();
	private ArrayList<TileResident> canMake = new ArrayList<>();
	
	public Player(Color color, String name) {
		this.name = name;
		PlayerData.me = new PlayerData(this.name, color);
		
		this.addLimited(new Base(PlayerData.me));
	}
	
	public void addLimited(TileResident limited) {
		this.limitedSupply.add(limited);
		this.canMake.add(limited);
	}
	
	public void removeCanMake(TileResident resident) {
		this.canMake.remove(resident);
		
		if (resident instanceof Base) {
			//this.addLimited(new Mine(PlayerData.me, 0));
			//this.addLimited(new Mine(PlayerData.me, 0));
			this.limitedSupply.remove(resident);
			this.needPlaceBase = false;
		}
	}
		
	public void turnStart() {		
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile currentTile = World.board.tiles[i][j];
				if (currentTile.resident().playerData() == PlayerData.me) {
					currentTile.resident().startOfTurnEffect();
				}
			}
		}
	}
	
	public TileResident[] makeOptions() {
		if (this.needPlaceBase) {
			return canMake.toArray(new TileResident[1]);
		}
		
		this.ownedMines();
		
		int size = canMake.size();
		for (int i = size - 1; i >= 0; i--) { //remove any remaining non limited options
			TileResident resident = canMake.get(i);
			if (!limitedSupply.contains(resident)) {
				canMake.remove(i);
			}
		}
		
		//add back non limited resources
		canMake.add(new Farm(PlayerData.me));
		canMake.add(new Armory(PlayerData.me));
		canMake.add(new Tower(PlayerData.me));
		
		if (this.ownedUnits() < this.maxUnits()) {
			canMake.add(new Footman(PlayerData.me));
			canMake.add(new Spearman(PlayerData.me));
			canMake.add(new Cavalry(PlayerData.me));
			canMake.add(new Archer(PlayerData.me));
			canMake.add(new Alchemist(PlayerData.me));
			canMake.add(new Cleric(PlayerData.me));
			canMake.add(new Fire_Mage(PlayerData.me));
			canMake.add(new Ice_Mage(PlayerData.me));
			canMake.add(new Medic(PlayerData.me));
			canMake.add(new Warlock(PlayerData.me));
			canMake.add(new Wizard(PlayerData.me));
		}
		
		if (this.baseLevel > 0) {
			canMake.add(new Academy(PlayerData.me));
			canMake.add(new AlchemyLab(PlayerData.me));
			canMake.add(new Hospice(PlayerData.me));
		}
		
		return canMake.toArray(new TileResident[canMake.size()]);
	}
	
	public void ownedMines() {
		ownedMines = 0;
		
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile currentTile = World.board.tiles[i][j];
				if (currentTile.resident().playerData() == PlayerData.me && currentTile.resident() instanceof Mine) {
					ownedMines++;
				}
			}
		}		
	}
	
	public int ownedUnits() {
		int owned = 0;
		
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile currentTile = World.board.tiles[i][j];
				if (!currentTile.resident().canBuildOn() && currentTile.resident().playerData() == PlayerData.me) {
					owned++;
				}
			}
		}
		
		return owned;
	}
	
	public int maxUnits() {
		int max = 0;
		
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile currentTile = World.board.tiles[i][j];
				if (currentTile.resident().playerData() == PlayerData.me) {
					max += currentTile.resident().maxUnitsBuff();
				}
			}
		}
		
		return max;
	}
	
	public ArrayList<String> ownedNames() {
		ArrayList<String> owned = new ArrayList<>();
		
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile currentTile = World.board.tiles[i][j];
				if (currentTile.resident().playerData() == PlayerData.me) {
					if (!owned.contains(currentTile.resident().name())) {
						owned.add(currentTile.resident().name());
					}
				}
			}
		}
		
		return owned;
	}
	
}
