import java.awt.*;
import java.util.ArrayList;

public class Grid_ {
    public int x, y;
    public int[][] grid = new int[25][12];
    public int[][] gridBackground = new int[25][12];
    public TetrisBlock Current_Block;
    public int Rows;
    public int Columns;
    public int CellSize;

    public Color[] ColorToChoose = {Color.CYAN, Color.YELLOW, new Color(128, 0, 128), Color.GREEN, Color.RED, Color.BLUE, Color.ORANGE};
    public TetrisBlock Next_Block;
    public TetrisBlock Hold_Block = null;
    public boolean canHold = true;
    private ArrayList<Integer> bag = new ArrayList<>();

    public Grid_(int GameCanvasWidth, int GameCanvasHeight) {
        Columns = grid[0].length;
        Rows = grid.length;
        CellSize = GameCanvasHeight / 30;
        for(int i=0;i<Rows;i++){
            for(int j=0;j<Columns;j++){
                grid[i][j] = -1;
                gridBackground[i][j] = -1;
            }
        }
        x = GameCanvasWidth/2 - CellSize * (Columns/2);
        y = GameCanvasHeight/2 - CellSize * (Rows/2);
        Next_Block = generateRandomBlock(); 
    }


    private TetrisBlock generateRandomBlock() {
        if (bag.isEmpty()) {
            for (int i = 0; i < 7; i++) bag.add(i);
            java.util.Collections.shuffle(bag);
        }
        int type = bag.remove(0);
        if(type==0) return new L_Shape();
        if(type==1) return new J_Shape();
        if(type==2) return new Z_Shape();
        if(type==3) return new S_Shape();
        if(type==4) return new T_Shape();
        if(type==5) return new O_Shape();
        return new I_Shape();
    }

    public void holdPiece() {
        if (!canHold) return;
        if (Hold_Block == null) {
            Hold_Block = Current_Block;
            spawnBlock();
        } else {
            TetrisBlock temp = Current_Block;
            Current_Block = Hold_Block;
            Current_Block.x = Columns/2-1;
            Current_Block.y = 0;
            Hold_Block = temp;
        }
        canHold = false; 
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
        Current_Block = Next_Block; 
        Current_Block.x = Columns/2-1;
        Current_Block.y = 0;
        Next_Block = generateRandomBlock(); 
        canHold = true; 
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

    private void drawSquare(Graphics g, Color color, int x, int y) {
        g.setColor(color);
        g.fillRect(x, y, CellSize, CellSize);
        g.setColor(Color.black);
        g.drawRect(x, y, CellSize, CellSize);
    }

    private void drawBackground(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(x - 4, y - 4, (CellSize * Columns) + 8, (CellSize * Rows) + 8);
        for (int r = 0; r < Rows; r++) {
            for (int c = 0; c < Columns; c++) {
                int cellX = x + (c * CellSize);
                int cellY = y + (r * CellSize);
                g.setColor(new Color(60, 60, 60)); 
                g.fillRect(cellX, cellY, CellSize, CellSize);
                g.setColor(new Color(20, 20, 20)); 
                g.fillRect(cellX + 2, cellY + 2, CellSize - 2, CellSize - 2);
                g.setColor(new Color(40, 40, 40)); 
                g.fillRect(cellX + 2, cellY + 2, CellSize - 4, CellSize - 4);
                g.setColor(Color.BLACK);
                g.drawRect(cellX, cellY, CellSize, CellSize);
            }
        }
    }
}