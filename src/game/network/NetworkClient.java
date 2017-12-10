package game.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import game.Board;
import game.BoardTile;
import game.PlayerData;
import game.residents.EmptyResident;
import game.residents.GoldResource;
import game.residents.TileResident;
import game.residents.Upgradeable;
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
import graphics.Sidebar;
import graphics.Surface;
import main.Main;
import main.World;

public class NetworkClient {
	
	private static final String hostName = "ec2-34-214-7-116.us-west-2.compute.amazonaws.com";
	private static final int portNumber = 4444;
	private static PrintWriter out;
	private static ArrayList<PlayerData> datas = new ArrayList<>();
	
	public static void start() {
	    
		Socket kkSocket;
		BufferedReader in = null;
		try {
			kkSocket = new Socket(hostName, portNumber);
			out = new PrintWriter(kkSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Couldn't connect to server.");
			e.printStackTrace();
			System.exit(1);
		}
    	
     	String fromServer;
     	out.println(PlayerData.me.name);

 		while (true) {
         	try {
         		fromServer = in.readLine();
         	} catch (IOException e) {
    			System.out.println("Couldn't read from server.");
    			e.printStackTrace();
    			return;
    		}
         	if (fromServer != null) {
         		System.out.println("In: " + fromServer);
         		proccessInput(fromServer);
         	} else {
         		return;
         	}
         }
    }
	
	public static void sendChange(int x, int y) {
		if (!World.isMyTurn) {
			return;
		}
		
		String outString = Integer.toString(x)+"|"+Integer.toString(y)+"|"+World.board.tiles[x][y].resident().toString();
		out.println(outString);
		System.out.println("Out: " + outString);
	}
	
	public static void sendEndTurn() {
		out.println("Done|");
		System.out.println("Out: Done");
	}
	
	public static void sendAttack(int sourceX, int sourceY, int targetX, int targetY) {
		if (!World.isMyTurn) {
			return;
		}
		
		String outString = "Damage|"+Integer.toString(sourceX)+"|"+Integer.toString(sourceY)+"|"+Integer.toString(targetX)+"|"+Integer.toString(targetY);
		out.println(outString);
		System.out.println("Out: " + outString);
	}
	
	public static void sendUpgrade(int x, int y, int slot) {
		if (!World.isMyTurn) {
			return;
		}
		
		String outString = "Upgrade|"+Integer.toString(x)+"|"+Integer.toString(y)+"|"+Integer.toString(slot);
		out.println(outString);
		System.out.println("Out: " + outString);
	}
	
	public static void sendDamage(int targetX, int targetY, int damage) {
		if (!World.isMyTurn) {
			return;
		}
		
		String outString = "Ouch|"+targetX+"|"+targetY+"|"+damage;
		out.println(outString);
		System.out.println("Out: " + outString);
	}
	
	public static void leaveGame() {
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile currentTile = World.board.tiles[i][j];
				if (currentTile.resident().playerData() == PlayerData.me) {
					World.board.setResident(i, j, new EmptyResident());
				}
			}
		}
		
		String outString = "Leave|";
		out.println(outString);
		System.out.println("Out: " + outString);
		Surface.instance.homeScreen();
	}
	
	public static void spectateGame() {
		World.isMyTurn = false;
		Sidebar.instance.redrawUI();
		Sidebar.instance.remove(Sidebar.instance.specButton);
		
		for (int i = 0; i < World.board.tiles.length; i++) {
			for (int j = 0; j < World.board.tiles[i].length; j++) {
				BoardTile currentTile = World.board.tiles[i][j];
				if (currentTile.resident().playerData() == PlayerData.me) {
					World.board.setResident(i, j, new EmptyResident());
				}
			}
		}
		
		String outString = "Spec|";
		out.println(outString);
		System.out.println("Out: " + outString);
	}
		
	private static void proccessInput(String input) {
		if (input.equals("Here?")) {
			out.println("Yup%%#!%@");
			return;
		}
		
		String[] parts = input.split("\\|");
		
		if (parts[0].equals("Turn")) {
			
			PlayerData.currentIndex++;
			if (PlayerData.currentIndex == PlayerData.players.length) {
				PlayerData.currentIndex = 0;
			}
			
			if (PlayerData.players[PlayerData.currentIndex] == PlayerData.me) {
				World.nextTurn(true);
			} else {
				Sidebar.instance.redrawUI();
			}
			
		} else if (parts[0].equals("Leave")) {
		
			if (parts.length == 1) {
				Main.instance.remove(Surface.instance);
				Main.instance.add(new LaunchScreen(Main.instance));
				return;
			}
			
			String name = parts[1];
			for (int i = 0; i < PlayerData.players.length; i++) {
				PlayerData data = PlayerData.players[i];
				if (data.name.equals(name)) {
					ArrayList<PlayerData> datas = new ArrayList<>(Arrays.asList(PlayerData.players));
					datas.remove(i);
					PlayerData.players = datas.toArray(new PlayerData[datas.size()]);
					
					if (PlayerData.currentIndex >= PlayerData.players.length) {
						PlayerData.currentIndex = 0;
					}
					return;
				}
			}
			
		} else if (parts[0].equals("Back")) {
			
			PlayerData.currentIndex--;
			
		} else if (parts[0].equals("Player")) {
			    		    		
    		if (parts.length == 3) {
        		datas.add(PlayerData.me);
    		} else {
    			PlayerData newPlayer = new PlayerData(parts[1]);
    			datas.add(newPlayer);
    		}
			
		} else if (parts[0].equals("AllSent")) {
			
			PlayerData.players = datas.toArray(new PlayerData[datas.size()]);
			
		} else if (parts[0].equals("Damage")) {
			
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			int endX = Integer.parseInt(parts[3]);
			int endY = Integer.parseInt(parts[4]);
			
			World.board.tiles[x][y].resident().attack(World.board.tiles[endX][endY].resident());
		} else if (parts[0].equals("Upgrade")) {
			
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			int slot = Integer.parseInt(parts[3]);
			TileResident resident = World.board.tiles[x][y].resident();
			if (resident instanceof Upgradeable) {
				Upgradeable upgradeable = (Upgradeable)resident;
				upgradeable.upgrade(slot);
			}
        
		} else if (parts[0].equals("Ouch")) {
			
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			int damage = Integer.parseInt(parts[3]);
			World.board.tiles[x][y].resident().takeDamage(damage);
			
	    } else {
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			String[] resident = parts[2].split(":");
			
			if (resident[0].equals("GoldResource")) {
				Board board = World.board;
				BoardTile tile = board.tiles[x][y];
				tile.setResident(new GoldResource());
				return;
			}
			
			PlayerData from = PlayerData.players[Integer.parseInt(parts[3])];
			
			switch(resident[0]) {
			case "Armory":
				World.board.tiles[x][y].setResident(new Armory(from));
				break;
			case "Base":
				World.board.tiles[x][y].setResident(new Base(from));
				break;
			case "EmptyResident":
				World.board.tiles[x][y].setResident(new EmptyResident());
				break;
			case "Farm":
				World.board.tiles[x][y].setResident(new Farm(from));
				break;
			case "Footman":
				World.board.tiles[x][y].setResident(new Footman(from));
				break;
			case "Mine":
				World.board.tiles[x][y].setResident(new Mine(from));
				break;
			case "Spearman":
				World.board.tiles[x][y].setResident(new Spearman(from));
				break;
			case "Archer":
				World.board.tiles[x][y].setResident(new Archer(from));
				break;
			case "Tower":
				World.board.tiles[x][y].setResident(new Tower(from));
				break;
			case "Cavalry":
				World.board.tiles[x][y].setResident(new Cavalry(from));
				break;
			case "Academy":
				World.board.tiles[x][y].setResident(new Academy(from));
				break;
			case "Laboratory":
				World.board.tiles[x][y].setResident(new AlchemyLab(from));
				break;
			case "Infirmary":
				World.board.tiles[x][y].setResident(new Hospice(from));
				break;
			case "Alchemist":
				World.board.tiles[x][y].setResident(new Alchemist(from));
				break;
			case "Cleric":
				World.board.tiles[x][y].setResident(new Cleric(from));
				break;
			case "Fire Mage":
				World.board.tiles[x][y].setResident(new Fire_Mage(from));
				break;
			case "Ice Mage":
				World.board.tiles[x][y].setResident(new Ice_Mage(from));
				break;
			case "Medic":
				World.board.tiles[x][y].setResident(new Medic(from));
				break;
			case "Warlock":
				World.board.tiles[x][y].setResident(new Warlock(from));
				break;
			case "Wizard":
				World.board.tiles[x][y].setResident(new Wizard(from));
				break;
			default:
				System.out.println("invalid input resident");
				break;
			}	
			
			from.setBuffs();
		}
	}
	
}
