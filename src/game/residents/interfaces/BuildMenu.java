package game.residents.interfaces;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Player;
import game.PlayerData;
import game.residents.TileResident;
import graphics.Sidebar;
import main.World;

public class BuildMenu extends JPanel {
	
	private TileResident[] buildingOptions;
	private boolean[] canBuy;
	private int xPos;
	private static final int spacing = 35;
	private static final int height = 30;

	public BuildMenu(TileResident[] buildingOptions) {
		setLayout(null);
		setOpaque(false);
		setBounds(0, Sidebar.detailsStart, Sidebar.width, buildingOptions.length * 35 + 40);
		
		this.buildingOptions = buildingOptions;
		this.canBuy = new boolean[buildingOptions.length];
		
		int i = 0;
		xPos = (Sidebar.width - 180) / 2;
		
		ArrayList<String> owned = Player.player.ownedNames();
		
		for (TileResident resident : buildingOptions) {
			JLabel name	= new JLabel(" " + resident.name(), SwingConstants.LEADING);
			name.setOpaque(true);
			name.setBackground(Color.white); //name.setBackground(resident.mapColor());
			name.setFont(new Font("Helvetica", Font.PLAIN, 16));
			name.setVerticalTextPosition(AbstractButton.CENTER);
			name.setBounds(xPos, i * spacing, 180, height);
			
			JLabel cost = new JLabel("$" + Integer.toString(resident.cost()) + " ", SwingConstants.TRAILING);
			cost.setFont(new Font("Helvetica", Font.PLAIN, 16));
			cost.setVerticalTextPosition(AbstractButton.CENTER);
			cost.setBounds(xPos, i * spacing, 180, height);
			
			
			boolean canBuy = true;
			for (String residentName : resident.prereqs()) {
				if (!owned.contains(residentName)) {
					canBuy = false;
					break;
				}
			}
			
			if (resident.cost() > Player.player.money || !canBuy) {
				JLabel cover = new JLabel("");
				cover.setOpaque(true);
				cover.setBackground(new Color(0, 0, 0, 140));
				cover.setBounds(xPos, i * spacing, 180, height);
				add(cover);
			} else {
				this.canBuy[i] = true;
			}
			
			add(cost);
			add(name);
			
			i++;
		}
	}
	
	public void pressed(double mouseX, double mouseY) {
		if (mouseX < xPos || mouseX > xPos + 180) {
			return;
		}
		
		int slot = (int)(mouseY / spacing);
		int topY = slot * spacing;
		int bottomY = topY + height;
		if (mouseY < topY || mouseY > bottomY) {
			return;
		}
		
		ArrayList<String> owned = Player.player.ownedNames();
		
		TileResident building = this.buildingOptions[slot];
		for (String name : building.prereqs()) {
			if (!owned.contains(name)) {
				return;
			}
		}
		
		if (canBuy[slot]) {
			Player.player.money -= building.cost();
			Player.player.removeCanMake(building);
		} else {
			return;
		}
		
		try {
			World.board.setSelectedResident(building.getClass().getConstructor(PlayerData.class).newInstance(PlayerData.me));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			System.out.println("That constructor doesn't exist!");
			e.printStackTrace();
		}
		
		World.board.deselectTile();
		
		Sidebar.instance.redrawUI();
	}
	
}
