package game.network;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

import game.Player;
import game.PlayerData;
import graphics.Surface;
import main.World;

public class LaunchScreen extends JPanel {
	
	private JFrame mainFrame;
	
	private Color borderColor = Color.white;
	private JLabel colorLabel;
	private JComboBox<Color> colorChooser;
	private JTextField nameField;
	
	public LaunchScreen(JFrame mainFrame) {		
		this.mainFrame = mainFrame;
		
		final Color[] colors = new Color[]{Color.white, Color.lightGray, Color.blue, Color.green, Color.red};
		borderColor = colors[0];
		PlayerData.colors = new ArrayList<>(Arrays.asList(colors));
		int width = mainFrame.getWidth();
		
		setLayout(null);
		setBounds(0, 0, width, mainFrame.getHeight());
		setBackground(Color.gray);
		
		JLabel gameName = new JLabel("Conquest", SwingConstants.CENTER);
		gameName.setOpaque(true);
		gameName.setBackground(Color.black);
		gameName.setFont(new Font("Helvetica", Font.BOLD, 70));
		gameName.setForeground(Color.white);
		gameName.setBounds(20, 20, width-40, 80);
		add(gameName);
		
		int nameWidth = 135;
		int textFieldWidth = 210;
		int space = 10;
		int total = nameWidth + textFieldWidth + space;
		
		JLabel nameHere = new JLabel("Name: ", SwingConstants.CENTER);
		nameHere.setOpaque(true);
		nameHere.setBackground(Color.lightGray);
		nameHere.setFont(new Font("Helvetica", Font.PLAIN, 36));
		nameHere.setForeground(Color.black);
		nameHere.setBounds((width-total)/2, 115, nameWidth, 50);
		add(nameHere);
		
		nameField = new JTextField(20);
		nameField.setBounds((width-total)/2 + nameWidth + space, 115, textFieldWidth, 50);
		nameField.setFont(new Font("Helvetica", Font.PLAIN, 24));
        ((AbstractDocument)nameField.getDocument()).setDocumentFilter(new LimitDocumentFilter(16));
		add(nameField);
		
		int colorWidth = 115;
		int dropdownWidth = 200;
		space = 10;
		total = colorWidth + dropdownWidth + space;
		
		JLabel colorHere = new JLabel("Color: ", SwingConstants.CENTER);
		colorHere.setOpaque(true);
		colorHere.setBackground(Color.lightGray);
		colorHere.setFont(new Font("Helvetica", Font.PLAIN, 36));
		colorHere.setForeground(Color.black);
		colorHere.setBounds((width-total)/2, 175, colorWidth, 50);
		add(colorHere);
		
		colorLabel = new JLabel(" ", SwingConstants.CENTER);
		colorLabel.setOpaque(true);
		colorLabel.setBackground(colors[0]);
		colorLabel.setFont(new Font("Helvetica", Font.PLAIN, 36));
		colorLabel.setForeground(Color.black);
		colorLabel.setBounds((width-total)/2 + colorWidth + space, 175, dropdownWidth, 50);
		add(colorLabel);
		
		colorChooser = new JComboBox<Color>(colors);
		colorChooser.setMaximumRowCount(colors.length);
		colorChooser.setBounds((width-total)/2 + colorWidth + space, 165, dropdownWidth, 50);
		colorChooser.setRenderer(new ColorOption());
		colorChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = colorChooser.getSelectedIndex();
				borderColor = colors[index];
				colorLabel.setBackground(colors[index]);
			}
			
		});
		add(colorChooser);

		JButton start = new JButton("Find Game");
		start.setFont(new Font("Helvetica", Font.PLAIN, 36));
		start.setVerticalTextPosition(AbstractButton.CENTER);
		start.setHorizontalTextPosition(AbstractButton.LEADING);
		start.setBounds((width-200)/2, 230, 200, 50);
		start.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		startGame();
	    	}
	    });
		add(start);
		
	}
	
	private void startGame() {
		Player.player = new Player(borderColor, nameField.getText());
		mainFrame.remove(this);
		mainFrame.add(new Surface());
		World.networkThread = new Thread(new Runnable() {
			@Override
			public void run() {
				NetworkClient.start();
			}
		});
		World.networkThread.start();
	}

}
