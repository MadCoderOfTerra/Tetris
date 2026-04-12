import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

        this.setFocusable(true); //Make the canvas focusable so it can receive key events
        this.addKeyListener(new KeyAdapter() { //Add a key listener to the canvas to listen for key events
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                for (Block b : gridd.BlocksInTheGrid) {
                    if (b.status) {//Control block dropping
                        
                        // 1. XOAY: Mũi tên LÊN hoặc phím W
                        if (e.getKeyCode()  == KeyEvent.VK_UP || e.getKeyCode()  == KeyEvent.VK_W) {
                            b.rotate(gridd.grid);
                        }
                        
                        // 2. SANG TRÁI: Mũi tên TRÁI hoặc phím A
                        else if (e.getKeyCode()  == KeyEvent.VK_LEFT || e.getKeyCode()  == KeyEvent.VK_A) {
                            b.x--; // Thử dịch sang trái 1 ô
                            // Nếu kẹt tường hoặc đụng gạch khác thì lùi lại vị trí cũ
                            if (!b.isValidPosition(gridd.grid)) b.x++; 
                        }
                        
                        // 3. SANG PHẢI: Mũi tên PHẢI hoặc phím D
                        else if (e.getKeyCode()  == KeyEvent.VK_RIGHT || e.getKeyCode()  == KeyEvent.VK_D) {
                            b.x++; // Thử dịch sang phải 1 ô
                            if (!b.isValidPosition(gridd.grid)) b.x--;
                        }
                        
                        // 4. RƠI NHANH (Soft Drop): Mũi tên XUỐNG hoặc phím S
                        else if (e.getKeyCode()  == KeyEvent.VK_DOWN || e.getKeyCode()  == KeyEvent.VK_S) {
                            b.y++; // Thử rơi xuống 1 ô
                            if (!b.isValidPosition(gridd.grid)) b.y--;
                        }
                        
                        break; 
                    }
                }
                repaint(); // Cập nhật lại hình ảnh ngay lập tức sau khi bấm
            }
        });


        //gridd.BlocksInTheGrid.add(new Square_1x1(3,1));
        //gridd.BlocksInTheGrid.add(new Square_2x2(5,2));
        gridd.BlocksInTheGrid.add(new T_block(7,1));

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