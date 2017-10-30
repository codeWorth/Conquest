
package main;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import graphics.Camera;
import graphics.Sidebar;
import graphics.Surface;

public class Main extends JFrame {
		
	public Main() {
		initUI();		
	}

	private void initUI() {
				
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		Camera.CAM_WIDTH = size.width - Sidebar.width;
		Camera.CAM_HEIGHT = size.height - 25;
		
		setTitle("Conquest");
		setLocationRelativeTo(null);
		setResizable(false);
		setSize(Camera.CAM_WIDTH + Sidebar.width, Camera.CAM_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(0, 0);

		add(new Surface());
		
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
