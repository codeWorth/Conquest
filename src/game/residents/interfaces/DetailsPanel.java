package game.residents.interfaces;

import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.OT;
import game.PlayerData;
import game.residents.TileResident;
import graphics.Sidebar;

public abstract class DetailsPanel extends JPanel {

	protected int addHeight = 0;
	private final int spacing = 5;
	
	public DetailsPanel(int height, TileResident resident, boolean info) {
		setLayout(null);
		setOpaque(false);
		setBounds(0, Sidebar.detailsStart, Sidebar.width, height);
		
		if (!info) {
			this.addInfo("Player: " + resident.playerData().name, 160, 30, resident.playerData().color);
		}
		this.addInfo(resident.name(), 120, 30, Color.white);
		this.addInfo(resident.health() + " Health", 110, 30, new Color(150, 80, 80));
		
		if (resident.playerData() == PlayerData.me && resident.moveRange() > 0 && info) {
			JLabel actions = new JLabel(resident.actionsLeft() + " Actions Left", SwingConstants.CENTER);
			actions.setOpaque(true);
			actions.setBackground(Color.LIGHT_GRAY);
			actions.setFont(new Font("Helvetica", Font.PLAIN, 20));
			actions.setBounds((Sidebar.width - 150) / 2, addHeight, 150, 30);
			actions.setVerticalTextPosition(AbstractButton.CENTER);
			add(actions);
			
			addHeight += spacing + 30;
		}
		
		if (resident.ots.size() > 0) {
			addHeight += 10;
			
			JLabel ots = new JLabel("Current Effects:", SwingConstants.CENTER);
			ots.setOpaque(true);
			ots.setBackground(Color.WHITE);
			ots.setFont(new Font("Helvetica", Font.PLAIN, 20));
			ots.setBounds((Sidebar.width - 180) / 2, addHeight, 180, 30);
			ots.setVerticalTextPosition(AbstractButton.CENTER);
			add(ots);
			addHeight += spacing + 30;
			
			boolean any = false;
			for (OT ot : resident.ots) {
				if (ot.ticksLeft > 0) {
					this.addInfo(ot.description(), 160, 30, ot.color(), 14);
					addHeight -= spacing;
					this.addInfo("Turns left: " + ot.ticksLeft, 140, 30, ot.color(), 14);
					any = true;
				}
			}
			
			if (!any) {
				ots.setVisible(false);
				addHeight -= spacing + 30;
			}
		}
	}
	
	public DetailsPanel(int height, TileResident resident) {
		this(height, resident, true);
	}
	
	protected void addInfo(String info, int width, int height, Color color) {
		this.addInfo(info, width, height, color, 20);
	}
	
	protected void addInfo(String info, int width, int height, Color color, int fontSize) {
		JLabel newLabel = new JLabel(info, SwingConstants.CENTER);
		newLabel.setOpaque(true);
		newLabel.setBackground(color);
		newLabel.setFont(new Font("Helvetica", Font.PLAIN, fontSize));
		newLabel.setBounds((Sidebar.width - width) / 2, addHeight, width, height);
		add(newLabel);
		
		addHeight += spacing + height;
	}
	
}
