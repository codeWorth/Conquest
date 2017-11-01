package game;

import java.awt.Color;
import java.util.ArrayList;

import game.residents.Archer;
import game.residents.Armory;
import game.residents.Base;
import game.residents.Farm;
import game.residents.Footman;
import game.residents.Mine;
import game.residents.Spearman;
import game.residents.TileResident;
import game.residents.Tower;
import main.World;

public class Player {
	
	public static Player player;

	public String name = "Andrew";
	public boolean needPlaceBase = true;
	public int money = 0;
	private ArrayList<TileResident> limitedSupply = new ArrayList<>();
	private ArrayList<TileResident> canMake = new ArrayList<>();
	
	public Player(Color color) {
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
			this.addLimited(new Mine(PlayerData.me, 0));
			this.addLimited(new Mine(PlayerData.me, 0));
			this.limitedSupply.remove(resident);
			this.needPlaceBase = false;
		}
	}
		
	public void turnStart() {		
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile currentTile = World.board.tiles[i][j];
				if (currentTile.resident.playerData() == PlayerData.me) {
					currentTile.resident.startOfTurnEffect();
				}
			}
		}
	}
	
	public TileResident[] makeOptions() {
		if (this.needPlaceBase) {
			return canMake.toArray(new TileResident[1]);
		}
		
		int size = canMake.size();
		for (int i = size - 1; i >= 0; i--) { //remove any remaining non limited options
			TileResident resident = canMake.get(i);
			if (!limitedSupply.contains(resident)) {
				canMake.remove(i);
			}
		}
		
		//add back non limited resources
		canMake.add(new Mine(PlayerData.me));
		canMake.add(new Farm(PlayerData.me));
		canMake.add(new Armory(PlayerData.me));
		canMake.add(new Tower(PlayerData.me));
		
		if (this.ownedUnits() < this.maxUnits()) {
			canMake.add(new Footman(PlayerData.me));
			canMake.add(new Spearman(PlayerData.me));
			canMake.add(new Archer(PlayerData.me));
		}
		
		return canMake.toArray(new TileResident[canMake.size()]);
	}
	
	public int ownedUnits() {
		int owned = 0;
		
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile currentTile = World.board.tiles[i][j];
				if (!currentTile.resident.canBuildOn() && currentTile.resident.playerData() == PlayerData.me) {
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
				if (currentTile.resident.playerData() == PlayerData.me) {
					max += currentTile.resident.maxUnitsBuff();
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
				if (currentTile.resident.playerData() == PlayerData.me) {
					if (!owned.contains(currentTile.resident.name())) {
						owned.add(currentTile.resident.name());
					}
				}
			}
		}
		
		return owned;
	}
	
}
