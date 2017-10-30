package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.util.Collections;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

import main.World;
import util.input.InputBinds;

public class Surface extends JPanel implements Runnable {
	
	public static Surface instance = null;
	
	private Thread t;
	private final int DELAY = 100;
	private JPanel ui = null;
		
	public Surface() {
		super();
		
		instance = this;

		InputBinds.bind(this);
		setLayout(null);
		setFocusable(true);
		this.setBounds(0, 0, Camera.CAM_WIDTH + Sidebar.width, Camera.CAM_HEIGHT);
		add(new Sidebar());
		
		Set<KeyStroke> empty = Collections.emptySet();
	    setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, empty);
	    setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, empty);
		
		World.initialize();
		
		setBackground(Color.black);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	    World.ctx = (Graphics2D) g;
	    World.graphicsUpdate();
	}
	
	@Override
    public void addNotify() {
        super.addNotify();

        this.t = new Thread(this);
        this.t.start();        
    }
	
	@Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {
            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }

            beforeTime = System.currentTimeMillis();
        }
    }
	
	public void addUI(JPanel ui) {
		removeUI();
		
		if (ui == null) {
			return;
		}
		
		this.ui = ui;
		add(ui);
		
		this.revalidate();
	}
	
	public void removeUI() {
		if (ui != null) {
			remove(ui);
		}
		
		ui = null;
	}
	
}
