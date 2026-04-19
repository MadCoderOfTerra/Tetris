import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;


public class GameCanvas extends JPanel implements Runnable{
    public int Width = 800;
    public int Height = 800;

    Grid_ Grid = new Grid_(Width, Height);
    Thread gameThread;

    public GameCanvas() {
        setPreferredSize(new Dimension(Width,Height));    //force the size of the canvas to be this size (basically prevent the title from taking some pixels)
        setBackground(Color.WHITE);
        setLayout(null);                                  //Stop java from forcefeeding me with its default layouts, forcing it to use my layout

        TetrisBlock b = new L_Piece();
        b.x = 2;
        b.y = 0;
        Grid.Current_Block = b;



    }

    int score = 0;

    @Override
    public void run() { //This is the game loop don't touch it pls
        while(true){

            update();
            repaint();

            try {
                Thread.sleep(500);
            } catch (InterruptedException var2) {
                return;
            }

            score += clearRows();

        }
    }

    public void update(){
        Grid.reset();
        if(Grid.Current_Block.checkCollisionUnder(Grid.gridBackground))Grid.Current_Block.moveDown();
        else {
            Grid.grid = Grid.Current_Block.setBlockInGrid(Grid.grid);
            Grid.moveToBackground();
            Grid.spawnBlock();

        }
        Grid.grid = Grid.Current_Block.setBlockInGrid(Grid.grid);
    }

    public void initControls() {
        InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();
        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        im.put(KeyStroke.getKeyStroke("LEFT"), "left");
        im.put(KeyStroke.getKeyStroke("UP"), "up");
        im.put(KeyStroke.getKeyStroke("DOWN"), "down");
        im.put(KeyStroke.getKeyStroke("SPACE"), "space");
        am.put("right", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                moveBlockRight();
            }
        });
        am.put("left", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                moveBlockLeft();
            }
        });
        am.put("up", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                rotateBlock();
            }
        });
        am.put("down", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                moveBlockDown();
            }
        });
        am.put("space", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                dropBlock();
            }
        });
    }

    public void refreshGrid(){
        Grid.reset();
        Grid.grid = Grid.Current_Block.setBlockInGrid(Grid.grid);
        repaint();
    }

    public void moveBlockRight(){
        if(Grid.Current_Block.checkCollisionRight(Grid.gridBackground)) Grid.Current_Block.moveRight();
        refreshGrid();
    }

    public void moveBlockLeft(){
        if(Grid.Current_Block.checkCollisionLeft(Grid.gridBackground)) Grid.Current_Block.moveLeft();
        refreshGrid();
    }
    public void moveBlockDown(){
        if(Grid.Current_Block.checkCollisionUnder(Grid.gridBackground)) Grid.Current_Block.moveDown();
        refreshGrid();
    }

    public void rotateBlock(){
        if(Grid.Current_Block.checkCollisionRotate(Grid.gridBackground)) Grid.Current_Block.Rotate();
        refreshGrid();
    }

    public void dropBlock(){
        Grid.reset();
        Grid.Current_Block.dropDown(Grid.gridBackground);
        Grid.grid = Grid.Current_Block.setBlockInGrid(Grid.grid);
        Grid.moveToBackground();
        Grid.spawnBlock();
        refreshGrid();
    }

    //-----------------------------------
    public int clearRows() {
        int linesCleared = 0;

        for(int row = Grid.Rows - 1; row >= 0; --row) {
            boolean LineFilled = true;

            for(int c = 0; c < Grid.Columns; ++c) {
                if (Grid.gridBackground[row][c] == -1) {
                    LineFilled = false;
                    break;
                }
            }

            if (LineFilled) {
                linesCleared++;
                clearLine(row);
                shiftDown(row);
                clearLine(0);
                row++;
                repaint();
            }
        }

        return linesCleared;
    }

    private void clearLine(int row) {
        for(int i = 0; i < Grid.Columns; ++i) {
            Grid.gridBackground[row][i] = -1;
        }
    }

    private void shiftDown(int row) {
        for(; row > 0; --row) {
            for(int col = 0; col < Grid.Columns; ++col) {
                Grid.gridBackground[row][col] = Grid.gridBackground[row - 1][col];
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Grid.Draw(g,Width,Height);
    }

    public void LaunchGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }
}