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
        Random r = new Random();
        int type = r.nextInt(7);
        if(type==0)Current_Block = new L_Piece();
        if(type==1)Current_Block = new Rev_L_Piece();
        if(type==2)Current_Block = new Z_Piece();
        if(type==3)Current_Block = new S_Piece();
        if(type==4)Current_Block = new T_Piece();
        if(type==5)Current_Block = new Square_Piece();
        if(type==6)Current_Block = new I_Piece();

        Current_Block.x = Columns/2-1;
        Current_Block.y = 0;
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
