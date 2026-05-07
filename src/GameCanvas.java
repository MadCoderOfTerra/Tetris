import java.util.prefs.Preferences;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class GameCanvas extends JPanel implements Runnable {
    public int Width;
    public int Height;
    public boolean running = true;
    public boolean isPaused = false;
    public boolean isGameOver = false; 
    Grid_ Grid;
    Thread gameThread;

    public GameCanvas() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Width = (int) screenSize.getWidth();
        Height = (int) screenSize.getHeight();
        Grid = new Grid_(Width, Height);
        setPreferredSize(new Dimension(Width,Height));
        setLayout(null); 
        setFocusable(true);
        initControls();
        Grid.spawnBlock();
    }

    public void drawGhostPiece(Graphics g) {
        if (Grid.Current_Block == null) return;
        int originalY = Grid.Current_Block.y;
        while(Grid.Current_Block.checkCollisionUnder(Grid.gridBackground)) {
            Grid.Current_Block.y++;
        }
        int ghostY = Grid.Current_Block.y;
        for(int r = 0; r < Grid.Current_Block.getHeight(); r++){
            for(int c = 0; c < Grid.Current_Block.getWidth(); c++){
                if(Grid.Current_Block.Shape[r][c] != -1){
                    int drawX = Grid.x + ((Grid.Current_Block.x + c) * Grid.CellSize);
                    int drawY = Grid.y + ((ghostY + r) * Grid.CellSize);
                    int colorIndex = Grid.Current_Block.Shape[r][c];
                    Color baseColor = Grid.ColorToChoose[colorIndex];
                    g.setColor(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 80));
                    g.fillRect(drawX, drawY, Grid.CellSize, Grid.CellSize);
                    g.setColor(new Color(255, 255, 255, 150));
                    g.drawRect(drawX, drawY, Grid.CellSize, Grid.CellSize);
                }
            }
        }
        Grid.Current_Block.y = originalY;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(25, 25, 25));
        g.fillRect(0, 0, Width, Height);
        Grid.Draw(g, Width, Height);
        drawGhostPiece(g); 
        drawHUD(g); 
        g.setColor(Color.LIGHT_GRAY);
        int controlFont = (int)(Grid.CellSize * 0.6);
        g.setFont(new Font("Monospaced", Font.PLAIN, controlFont));
        int textY = Height - (Grid.CellSize * 5);
        g.drawString("CONTROLS:", 20, textY);
        g.drawString("← / →  : Move", 20, textY + controlFont * 2);
        g.drawString("↑      : Rotate", 20, textY + controlFont * 3);
        g.drawString("↓      : Soft Drop", 20, textY + controlFont * 4);
        g.drawString("SPACE  : Hard Drop", 20, textY + controlFont * 5);
        g.drawString("C      : Hold", 20, textY + controlFont * 6);
        if (isGameOver) {
            g.setColor(new Color(150, 0, 0, 180)); 
            g.fillRect(0, 0, Width, Height);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, Grid.CellSize * 2));
            g.drawString("GAME OVER", (Width / 2) - (Grid.CellSize * 4), Height / 2 - Grid.CellSize);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Monospaced", Font.BOLD, (int)(Grid.CellSize * 1.5)));
            g.drawString("FINAL SCORE: " + score, (Width / 2) - (Grid.CellSize * 5), Height / 2 + (Grid.CellSize));
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, (int)(Grid.CellSize * 0.8)));
            g.drawString("Press [Q] to Quit to Menu", (Width / 2) - (Grid.CellSize * 5), Height / 2 + Grid.CellSize * 3);
        } else if (isPaused) {
            g.setColor(new Color(0, 0, 0, 180)); 
            g.fillRect(0, 0, Width, Height);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, Grid.CellSize * 2));
            g.drawString("PAUSED", (Width / 2) - (Grid.CellSize * 3), Height / 2 - Grid.CellSize);
            g.setFont(new Font("Monospaced", Font.PLAIN, (int)(Grid.CellSize * 0.8)));
            g.drawString("Press [P] or [ESC] to Continue", (Width / 2) - (Grid.CellSize * 6), Height / 2 + Grid.CellSize);
            g.drawString("Press [Q] to Quit to Menu", (Width / 2) - (Grid.CellSize * 5), Height / 2 + Grid.CellSize * 2);
        }
    }

    public void LaunchGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    int score = 0;
    @Override
    public void run() {
        while(running){
            if (!isPaused && !isGameOver) {
                update();
            }
            repaint();
            try { Thread.sleep(500); } catch (InterruptedException var2) { return; }
            if (!isPaused && !isGameOver) {
                score += clearRows();
            }
        }
    }

    private void gameOver() {
        running = false;
        isGameOver = true; 
        Preferences prefs = Preferences.userNodeForPackage(GameCanvas.class);
        int currentHigh = prefs.getInt("HighScore", 0);
        if(score > currentHigh) prefs.putInt("HighScore", score);
        Main.gameMusic.stop();
        Main.gameOverMusic.play();
        repaint(); 
    }

    public void update() {
        if (!running) return;
        Grid.reset();
        if(Grid.Current_Block.checkCollisionUnder(Grid.gridBackground)){
            Grid.Current_Block.moveDown();
        } else {
            Main.blockLandSound.play();
            
            Grid.grid = Grid.Current_Block.setBlockInGrid(Grid.grid);
            Grid.moveToBackground();
            Grid.spawnBlock();
            if (!checkSpawn(Grid.gridBackground)) {
                gameOver();
                return;
            }
        }
        Grid.grid = Grid.Current_Block.setBlockInGrid(Grid.grid);
    }

    public boolean checkSpawn(int[][] grid) {
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
        im.put(KeyStroke.getKeyStroke("P"), "pause");
        im.put(KeyStroke.getKeyStroke("ESCAPE"), "pause");
        im.put(KeyStroke.getKeyStroke("Q"), "quit");

        am.put("pause", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (running && !isGameOver) {
                    isPaused = !isPaused;
                    if (isPaused) {
                        Main.gameMusic.pause();
                    } else {
                        Main.gameMusic.resume();
                    }
                    repaint();
                }
            }
        });

        am.put("quit", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                if (isPaused || isGameOver) {
                    Main.menuClickSound.play();
                    running = false; 
                    Main.gameMusic.stop();
                    Main.gameOverMusic.stop();
                    Main.menuMusic.playLoop();
                    
                    Container parent = getParent();
                    if (parent != null && parent.getLayout() instanceof CardLayout) {
                        CardLayout cl = (CardLayout) parent.getLayout();
                        cl.show(parent, "Menu");
                    }
                }
            }
        });

        am.put("right", new AbstractAction() { public void actionPerformed(ActionEvent e) { moveBlockRight(); }});
        am.put("left", new AbstractAction() { public void actionPerformed(ActionEvent e) { moveBlockLeft(); }});
        am.put("up", new AbstractAction() { public void actionPerformed(ActionEvent e) { rotateBlock(); }});
        am.put("down", new AbstractAction() { public void actionPerformed(ActionEvent e) { moveBlockDown(); }});
        am.put("space", new AbstractAction() { public void actionPerformed(ActionEvent e) { dropBlock(); }});
        am.put("hold", new AbstractAction() { public void actionPerformed(ActionEvent e) { triggerHold(); }});
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
        if(Grid.Current_Block.checkCollisionRotate(Grid.gridBackground)) {
            Grid.Current_Block.Rotate();
            Main.rotationSound.play();
        }
        refreshGrid();
    }

    public void dropBlock(){
        if (!running || isGameOver) return;
        Grid.reset();
        Grid.Current_Block.dropDown(Grid.gridBackground);
        Main.blockLandSound.play();
        Grid.grid = Grid.Current_Block.setBlockInGrid(Grid.grid);
        Grid.moveToBackground();
        Grid.spawnBlock();
        if (!checkSpawn(Grid.gridBackground)) {
            gameOver();
            return;
        }
        refreshGrid();
    }

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
        if (linesCleared > 0) {
            Main.lineClearSound.play();
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
        int fontSize = (int)(Grid.CellSize * 0.8);
        g.setFont(new Font("Monospaced", Font.BOLD, fontSize));
        int boxSize = Grid.CellSize * 5;
        int gap = Grid.CellSize * 2;
        int topY = Grid.y + Grid.CellSize;
        int rightSideX = Grid.x + (Grid.Columns * Grid.CellSize) + gap; 
        g.drawString("SCORE: " + score, rightSideX, topY);
        g.drawString("NEXT", rightSideX, topY + (Grid.CellSize * 3));
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(rightSideX, topY + (Grid.CellSize * 4), boxSize, boxSize); 
        g.setColor(Color.WHITE); 
        g.drawRect(rightSideX, topY + (Grid.CellSize * 4), boxSize, boxSize); 
        drawBlockPreview(g, Grid.Next_Block, rightSideX + Grid.CellSize, topY + (Grid.CellSize * 5));
        int leftSideX = Grid.x - boxSize - gap; 
        g.drawString("HOLD", leftSideX, topY + (Grid.CellSize * 3));
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(leftSideX, topY + (Grid.CellSize * 4), boxSize, boxSize); 
        g.setColor(Color.WHITE);
        g.drawRect(leftSideX, topY + (Grid.CellSize * 4), boxSize, boxSize); 
        if (Grid.Hold_Block != null) {
            drawBlockPreview(g, Grid.Hold_Block, leftSideX + Grid.CellSize, topY + (Grid.CellSize * 5));
        }
    }

    public void drawBlockPreview(Graphics g, TetrisBlock block, int startX, int startY) {
        if (block == null) return;
        int previewSize = (int)(Grid.CellSize * 0.8);
        for (int r = 0; r < block.Shape.length; r++) {
            for (int c = 0; c < block.Shape[0].length; c++) {
                if (block.Shape[r][c] != -1) {
                    int colorIndex = block.Shape[r][c];
                    if(colorIndex < 0 || colorIndex >= Grid.ColorToChoose.length) colorIndex = 0;
                    g.setColor(Grid.ColorToChoose[colorIndex]);
                    g.fillRect(startX + (c * previewSize), startY + (r * previewSize), previewSize, previewSize);
                    g.setColor(Color.WHITE);
                    g.drawRect(startX + (c * previewSize), startY + (r * previewSize), previewSize, previewSize);
                }
            }
        }
    }

    public void triggerHold() {
        if (isGameOver) return; 
        Grid.reset();
        Grid.holdPiece();
        Grid.grid = Grid.Current_Block.setBlockInGrid(Grid.grid);
        repaint();
    }

    public void resetGame() {
        Grid = new Grid_(Width, Height); 
        score = 0;
        running = true;
        isGameOver = false; 
        Main.gameOverMusic.stop();
        Grid.spawnBlock();
        repaint();
    }
}