import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.plaf.ColorUIResource;
import java.nio.file.FileAlreadyExistsException;
import java.util.*;

// 0. I - Straight line of 4
// 1. O - Square
// 2. T - T shape
// 3. S_Left - S with a hook to the left
// 4. Z_Right - Z with a hook to the right
// 5. J_Left - Long left hook
// 6. L_Right - Long right hook

abstract class Block {
    int x = 0, y = 0, timer = 0;
    public abstract void Draw(Graphics g);
}

class I_Piece extends Block {
    public I_Piece(int x, int y){
        this.x = x;
        this.y = y;
    }

    int Orientation = 1; //1 is straight up & 2 is lying down

    @Override
    public void Draw(Graphics g){
        //g.drawRect(x,y);
    }
}

class Square_1x1 extends Block {

    public Square_1x1(){};

    public Square_1x1(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void Draw(Graphics g){
        g.fillRect(x,y,25,25);
    }
}

class Grid {
    public ArrayList<Block> Existing_Blocks = new ArrayList<>();

}

//----------------------------------------------------------------------------------------------------------------------

public class Main {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setTitle("Tetris Made by Anderson The First");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ArrayList<Block> Existing_Blocks = new ArrayList<Block>();
        Existing_Blocks.add(new Square_1x1());



        GameCanvas canvas = new GameCanvas(Existing_Blocks);
        frame.add(canvas);
        frame.pack(); //cái này biến dimension của canvas thành dimension của cái window


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        canvas.LaunchGame();

    }
}

//----------------------------------------------------------------------------------------------------------------------


class GameCanvas extends JPanel implements Runnable{

    public int Width = 800;
    public int Height = 800;
    public int Fps = 144;

    ArrayList<Block> Existing_Blocks = new ArrayList<Block>();

    Thread gameThread; // this thing is used to run the gameloop


    public GameCanvas(ArrayList<Block> Existing_Blocks) {
        this.Existing_Blocks = Existing_Blocks;
        this.setPreferredSize(new Dimension(Width,Height));
        this.setBackground(Color.black);
        this.setLayout(null);
        for (Block b : Existing_Blocks){
            b.x = Width/2;
            b.y = Height/2;
        }
    }



    public void LaunchGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() { //Chổ này là game loop
        double fps = 1000000000/Fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){
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
        for(Block b : Existing_Blocks){
            if(b.timer==50)b.timer = 0;
            if(b.timer==0)b.y += 25;
            b.timer++;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.red);
        for(Block b : Existing_Blocks){
            b.Draw(g);
        }
    }
}

/*
@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


    }





        int w = 25;
        int h = 25;

        g.drawRect(Width/2+(-5*w)-1, Height/2 + (-10*h)-1,w*10+1,h*20+1);

        int c = 0;


        for(int i=-10;i<10;i++){
            for(int j=-5;j<5;j++){
                if(c%2==0) g.setColor(Color.red);
                else g.setColor(Color.blue);
                c++;
                g.fillRect(Width/2+j*w, Height/2+i*h,w,h);
            }
            c++;
        }

         */





/*

don't address this just some notes

Render the box 10x20
Render a square
Make that square fall each seconds
Stop it when touching the bottom
allow for real time input
allow real time movement
allow rotation
Create other shapes and testing its collision with the bottom wall
Limit them inside left and right wall
Allow for speeding to the bottom
(For now no collision between shapes yet) allow for random generated shapes and move them around with diff colors
Now check for collisions and stop them
Now check for row clearing and moving the squares down several blocks
Scoring system
Menu
LeaderBoard


-------------------------

Render box 10x20
Render & fall one square
Stop at bottom
Real time input + movement + wall limits
Rotation
Speed up (hard drop / soft drop)
Other shapes + colors
Bottom & wall collision for all shapes
Spawn new piece when one lands
Shape-to-shape collision
Row clearing
Game over
Scoring
Menu + Leaderboard
Ghost piece (bonus)
 */
