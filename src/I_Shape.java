public class I_Shape extends TetrisBlock {
    public I_Shape(){
        super(new int[][]{ {1, 1, 1, 1} }, 0); 
    }

    @Override
    public void Rotate() {
        if(CurrentOrientation==1){
            x += 2;
            y -= 1;
        } else if(CurrentOrientation==2){
            x -= 2;
            y += 2;
        } else if(CurrentOrientation==3){
            x += 1;
            y -= 2;
        } else if(CurrentOrientation==0){
            x -= 1;
            y += 1;
        }
        while(x<0)x++;
        while(x>20)x--;
        while(y<0)y++;
        while(y>20)y--;

        super.Rotate();
    }

    @Override
    public boolean checkCollisionRotate(int[][] grid){
        int nextOrientation = (CurrentOrientation + 1) % 4;
        int[][] nextShape = Shape_Rotation[nextOrientation];

        int offsetX = switch(CurrentOrientation){
            case 0 -> -1;
            case 1 -> 2;
            case 2 -> -2;
            case 3 -> 1;
            default -> 0;
        };

        int offsetY = switch(CurrentOrientation){
            case 0 -> 1;
            case 1 -> -1;
            case 2 -> 2;
            case 3 -> -2;
            default -> 0;
        };

        for(int row = 0; row < nextShape.length; row++){
            for(int col = 0; col < nextShape[0].length; col++){
                if(nextShape[row][col] != -1){
                    int gridRow = y + row + offsetY;
                    int gridCol = x + col + offsetX;
                    if(gridCol < 0) return false;
                    if(gridCol >= grid[0].length) return false;
                    if(gridRow >= grid.length) return false;
                    if(grid[gridRow][gridCol] != -1) return false;
                }
            }
        }
        Rotate();
        return true;
    }
}