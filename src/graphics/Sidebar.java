package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.Player;
import game.residents.interfaces.BuildMenu;
import main.World;

public class Sidebar extends JPanel {

	public static Sidebar instance = null;
	
	private JPanel stats;
	
	private JButton endTurnButton;
	private JLabel turnLabel;
	private JLabel moneyLabel;
	private JLabel unitsLabel;
	
	public static final int width = 200;
	public static int detailsStart = 210;
	
	public Sidebar() {
		setLayout(null);
		setBackground(Color.darkGray);
		setBounds(Camera.CAM_WIDTH, 0, width, Camera.CAM_HEIGHT);
		setFocusable(false);
			
		turnLabel = new JLabel("Player 1", SwingConstants.CENTER);
		turnLabel.setOpaque(true);
		turnLabel.setBackground(Player.player.color);
		turnLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		turnLabel.setBounds((width - 110) / 2, 15, 110, 30);
		turnLabel.setVerticalTextPosition(AbstractButton.CENTER);
		
		moneyLabel = new JLabel("6 Gold", SwingConstants.CENTER);
		moneyLabel.setOpaque(true);
		moneyLabel.setBackground(new Color(246, 249, 157));
		moneyLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		moneyLabel.setBounds((width - 90) / 2, 55, 90, 30);
		moneyLabel.setVerticalTextPosition(AbstractButton.CENTER);
		
		unitsLabel = new JLabel("Units: 0/3", SwingConstants.CENTER);
		unitsLabel.setOpaque(true);
		unitsLabel.setBackground(new Color(234, 204, 187));
		unitsLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
		unitsLabel.setBounds((width - 120) / 2, 95, 120, 30);
		unitsLabel.setVerticalTextPosition(AbstractButton.CENTER);
  		
		endTurnButton = new JButton("End Turn");
		endTurnButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
		endTurnButton.setVerticalTextPosition(AbstractButton.CENTER);
		endTurnButton.setHorizontalTextPosition(AbstractButton.CENTER);
		endTurnButton.setBounds((width - 110) / 2, 145, 110, 33);
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
			endTurnButton.setVisible(true);
			turnLabel.setVisible(true);
			moneyLabel.setVisible(true);
			unitsLabel.setVisible(true);
			
			turnLabel.setText("Your Turn");
			moneyLabel.setText(Integer.toString(Player.player.money) + " Gold");
			unitsLabel.setText("Units: " + Player.player.ownedUnits() + "/" + Player.player.maxUnits());
		} else {
			endTurnButton.setVisible(false);
			turnLabel.setVisible(false);
			moneyLabel.setVisible(false);
			unitsLabel.setVisible(false);
		}
	}
	
	public void press(double mouseX, double mouseY) {
		if (mouseY > detailsStart && this.stats != null) {
			if (this.stats instanceof BuildMenu) {
				BuildMenu menu = (BuildMenu)this.stats;
				menu.pressed(mouseX, mouseY - detailsStart);
			}
			
			return;
		}
	}
	
	public void addStats(JPanel stats) {
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
