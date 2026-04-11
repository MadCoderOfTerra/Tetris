public class Square_2x2 extends Block{

    public Square_2x2(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean canMoveDown(int[][] grid) {
        if (y + 2 > 21) return false;
        if (grid[y + 2][x] == -1 || grid[y + 2][x] == 1 || grid[y+2][x+1] == -1 || grid [y+2][x+1] == 1) return false;

        return true;
    }    
    @Override
    public int[][] SetOnesInGrid(int[][] grid, boolean status) {
        int value = status ? 2 : 1;

        grid[y][x] = value;
        grid[y][x+1] = value;
        grid[y+1][x] = value;
        grid[y+1][x+1] = value;

        return grid;
    }
}