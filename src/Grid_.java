import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Grid_ {
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

        Collections.sort(BlocksInTheGrid, (a, b) -> b.y - a.y); //Prevent top cube from phasing onto the lowest cube by sorting and processing the lowest cube first

        timer++;
        for(Block b : BlocksInTheGrid){
            if(timer == 30 && b.status){
                if(grid[b.LowestPoint+1][b.x] == -1 || grid[b.LowestPoint+1][b.x] == 1)b.status = false;
                else {
                    b.y++;
                    b.LowestPoint++;
                    b.update();
                }
            }
            grid = b.SetOnesInGrid(grid, b.status);
        }
        if(timer==30)timer = 0;
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
    }
}