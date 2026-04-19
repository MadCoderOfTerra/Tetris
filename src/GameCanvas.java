import javax.swing.*;
import java.awt.*;

public class GameCanvas extends JPanel implements Runnable{
    public int Width = 800;
    public int Height = 800;

    Grid_ gridd = new Grid_(Width, Height);
    Thread gameThread;                                         // this thing is used to run the gameloop

    public GameCanvas() {
        this.setPreferredSize(new Dimension(Width,Height));    //force the size of the canvas to be this size (basically prevent the title from taking some pixels)
        this.setBackground(Color.WHITE);
        this.setLayout(null);//Stop java from forcefeeding me with its default layouts, forcing it to use my layout

        TetrisBlock b = new L_Piece();

        b.x = 2;
        b.y = 3;
        b.ColorToChooseID = 0;
        gridd.Current_Block = b;

    }

    @Override
    public void run() { //This is the game loop don't touch it pls
        while(true){

            update();
            repaint();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException var2) {
                return;
            }


        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gridd.Draw(g,Width,Height);
    }

    public void update(){
        gridd.update();
    }

    public void LaunchGame(){ //Obvious brah
        gameThread = new Thread(this);
        gameThread.start();
    }
}