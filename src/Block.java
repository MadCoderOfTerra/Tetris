public abstract class Block implements SetOnesInGrid_{
    public int x = 0, y = 0;
    public boolean status = true;

    public abstract int[][] SetOnesInGrid(int[][] grid, boolean status);

    public abstract boolean canMoveDown(int[][] grid);
}
