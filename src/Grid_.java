import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Grid_ {
    public int x, y;
    public int[][] grid = new int[20][10];
    public int[][] gridBackground = new int[20][10];
    public TetrisBlock Current_Block;
    public ArrayList<TetrisBlock> Background_Blocks = new ArrayList<>();

    public int Rows;
    public int Columns;
    public int CellSize;
    public Color[] ColorToChoose = {Color.red, Color.blue, Color.GREEN};

    public TetrisBlock Next_Block;
    public TetrisBlock Hold_Block = null;
    public boolean canHold = true;


    public Grid_(int GameCanvasWidth, int GameCanvasHeight){
        Columns = grid[0].length;
        Rows = grid.length;
        CellSize = 25;

        for(int i=0;i<Rows;i++){
            for(int j=0;j<Columns;j++){
                grid[i][j] = -1;
                gridBackground[i][j] = -1;
            }
        }

        x = GameCanvasWidth/2 - CellSize * (Columns/2);
        y = GameCanvasHeight/2 - CellSize * (Rows/2);
        
        // Generate the first Next block right away
        Next_Block = generateRandomBlock(); 
    }

    private TetrisBlock generateRandomBlock() {
        Random r = new Random();
        int type = r.nextInt(7);
        if(type==0) return new L_Piece();
        if(type==1) return new Rev_L_Piece();
        if(type==2) return new Z_Piece();
        if(type==3) return new S_Piece();
        if(type==4) return new T_Piece();
        if(type==5) return new Square_Piece();
        return new I_Piece();
    }

    public void holdPiece() {
        if (!canHold) return; // Only allow one hold per piece drop

        if (Hold_Block == null) {
            // First time holding: put current in hold, spawn next
            Hold_Block = Current_Block;
            spawnBlock();
        } else {
            // Swap current and hold
            TetrisBlock temp = Current_Block;
            Current_Block = Hold_Block;
            
            // Reset the swapped block's position
            Current_Block.x = Columns/2-1;
            Current_Block.y = 0;
            
            Hold_Block = temp;
        }
        canHold = false; // Lock holding until the block is placed
    }

    public void reset(){
        for(int i=0;i<Rows;i++){
            for(int j=0;j<Columns;j++){
                grid[i][j] = gridBackground[i][j];
            }
        }
    }

    public void moveToBackground() {
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] != -1) gridBackground[i][j] = grid[i][j];
            }
        }
    }

    public void spawnBlock(){
        Current_Block = Next_Block; // Take the next block
        Current_Block.x = Columns/2-1;
        Current_Block.y = 0;
        
        Next_Block = generateRandomBlock(); // Generate a new one for the queue
        canHold = true; // Reset the player's ability to hold for this turn
    }



    public void Draw(Graphics g, int w, int h){
        drawBackground(g);
        for(int i = 0; i < Rows; i++){
            for(int j = 0;j < Columns; j++){
                if(grid[i][j] != -1){
                    drawSquare(g,ColorToChoose[grid[i][j]],x + j*CellSize, y + i*CellSize);
                }
            }
        }
    }

    public void drawSquare(Graphics g, Color color, int x, int y) {
        g.setColor(color);
        g.fillRect(x, y, CellSize, CellSize);
        g.setColor(Color.black);
        g.drawRect(x, y, CellSize, CellSize);
    }

    public void drawBackground(Graphics g){
        g.setColor(Color.black);
        g.fillRect(x - CellSize, y - CellSize, CellSize * Columns + CellSize*2, CellSize * Rows + CellSize*2);
        g.setColor(Color.WHITE);
        g.fillRect(x, y, CellSize * Columns, CellSize * Rows);
    }
}
