package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Player;
import game.PlayerData;
import game.network.NetworkClient;
import game.residents.interfaces.BuildMenu;
import game.residents.interfaces.Clickable;
import main.World;

public class Sidebar extends JPanel {

	public static Sidebar instance = null;
	
	private JComponent stats;
		
	private JButton endTurnButton;
	public JButton specButton;
	private JButton leaveButton;
	public JLabel turnLabel;
	private JLabel moneyLabel;
	private JLabel unitsLabel;
	
	public static final int width = 200;
	public static int detailsStart = 255;
	
	public Sidebar() {
		setLayout(null);
		setBackground(Color.darkGray);
		setBounds(Camera.CAM_WIDTH, 0, width, Camera.CAM_HEIGHT);
		setFocusable(false);
		
		specButton = new JButton("Spectate");
		specButton.setFont(new Font("Helvetica", Font.PLAIN, 14));
		specButton.setVerticalTextPosition(AbstractButton.CENTER);
		specButton.setHorizontalTextPosition(AbstractButton.CENTER);
		specButton.setBounds(10, 10, width/2 - 15, 28);
		specButton.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		NetworkClient.spectateGame();
	    	}
	    });
		add(specButton);
		specButton.setVisible(false);
		
		leaveButton = new JButton("Leave");
		leaveButton.setFont(new Font("Helvetica", Font.PLAIN, 14));
		leaveButton.setVerticalTextPosition(AbstractButton.CENTER);
		leaveButton.setHorizontalTextPosition(AbstractButton.CENTER);
		leaveButton.setBounds(5 + width/2, 10, width/2 - 15, 28);
		leaveButton.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		NetworkClient.leaveGame();
	    	}
	    });
		add(leaveButton);
		leaveButton.setVisible(false);
			
		turnLabel = new JLabel("Player 1", SwingConstants.CENTER);
		turnLabel.setOpaque(true);
		turnLabel.setBackground(Color.white);
		turnLabel.setFont(new Font("Helvetica", Font.PLAIN, 18));
		turnLabel.setBounds((width - 180) / 2, 60, 180, 30);
		turnLabel.setVerticalTextPosition(AbstractButton.CENTER);
		
		moneyLabel = new JLabel("6 Gold", SwingConstants.CENTER);
		moneyLabel.setOpaque(true);
		moneyLabel.setBackground(new Color(246, 249, 157));
		moneyLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		moneyLabel.setBounds((width - 90) / 2, 100, 90, 30);
		moneyLabel.setVerticalTextPosition(AbstractButton.CENTER);
		
		unitsLabel = new JLabel("Units: 0/3", SwingConstants.CENTER);
		unitsLabel.setOpaque(true);
		unitsLabel.setBackground(new Color(234, 204, 187));
		unitsLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		unitsLabel.setBounds((width - 120) / 2, 140, 120, 30);
		unitsLabel.setVerticalTextPosition(AbstractButton.CENTER);
  		
		endTurnButton = new JButton("End Turn");
		endTurnButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
		endTurnButton.setVerticalTextPosition(AbstractButton.CENTER);
		endTurnButton.setHorizontalTextPosition(AbstractButton.CENTER);
		endTurnButton.setBounds((width - 110) / 2, 190, 110, 33);
		endTurnButton.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		World.nextTurn(false);
	    	}
	    });
		
		add(turnLabel);
		add(endTurnButton);
		add(moneyLabel);
		add(unitsLabel);
		
		instance = this;
	}
	
	public void redrawUI() {
		if (World.isMyTurn) {
			turnLabel.setVisible(true);
			endTurnButton.setVisible(true);
			moneyLabel.setVisible(true);
			unitsLabel.setVisible(true);
			specButton.setVisible(true);
			leaveButton.setVisible(true);
			
			turnLabel.setText("Your Turn");
			moneyLabel.setText(Integer.toString(Player.player.money) + " Gold");
			unitsLabel.setText("Units: " + Player.player.ownedUnits() + "/" + Player.player.maxUnits());
			
			if (PlayerData.me == null) {
				turnLabel.setBackground(Color.white);
			} else {
				turnLabel.setBackground(PlayerData.me.color);
			}
		} else {
			endTurnButton.setVisible(false);
			moneyLabel.setVisible(false);
			unitsLabel.setVisible(false);
			
			if (PlayerData.players == null) {
				turnLabel.setVisible(false);
			} else {
				turnLabel.setVisible(true);
				specButton.setVisible(true);
				leaveButton.setVisible(true);
				turnLabel.setText(PlayerData.players[PlayerData.currentIndex].name+"'s Turn");
			}
		}
	}
	
	public void press(double mouseX, double mouseY) {
		if (mouseY > detailsStart && this.stats != null) {
			if (this.stats instanceof Clickable) {
				Clickable menu = (Clickable)this.stats;
				menu.pressed(mouseX, mouseY - detailsStart);
			}
		}
	}
	
	public void rightPress(double mouseX, double mouseY) {
		if (mouseY > detailsStart && this.stats != null) {
			if (this.stats instanceof BuildMenu) {
				BuildMenu menu = (BuildMenu)this.stats;
				menu.rightPressed(mouseX, mouseY - detailsStart);
			}
		}
	}
	
	public void addStats(JComponent stats) {
		removeStats();
		
		if (stats == null) {
			return;
		}
		
		this.stats = stats;
		add(stats);
		
		this.revalidate();
	}
	
	public void removeStats() {
		if (stats != null) {
			remove(stats);
		}
		
		stats = null;
	}
	
}
