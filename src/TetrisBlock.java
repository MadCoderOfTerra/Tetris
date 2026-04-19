import java.awt.*;
import java.util.Random;

public abstract class TetrisBlock {
    public int[][] Shape;
    public int[][][] Shape_Rotation;
    public int x;
    public int y;
    public int CurrentOrientation;
    public int ColorToChooseID;
    public Color[] ColorToChoose = {Color.red, Color.blue, Color.GREEN};

    public TetrisBlock(int[][] Shape){
        this.Shape = Shape;
        Random r = new Random();
        ColorToChooseID = r.nextInt(ColorToChoose.length);
        CurrentOrientation = r.nextInt(4);
        resetColor();
        Find4Rotation();
        Rotate();
    }

    public void Find4Rotation(){
        Shape_Rotation = new int[4][][];

        for(int i = 0; i < 4; ++i) {
            int r = Shape[0].length;
            int c = Shape.length;
            Shape_Rotation[i] = new int[r][c];

            for(int y = 0; y < r; ++y) {
                for(int x = 0; x < c; ++x) {
                    Shape_Rotation[i][y][x] = Shape[c - x - 1][y];
                }
            }

            Shape = Shape_Rotation[i];
        }
    }

    public void resetColor(){
        for(int i = 0; i < Shape.length; i++){
            for(int j = 0; j < Shape[0].length; j++){
                if(Shape[i][j] != -1)Shape[i][j] = ColorToChooseID;
            }
        }
    }

    public int[][] setBlockInGrid(int[][] grid){
        int rows = grid.length;
        int columns = grid[0].length;
        for(int i = y; i < y + Shape.length && i < rows; i++){
            for(int j = x; j < x + Shape[0].length && j < columns; j++){
                if(Shape[i-y][j-x] != -1) grid[i][j] = Shape[i-y][j-x];
            }
        }
        return grid;
    }

    public void Rotate(){
        CurrentOrientation++;
        if(CurrentOrientation==4) CurrentOrientation = 0;
        Shape = Shape_Rotation[CurrentOrientation];
    }

    public void moveDown(){ y++; }
    public void moveRight(){  x++; }
    public void moveLeft(){ x--; }

    public void dropDown(int[][] grid){
        while(checkCollisionUnder(grid)) y++;
    }

    public boolean checkCollisionUnder(int[][] grid){
        for(int col = 0; col < getWidth(); col++){
            // find the lowest non-empty cell in this column
            for(int row = getHeight()-1; row >= 0; row--){
                if(Shape[row][col] != -1){
                    int gridRow = y + row + 1;
                    int gridCol = x + col;
                    if(gridRow >= grid.length) return false; // hit bottom wall
                    if(grid[gridRow][gridCol] != -1) return false; // hit another block
                    break; // found lowest cell in this column, move to next column
                }
            }
        }
        return true;
    }

    /*public boolean checkCollisionUnder(int[][] grid){
        if(y + getHeight() >= grid.length) return false;
        for(int col = 0; col < getWidth(); col++){
            if(Shape[getHeight()-1][col] != -1){
                if(grid[y + getHeight()][x + col] != -1) return false;
            }
        }
        return true;
    }

     */

    public int getHeight() {return Shape.length;}
    public int getWidth() {return Shape[0].length;}



}

