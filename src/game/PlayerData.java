package game;

import java.awt.Color;

public class PlayerData {
	
	public static PlayerData[] players = null;
	public static int nextIndex = 0;
	
	public static PlayerData me;
	public static PlayerData noPlayer = new PlayerData("", new Color(0, 0, 0, 0));

	public String name;
	public Color color;
	
	public PlayerData(String name, Color color) {
		this.name = name;
		this.color = color;
	}
	
}
