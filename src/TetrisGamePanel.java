package MAIN;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class TetrisGamePanel extends JPanel implements Runnable {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread Gamethread;
    PlayManager pm;

    public TetrisGamePanel(){
        //SETTING
        this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);
        //IMPLEMENT KEYLISTENER
        this.addKeyListener(new BasicComboBoxUI.KeyHandler());
        this.setFocusable(true);


        pm = new PlayManager();
    }
    public void launchGame(){
        Gamethread = new Thread(this);
        Gamethread.start();
    }

    @Override
    public void run(){

        //GameLoop
        double DrawInterval = 1000000000/FPS;
        double delta = 0;
        long LastTime = System.nanoTime();
        long CurrentTime;

        while (Gamethread!=null){
            CurrentTime = System.nanoTime();
            delta += (CurrentTime - LastTime) / DrawInterval;
            LastTime =CurrentTime;

            if (delta >= 1){
                update();
                repaint();
                delta--;
            }
        }
    }
    private void update (){
        pm.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        pm.draw(g2);
    }
}
