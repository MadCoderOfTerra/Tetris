import java.awt.*;

public class Grid_ {
    public int x, y;
    public int[][] grid = new int[20][10];
    public int[][] gridBackground = grid;
    public TetrisBlock Current_Block;

    public int Rows;
    public int Columns;
    public int CellSize;
    public Color[] ColorToChoose = {Color.red, Color.blue, Color.GREEN};


    public Grid_(int GameCanvasWidth, int GameCanvasHeight){
        Columns = grid[0].length;
        Rows = grid.length;
        CellSize = 25;
        reset();

        x = GameCanvasWidth/2 - CellSize * (Columns/2);
        y = GameCanvasHeight/2 - CellSize * (Rows/2);
    }

    public void reset(){
        for(int i=0;i<Rows;i++){
            for(int j=0;j<Columns;j++){
                grid[i][j] = -1;
            }
        }
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
