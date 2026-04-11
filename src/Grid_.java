import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class Grid_ {
    public int score = 0;
    public int level = 1;
    public int linesCleared = 0;
    public int fallSpeed = 30;


    public int[][] grid = new int[22][12];
    public ArrayList<Block> BlocksInTheGrid = new ArrayList<Block>();

    int timer = 0;

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
        reset();

        Collections.sort(BlocksInTheGrid, (a, b) -> b.y - a.y); //Prevent top cube from phasing onto the lowest cube

        timer++;
        boolean needToCheckLines = false;

        for(Block b : BlocksInTheGrid){
            if(timer >= fallSpeed && b.status){
                if(!b.canMoveDown(grid)){
                    b.status = false;
                    needToCheckLines = true;
                }
                else b.y++;
            }
            grid = b.SetOnesInGrid(grid, b.status);
        }

        if (needToCheckLines) checkandClearLines();

        if(timer==fallSpeed)timer = 0;
    }

    public void addScore(int lines) {
        switch (lines) {
            case 1: score += 100 * level; break;
            case 2: score += 300 * level; break;
            case 3: score += 500 * level; break;
            case 4: score += 800 * level; break;
        }
        System.out.println("Score: " + score); // Tạm in ra console để test
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

                Iterator<Block> iterator = BlocksInTheGrid.iterator();
            
                while (iterator.hasNext()) {
                    Block b = iterator.next();

                    if (b.y == i) iterator.remove();

                    else if (b.y < i) b.y++;
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
    }
}