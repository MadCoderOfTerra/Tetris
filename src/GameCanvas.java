import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GameCanvas extends JPanel implements Runnable{
    public int Width = 800;
    public int Height = 800;
    public int Fps = 144;


    Grid_ gridd = new Grid_();
    Thread gameThread; // this thing is used to run the gameloop

    public GameCanvas() {
        this.setPreferredSize(new Dimension(Width,Height));  //force the size of the canvas to be this size (basically prevent the title from taking some pixels)
        this.setBackground(Color.black);
        this.setLayout(null);  //Stop java from forcefeeding me with its default layouts, forcing it to use my layout



        gridd.BlocksInTheGrid.add(new Square_1x1(5,2));
        gridd.BlocksInTheGrid.add(new Square_1x1(5,3));

    }

    public void LaunchGame(){ //Obvious brah
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() { //Chổ này là game loop
        double fps = 1000000000/Fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){              // this thing is basically just used to calculate how much time is a frame and update per frame
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / fps;
            lastTime = currentTime;
            if(delta > 1){
                update();
                repaint();
                delta--;
            }
        }

    }

    public void update(){
        gridd.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gridd.Draw(g,Width,Height);
    }
}