import java.awt.*;

public class Grid_ {
    int x, y;
    public int[][] grid = new int[20][10];
    public TetrisBlock Current_Block;

    private int Rows;
    private int Columns;
    private int CellSize;
    public Color[] ColorToChoose = {Color.red, Color.blue, Color.GREEN};


    public Grid_(int GameCanvasWidth, int GameCanvasHeight){

        Columns = grid[0].length;
        Rows = grid.length;
        CellSize = 25;

        reset();

        this.x = GameCanvasWidth/2 - CellSize * (Columns/2);
        this.y = GameCanvasHeight/2 - CellSize * (Rows/2);
    }

    public void reset(){
        for(int i=0;i<Rows;i++){
            for(int j=0;j<Columns;j++){
                grid[i][j] = -1;
            }
        }
    }

    public void update(){
        grid = Current_Block.setBlockInGrid(grid);
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
}



/*public void DrawCurrent_Block(Graphics g) {
        int h = Current_Block.getHeight();
        int w = Current_Block.getWidth();
        Color c = Current_Block.color;
        int[][] shape = Current_Block.Shape;

        for(int row = 0; row < h; ++row) {
            for(int col = 0; col < w; ++col) {
                if (shape[row][col] == 1) {
                    int x = (Current_Block.x + col) * CellSize;
                    int y = (Current_Block.y + row) * CellSize;
                    drawSquare(g, c, x, y);
                }
            }
        }
    }*/
