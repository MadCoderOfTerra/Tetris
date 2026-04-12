package main;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread;

    public GamePanel() {
        //GamePanel setting
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);


    }
    public void LaunchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        //Game loop
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime
    }
    private void update() {

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

}
