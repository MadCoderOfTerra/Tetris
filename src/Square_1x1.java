public class Square_1x1 extends Block{

    public Square_1x1(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean canMoveDown(int[][] grid) {
        if (y + 1 > 21) return false;
        return !(grid[y + 1][x] == -1 || grid[y + 1][x] == 1);
    }


    @Override
    public int[][] SetOnesInGrid(int[][] grid, boolean status) {
        grid[y][x] = status ? 2 : 1;
        return grid;
    }
}