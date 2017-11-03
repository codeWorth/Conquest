package game.network;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import game.PlayerData;
import game.residents.Archer;
import game.residents.Armory;
import game.residents.Base;
import game.residents.Cavalry;
import game.residents.EmptyResident;
import game.residents.Farm;
import game.residents.Footman;
import game.residents.Mine;
import game.residents.Spearman;
import game.residents.TileResident;
import game.residents.Tower;
import game.residents.Upgradeable;
import main.World;

public class NetworkClient {
	
	private static final String hostName = "ec2-34-214-7-116.us-west-2.compute.amazonaws.com";
	private static final int portNumber = 4444;
	private static PrintWriter out;
	private static ArrayList<PlayerData> datas = new ArrayList<>();
	
	public static void start() {
        try (
            Socket kkSocket = new Socket(hostName, portNumber);
        	PrintWriter outTry = new PrintWriter(kkSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(kkSocket.getInputStream()));
        ) {
        	out = outTry;
        	String fromServer;
        	
            while ((fromServer = in.readLine()) != null) {
            	System.out.println("In: " + fromServer);
            	proccessInput(fromServer);
            }
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
	
	public static void sendChange(int x, int y) {
		String outString = Integer.toString(x)+"|"+Integer.toString(y)+"|"+World.board.tiles[x][y].resident.toString();
		out.println(outString);
		System.out.println("Out: " + outString);
	}
	
	public static void sendEndTurn() {
		out.println("Done|");
		System.out.println("Out: Done");
	}
	
	public static void sendAttack(int sourceX, int sourceY, int targetX, int targetY) {
		String outString = "Damage|"+Integer.toString(sourceX)+"|"+Integer.toString(sourceY)+"|"+Integer.toString(targetX)+"|"+Integer.toString(targetY);
		out.println(outString);
		System.out.println("Out: " + outString);
	}
	
	public static void sendUpgrade(int x, int y, int slot) {
		String outString = "Upgrade|"+Integer.toString(x)+"|"+Integer.toString(y)+"|"+Integer.toString(slot);
		out.println(outString);
		System.out.println("Out: " + outString);
	}
		
	private static void proccessInput(String input) {
		String[] parts = input.split("\\|");
		
		if (parts[0].equals("Done")) {
			
			World.nextTurn(true);
			
		} else if (parts[0].equals("Player")) {
			
    		String[] attributes = parts[1].split(",");
    		
    		String name = attributes[0];
    		int r = Integer.parseInt(attributes[1]);
    		int g = Integer.parseInt(attributes[2]);
    		int b = Integer.parseInt(attributes[3]);
    		Color color = new Color(r, g, b);
    		
    		PlayerData newPlayer = new PlayerData(name, color);
    		datas.add(newPlayer);
    		if (parts.length == 3) {
    			PlayerData.me = newPlayer;
    		}
			
		} else if (parts[0].equals("AllSent")) {
			
			PlayerData.players = datas.toArray(new PlayerData[datas.size()]);
			
		} else if (parts[0].equals("Damage")) {
			
			int x = Integer.parseInt(parts[1]);
			int y = Integer.parseInt(parts[2]);
			int endX = Integer.parseInt(parts[3]);
			int endY = Integer.parseInt(parts[4]);
			
			World.board.tiles[x][y].resident.attack(World.board.tiles[endX][endY].resident);
		} else if (parts[0].equals("Start")) {
			
			String outString = "Player|"+PlayerData.me.name+","+PlayerData.me.color.getRed()+","+PlayerData.me.color.getGreen()+","+PlayerData.me.color.getBlue();
        	out.println(outString);
        	System.out.println("Out: " + outString);
		} else if (parts[0].equals("Upgrade")) {
			
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			int slot = Integer.parseInt(parts[2]);
			TileResident resident = World.board.tiles[x][y].resident;
			if (resident instanceof Upgradeable) {
				Upgradeable upgradeable = (Upgradeable)resident;
				upgradeable.upgrade(slot);
			}
        
	    } else {
			int x = Integer.parseInt(parts[0]);
			int y = Integer.parseInt(parts[1]);
			String[] resident = parts[2].split(":");
			PlayerData from = PlayerData.players[Integer.parseInt(parts[3])];
			
			switch(resident[0]) {
			case "Armory":
				World.board.tiles[x][y].resident = new Armory(from);
				break;
			case "Base":
				World.board.tiles[x][y].resident = new Base(from);
				break;
			case "EmptyResident":
				World.board.tiles[x][y].resident = new EmptyResident();
				break;
			case "Farm":
				World.board.tiles[x][y].resident = new Farm(from);
				break;
			case "Footman":
				World.board.tiles[x][y].resident = new Footman(from);
				break;
			case "Mine":
				World.board.tiles[x][y].resident = new Mine(from);
				break;
			case "Spearman":
				World.board.tiles[x][y].resident = new Spearman(from);
				break;
			case "Archer":
				World.board.tiles[x][y].resident = new Archer(from);
				break;
			case "Tower":
				World.board.tiles[x][y].resident = new Tower(from);
				break;
			case "Cavalry":
				World.board.tiles[x][y].resident = new Cavalry(from);
				break;
			default:
				System.out.println("invalid input resident");
				break;
			}		
		}
	}
	
}
