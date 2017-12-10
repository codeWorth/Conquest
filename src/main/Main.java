
package main;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import game.network.LaunchScreen;
import graphics.Camera;
import graphics.Sidebar;

public class Main extends JFrame {
		
	public static Main instance;
	
	public Main() {
		initUI();		
	}

	private void initUI() {
		
		instance = this;
				
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		Camera.CAM_WIDTH = size.width - Sidebar.width - 25;
		Camera.CAM_HEIGHT = size.height - 25;
		
		setTitle("Conquest");
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(Camera.CAM_WIDTH + Sidebar.width, Camera.CAM_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(0, 0);

		add(new LaunchScreen(this));
		
	}

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				Main ex = new Main();
				ex.setVisible(true);
			}
		});
		
	}
}
