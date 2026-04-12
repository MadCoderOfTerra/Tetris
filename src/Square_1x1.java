public class Square_1x1 extends Block{

    public Square_1x1(int x, int y){
        super(x,y);
        LowestPoint = y;
    }

    @Override
    public void update(){};

    @Override
    public int[][] SetOnesInGrid(int[][] grid, boolean status) {
        grid[y][x] = 2;
        if(!status) grid[y][x] = 1;
        return grid;
    }
}