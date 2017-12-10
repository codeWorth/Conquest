package game.residents.interfaces;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Player;
import game.PlayerData;
import game.residents.TileResident;
import graphics.Camera;
import graphics.Sidebar;
import main.World;

public class BuildMenu extends JPanel implements Clickable {
	
	private static final int spacing = 35;
	private static final int maxHeight = Camera.CAM_HEIGHT - Sidebar.detailsStart - spacing;
	private static final int maxItems = maxHeight/spacing - 2;
	private static final int height = 30;
	private static final int navButtonSpacing = 20;
	private static final int navButtonWidth = (Sidebar.width - navButtonSpacing*3)/2;
	
	private TileResident[] buildingOptions;
	private TileResident[] allOptions;
	private int index;
	private boolean[] canBuy;
	private int xPos;
	private boolean canNext = false;
	private boolean canPrev = false;
	
	public BuildMenu(TileResident[] buildingOptions) {
		this(buildingOptions, 0);
	}

	public BuildMenu(TileResident[] buildingOptions, int index) {
		setLayout(null);
		setOpaque(false);
		
		this.index = index;
		int items = buildingOptions.length - index;
		int wantedHeight = items * 35;
		if (wantedHeight > maxHeight) {
			items = maxItems;
		}
		setBounds(0, Sidebar.detailsStart, Sidebar.width, maxHeight);
		
		this.allOptions = buildingOptions;
		this.buildingOptions = Arrays.copyOfRange(buildingOptions, index, items + index);
		this.canBuy = new boolean[this.buildingOptions.length];
		
		int i = 0;
		xPos = (Sidebar.width - 180) / 2;
		
		ArrayList<String> owned = Player.player.ownedNames();
		
		if (index + items < buildingOptions.length) {
			JLabel next = new JLabel("Next", SwingConstants.CENTER);
			next.setBackground(Color.white);
			next.setOpaque(true);
			next.setFont(new Font("Helvetica", Font.PLAIN, 14));
			next.setBounds(navButtonWidth+navButtonSpacing*2, maxHeight - spacing, navButtonWidth, height);
			canNext = true;
			add(next);
		}
		if (index > 0) {
			JLabel prev = new JLabel("Previous", SwingConstants.CENTER);
			prev.setOpaque(true);
			prev.setBackground(Color.white);
			prev.setFont(new Font("Helvetica", Font.PLAIN, 14));
			prev.setBounds(navButtonSpacing, maxHeight - spacing, navButtonWidth, height);
			canPrev = true;
			add(prev);
		}
		
		for (TileResident resident : this.buildingOptions) {
			JLabel name	= new JLabel(" " + resident.name(), SwingConstants.LEADING);
			name.setOpaque(true);
			name.setBackground(Color.white);
			name.setFont(new Font("Helvetica", Font.PLAIN, 16));
			name.setBounds(xPos, i * spacing, 180, height);
			
			JLabel cost = new JLabel("$" + Integer.toString(resident.cost()) + " ", SwingConstants.TRAILING);
			cost.setFont(new Font("Helvetica", Font.PLAIN, 16));
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
		
		if (mouseY > maxHeight - spacing) {
			if (canNext && mouseX > navButtonWidth + navButtonSpacing*2) {
				Sidebar.instance.addStats(new BuildMenu(this.allOptions, this.index + maxItems));
				return;
			} else if (canPrev && mouseX < navButtonWidth + navButtonSpacing) {
				int newIndex = this.index - maxItems;
				if (newIndex < 0) {
					newIndex = 0;
				}
				Sidebar.instance.addStats(new BuildMenu(this.allOptions, newIndex));
				return;
			}
		}
		
		int slot = (int)(mouseY / spacing);
		int topY = slot * spacing;
		int bottomY = topY + height;
		if (mouseY < topY || mouseY > bottomY || slot >= this.buildingOptions.length || slot < 0) {
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
	
	public void rightPressed(double mouseX, double mouseY) {
		if (mouseX < xPos || mouseX > xPos + 180) {
			return;
		}
		
		int slot = (int)(mouseY / spacing);
		int topY = slot * spacing;
		int bottomY = topY + height;
		if (mouseY < topY || mouseY > bottomY || slot >= this.buildingOptions.length || slot < 0) {
			return;
		}
				
		TileResident building = this.buildingOptions[slot];
		Sidebar.instance.addStats(building.statsPanel(false));
		World.isInfo = true;
	}
	
}
