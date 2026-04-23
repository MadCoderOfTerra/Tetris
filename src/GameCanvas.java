import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import static java.lang.Integer.MAX_VALUE;

public class GameCanvas extends JPanel implements Runnable {
    public int Width = 800;
    public int Height = 800;
    public int gameSpeed = 500;
    public boolean paused = false;
    public boolean running = true;
    public boolean gameOver = false;

    public JLabel gameOverLabel = new JLabel("GAME OVER", SwingConstants.CENTER);
    public JLabel ScoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
    public JButton pauseBt = new JButton("Pause");

    Grid_ Grid = new Grid_(Width, Height);
    Thread gameThread;

    public GameCanvas() {
        setPreferredSize(new Dimension(Width,Height));    //force the size of the canvas to be this size (basically prevent the title from taking some pixels)
        setBackground(Color.WHITE);
        setLayout(null);                                  //if this is not here then .setbounds() is useless
        setBorder(BorderFactory.createLineBorder(Color.BLACK));



        gameOverLabel.setBounds(400-100,0,200,200);
        gameOverLabel.setFont(new Font("Times new Roman", Font.BOLD, 30));

        ScoreLabel.setBounds(550,100,100,100);
        ScoreLabel.setFont(new Font("Times new Roman", Font.BOLD, 20));

        pauseBt.setBounds(650,130,100,30);
        pauseBt.addActionListener(e -> {
            if(!paused){
                paused = true;
                pauseBt.setText("Resume");
            } else {
                paused = false;
                pauseBt.setText("Pause");
            }
        });
        pauseBt.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");



        this.add(ScoreLabel);
        this.add(pauseBt);

        Grid.spawnBlock();
    }

    int score = 0;

    @Override
    public void run() { //This is the game loop don't touch it pls
        while(running){
            update();
            repaint();

            try {
                Thread.sleep(gameSpeed);
                while(paused){
                    Thread.sleep(1);
                };
            } catch (InterruptedException var2) {
                return;
            }
            score += clearRows();
            ScoreLabel.setText("Score: " + String.valueOf(score));

        }
    }

    public void update(){
        Grid.reset();
        if(Grid.Current_Block.checkCollisionUnder(Grid.gridBackground))Grid.Current_Block.moveDown();
        else {
            Grid.grid = Grid.Current_Block.setBlockInGrid(Grid.grid);
            Grid.moveToBackground();
            Grid.spawnBlock();
            if(!checkSpawn(Grid.gridBackground)){
                running = false;
                this.add(gameOverLabel); //add this label to gameCanvas
                gameOver = true;
            }
        }
        Grid.grid = Grid.Current_Block.setBlockInGrid(Grid.grid);
    }

    public boolean checkSpawn(int[][] grid){
        int y = Grid.Current_Block.y;
        int x = Grid.Current_Block.x;
        for(int row = y; row < y + Grid.Current_Block.getHeight(); row++){
            for(int col = x; col < x + Grid.Current_Block.getWidth(); col++){
                if(grid[row][col] != -1 && Grid.Current_Block.Shape[row-y][col-x] != -1) return false;
            }
        }
        return true;
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
        if(!running || paused) return;
        if(Grid.Current_Block.checkCollisionRight(Grid.gridBackground)) Grid.Current_Block.moveRight();
        refreshGrid();
    }

    public void moveBlockLeft(){
        if(!running || paused) return;
        if(Grid.Current_Block.checkCollisionLeft(Grid.gridBackground)) Grid.Current_Block.moveLeft();
        refreshGrid();
    }
    public void moveBlockDown(){
        if(!running || paused) return;
        if(Grid.Current_Block.checkCollisionUnder(Grid.gridBackground)) Grid.Current_Block.moveDown();
        refreshGrid();
    }

    public void rotateBlock(){
        if(!running || paused) return;
        if(Grid.Current_Block.checkCollisionRotate(Grid.gridBackground)) Grid.Current_Block.Rotate();
        refreshGrid();
    }

    public void dropBlock(){
        if(!running || paused) return;
        Grid.reset();
        Grid.Current_Block.dropDown(Grid.gridBackground);
        Grid.grid = Grid.Current_Block.setBlockInGrid(Grid.grid);
        Grid.moveToBackground();
        Grid.spawnBlock();
        refreshGrid();
    }

    public int clearRows() {
        int linesCleared = 0;

        for(int row = Grid.Rows - 1; row >= 0; row--) {
            boolean LineFilled = true;

            for(int col = 0; col < Grid.Columns; col++) {
                if (Grid.gridBackground[row][col] == -1) {
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