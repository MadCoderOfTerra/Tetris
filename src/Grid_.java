import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;


public class Grid_ {
    public int score = 0;
    public int level = 1;
    public int linesCleared = 0;
    public int fallSpeed = 30;


    public boolean isGameOver = false; 
    public Block nextBlock;

    public int[][] grid = new int[22][12];
    public ArrayList<Block> BlocksInTheGrid = new ArrayList<Block>();

    int timer = 0;

    private Block createRandomBlock() {
        Random rand = new Random();
        int type = rand.nextInt(7); // 7 types of blocks
        int startX = 4;
        int startY = 1;

        switch (type) {
            case 0: return new T_block(startX, startY);
            case 1: return new L_block(startX, startY);
            case 2: return new Square_2x2(startX, startY);
            case 3: return new J_block(startX, startY);
            case 4: return new I_block(startX, startY);
            case 5: return new S_block(startX, startY);
            case 6: return new Z_block(startX, startY);
            default: return new T_block(startX, startY);
        }
    }

    public void spawnRandomBlock() {
        if (nextBlock == null) nextBlock = createRandomBlock();

        Block currentBlock = nextBlock;
        BlocksInTheGrid.add(currentBlock);

        //Check Game Over status here

        if (!currentBlock.isValidPosition(grid)) {
            isGameOver =  true;
            currentBlock.status = false;
        }

        nextBlock = createRandomBlock();
    }

    public Grid_(){
        reset();
    }

    public void reset(){
        for(int i=0;i<22;i++){
            for(int j=0;j<12;j++){
                if(j==0 || j==11 || i==0 || i==21) grid[i][j] = -1;
                else grid[i][j] = 0;
            }
        }
    }

    public void update(){
        if (isGameOver) return;

        // 1. Remove dropping block shadow and keep block 1
        for(int i=1; i<=20; i++) {
            for(int j=1; j<=10; j++) {
                if(grid[i][j] == 2) grid[i][j] = 0;
            }
        }

        timer++;
        boolean needToCheckLines = false;

        // 2. Use Iterator to check and remove
        Iterator<Block> iterator = BlocksInTheGrid.iterator();
        while(iterator.hasNext()){
            Block b = iterator.next();
            
            if(timer >= fallSpeed && b.status){
                if(!b.canMoveDown(grid)){
                    b.status = false;
                    needToCheckLines = true;
                    
                    // Print and set constant location for block 
                    b.SetOnesInGrid(grid, false); 
                    
                    // Remove object because of cleared row
                    iterator.remove(); 
                }
                else b.y++;
            }
            
            // Draw block 2
            if (b.status) {
                b.SetOnesInGrid(grid, true);
            }
        }

        if (needToCheckLines){
            checkandClearLines();
            spawnRandomBlock();
        }

        if(timer==fallSpeed)timer = 0;
    }

    public void addScore(int lines) {
        switch (lines) {
            case 1: score += 100 * level; break;
            case 2: score += 300 * level; break;
            case 3: score += 500 * level; break;
            case 4: score += 800 * level; break;
        }
        System.out.println("Score: " + score); // Print in console
    }

    public void checkLevelUp(int lines) {
        linesCleared += lines;
        if (linesCleared >= level * 10) {
            level++;
            fallSpeed = Math.max(5, 30 - (level * 3));
            System.out.println("Level Up! Level: " + level + ", Speed: " + fallSpeed);
        }
    }


    public void checkandClearLines(){
        int linesClearedThisTurn = 0;

        for (int i = 20; i >= 1; i--) {
            boolean rowisFull = true;

            for (int j = 1; j <= 10; j++) {
                if (grid[i][j] == 0 || grid[i][j] == 2) {
                    rowisFull = false;
                    break;
                }
            }

            if (rowisFull) {
                linesClearedThisTurn++;

                for (int r = i; r > 1; r--) {
                    for (int c = 1; c <= 10; c++) {
                        grid[r][c] = grid[r-1][c];
                    }
                }
                
                // Clear up row
                for (int c = 1; c <= 10; c++) {
                    grid[1][c] = 0;
                }

                i++; 
            }
        }

        if (linesClearedThisTurn > 0) {
            addScore(linesClearedThisTurn);
            checkLevelUp(linesClearedThisTurn);
        }
    }



    public void Draw(Graphics g, int w, int h){
        for(int i=0;i<22;i++){
            for(int j=0;j<12;j++){
                if(grid[i][j] == 0)continue;
                if(grid[i][j] == 1 || grid[i][j] == 2){
                    g.setColor(Color.red);
                    g.fillRect(w/2 + 25*(j-6)+1,h/2 + 25*(i-11)+1,23,23);
                }
                else {
                    g.setColor(Color.white);
                    g.fillRect(w/2 + 25*(j-6),h/2 + 25*(i-11),25,25);
                }
            }
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times New Roman", Font.BOLD, 20)); 
    
        g.drawString("Score: " + score, 50, 50);
        g.drawString("Level: " + level, 50, 80);
        g.drawString("Lines: " + linesCleared, 50, 110);


        // GAME OVER SCREEN
        if (isGameOver) {
            
            g.setColor(new Color(0, 0, 0, 150)); 
            g.fillRect(0, 0, w, h);

            // Draw GAME OVER
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            g.drawString("GAME OVER", w/2 - 170, h/2);

            // LAST POINT
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Final Score: " + score, w/2 - 100, h/2 + 50);
        }    
    }
}