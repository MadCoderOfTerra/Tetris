import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;


public class GameCanvas extends JPanel implements Runnable{
    public int Width = 800;
    public int Height = 800;
    public boolean running = true;

    private Image backgroundImage;

    Grid_ Grid = new Grid_(Width, Height);
    Thread gameThread;

    public GameCanvas() {
        setPreferredSize(new Dimension(Width,Height));
        setLayout(null); 
        
        // 1. ADD THIS LINE so the canvas is allowed to detect key presses!
        setFocusable(true); 

        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("../materials/download.jpg")); 
        } catch (IOException e) {
            System.out.println("Could not load background image!");
            e.printStackTrace();
        }

        // 2. MAKE SURE THIS IS HERE so your keys actually bind!
        initControls(); 

        Grid.spawnBlock();
    }

    int score = 0;

    @Override
    public void run() { //This is the game loop don't touch it pls
        while(running){
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

    private void gameOver() {
        running = false; // Stop the game loop thread
                
        // Save the high score
        Preferences prefs = Preferences.userNodeForPackage(GameCanvas.class);
        int currentHigh = prefs.getInt("HighScore", 0);
        if(score > currentHigh) prefs.putInt("HighScore", score);
        
        // Safely switch back to the UI thread to show the message and change screens
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Game Over! Your score: " + score);
            
            // Tell the parent container (MainApp's mainContainer) to show the Menu
            Container parent = getParent();
            if (parent != null && parent.getLayout() instanceof CardLayout) {
                CardLayout cl = (CardLayout) parent.getLayout();
                cl.show(parent, "Menu");
            }
        });
    }

    public void update(){
        if (!running) return; // Prevent updates if game is already over

        Grid.reset();
        if(Grid.Current_Block.checkCollisionUnder(Grid.gridBackground)){
            Grid.Current_Block.moveDown();
        } else {
            Grid.grid = Grid.Current_Block.setBlockInGrid(Grid.grid);
            Grid.moveToBackground();
            Grid.spawnBlock();
            
            if(!checkSpawn(Grid.gridBackground)){
                gameOver();
                return; // <--- THIS PREVENTS THE GLITCH! Stops drawing the new block.
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
        im.put(KeyStroke.getKeyStroke("C"), "hold");
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
        am.put("hold", new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            triggerHold();
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
        if (!running) return; // Prevent input if game is over

        Grid.reset();
        Grid.Current_Block.dropDown(Grid.gridBackground);
        Grid.grid = Grid.Current_Block.setBlockInGrid(Grid.grid);
        Grid.moveToBackground();
        Grid.spawnBlock();
        
        if(!checkSpawn(Grid.gridBackground)){
            gameOver();
            return; // Stops here, preventing the glitch!
        }
        
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

    public void drawHUD(Graphics g) {
        g.setColor(Color.WHITE); 
        g.setFont(new Font("Monospaced", Font.BOLD, 24));

        // --- RIGHT SIDE: Score & Next Block ---
        int rightSideX = Grid.x + (Grid.Columns * Grid.CellSize) + 50; 

        g.drawString("SCORE: " + score, rightSideX, 100);
        
        g.drawString("NEXT", rightSideX, 200);
        
        // 1. Fill the NEXT box with a dark background so it's not hidden
        g.setColor(new Color(0, 0, 0, 180)); // Semi-transparent black
        g.fillRect(rightSideX, 220, 120, 120); 
        // 2. Draw the white border on top
        g.setColor(Color.WHITE); 
        g.drawRect(rightSideX, 220, 120, 120); 
        
        drawBlockPreview(g, Grid.Next_Block, rightSideX + 20, 240); // Draw next block

        // --- LEFT SIDE: Hold Block ---
        int leftSideX = Grid.x - 170; 

        g.drawString("HOLD", leftSideX, 200);
        
        // 1. Fill the HOLD box with a dark background
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(leftSideX, 220, 120, 120); 
        // 2. Draw the white border on top
        g.setColor(Color.WHITE);
        g.drawRect(leftSideX, 220, 120, 120); 
        
        if (Grid.Hold_Block != null) {
            drawBlockPreview(g, Grid.Hold_Block, leftSideX + 20, 240); // Draw hold block
        }
    }

    public void drawBlockPreview(Graphics g, TetrisBlock block, int startX, int startY) {
        if (block == null) return;
        
        // Use a slightly smaller cell size (20 instead of 25) just for the UI so blocks fit inside the 120x120 box
        int previewSize = 20; 
        
        for (int r = 0; r < block.Shape.length; r++) {
            for (int c = 0; c < block.Shape[0].length; c++) {
                if (block.Shape[r][c] != -1) {
                    // Safe color check
                    int colorIndex = block.Shape[r][c];
                    if(colorIndex < 0 || colorIndex >= Grid.ColorToChoose.length) {
                        colorIndex = 0;
                    }
                    
                    g.setColor(Grid.ColorToChoose[colorIndex]);
                    g.fillRect(startX + (c * previewSize), startY + (r * previewSize), previewSize, previewSize);
                    
                    g.setColor(Color.WHITE); // White border for the preview blocks
                    g.drawRect(startX + (c * previewSize), startY + (r * previewSize), previewSize, previewSize);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, Width, Height, this);
        }
        
        // Draw the main game grid
        Grid.Draw(g, Width, Height);
        
        // Draw the UI over it
        drawHUD(g); 
    }

    public void LaunchGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void triggerHold() {
        Grid.reset();
        Grid.holdPiece();
        Grid.grid = Grid.Current_Block.setBlockInGrid(Grid.grid);
        repaint();
    }

    public void resetGame() {
        // Create a brand new grid to wipe away all old blocks and background pieces
        Grid = new Grid_(Width, Height); 
        score = 0;
        running = true; // Re-enable the game loop
        Grid.spawnBlock();
        repaint();
    }
}