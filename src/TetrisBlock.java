import java.awt.*;
import java.util.Random;

public abstract class TetrisBlock {
    public int[][] Shape;
    public int[][][] Shape_Rotation;   //each rotation of
    public int x;
    public int y;
    public int CurrentOrientation = 0;
    public int ColorToChooseID;
    public Color[] ColorToChoose = {Color.red, Color.blue, Color.GREEN};

    public TetrisBlock(int[][] Shape){
        this.Shape = Shape;
        Random r = new Random();
        ColorToChooseID = r.nextInt(ColorToChoose.length);
        resetColor();
        Find4Rotation();
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

    public void Rotate(){
        CurrentOrientation++;
        if(CurrentOrientation==4) CurrentOrientation = 0;
        Shape = Shape_Rotation[CurrentOrientation];
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
                grid[i][j] = Shape[i-y][j-x];
            }
        }
        return grid;
    }

    public void moveDown(){ y++; }
    public void moveRight(){  x++; }
    public void moveLeft(){ x--; }

    public void dropDown(int[][] grid){
        while(checkCollisionUnder(grid)) y++;
    }

    public void fixBlockInPlace(){

    }

    public boolean checkCollisionUnder(int[][] grid){
        if(grid[x][y+getHeight()+1] != -1) return false;
        return true;
    }

    public int getHeight() {return Shape.length;}
    public int getWidth() {return Shape[0].length;}



}

