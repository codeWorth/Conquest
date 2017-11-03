package game.residents.interfaces;

import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Player;
import game.network.NetworkClient;
import game.residents.Upgrade;
import graphics.Sidebar;
import main.World;

public class UpgradePanel extends JPanel implements Clickable {
	
	private static final int width = 180;
	private static final int spacing = 35;
	private static final int height = 30;
	private static final int xPos = (Sidebar.width-width)/2;
	
	private Upgrade[] upgrades;
	private JLabel[] names;
	private JLabel[] costs;
	private JLabel[] covers;

	public UpgradePanel(Upgrade[] upgrades) {
		setLayout(null);
		setOpaque(false);
		
		this.upgrades = upgrades;
		this.names = new JLabel[upgrades.length];
		this.costs = new JLabel[upgrades.length];
		this.covers = new JLabel[upgrades.length];
		
		int i = 0;
		for (Upgrade upgrade : upgrades) {
			
			if (upgrade == null) {
				break;
			}
			
			JLabel name = new JLabel("", SwingConstants.LEADING);
			name.setOpaque(true);
			name.setBackground(upgrade.color);
			name.setFont(new Font("Helvetica", Font.PLAIN, 15));
			name.setBounds(xPos, i * spacing, width, height);
			name.setVerticalTextPosition(AbstractButton.CENTER);
			names[i] = name;
			
			JLabel cost = new JLabel("", SwingConstants.TRAILING);
			cost.setOpaque(false);
			cost.setFont(new Font("Helvetica", Font.PLAIN, 15));
			cost.setBounds(xPos, i * spacing, width, height);
			cost.setVerticalTextPosition(AbstractButton.CENTER);
			costs[i] = cost;
			
			JLabel cover = new JLabel("");
			cover.setOpaque(true);
			cover.setBackground(new Color(0, 0, 0, 140));
			cover.setBounds(xPos, i * spacing, width, height);
			cover.setVisible(true);
			covers[i] = cover;
			
			add(cover);
			add(cost);
			add(name);
					
			i++;
		}
		
		redraw();
				
	}
	
	@Override
	public void pressed(double x, double y) {
		if (x < xPos || x > xPos + width || y < 0) {
			return;
		}
		
		int slot = (int)(y / spacing);
		int topY = slot * spacing;
		int bottomY = topY + height;
		if (y < topY || y > bottomY) {
			return;
		}
		
		Upgrade clicked = upgrades[slot];
		
		if (Player.player.money >= clicked.cost) {
			upgrades[slot] = clicked.run();
			NetworkClient.sendUpgrade(World.board.selectedTile.x, World.board.selectedTile.y, slot);
			if (upgrades[slot] == null) {
				names[slot].setVisible(false);
				costs[slot].setVisible(false);
			} else {
				redraw();
			}
			
			Sidebar.instance.redrawUI();
		}
	}
	
	private void redraw() {
		for (int slot = 0; slot < upgrades.length; slot++) {
		
			if (Player.player.money < upgrades[slot].cost) {
				covers[slot].setVisible(true);
			} else {
				covers[slot].setVisible(false);
			}
			
			names[slot].setText(" " + upgrades[slot].name);
			costs[slot].setText("$" + Integer.toString(upgrades[slot].cost) + " ");
			
		}
	}
	
}
