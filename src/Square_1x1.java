public class Square_1x1 extends Block{

    public Square_1x1(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean status = true; //true is falling false is stopped

    @Override
    public int[][] SetOnesInGrid(int[][] grid, boolean status) {
        grid[y][x] = 2;
        if(!status) grid[y][x] = 1;
        return grid;
    }
}