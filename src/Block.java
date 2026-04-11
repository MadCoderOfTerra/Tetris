public abstract class Block implements SetOnesInGrid_{
    public int x = 0, y = 0;
    public boolean status = true;
}

interface SetOnesInGrid_ {
    public int[][] SetOnesInGrid(int[][] grid, boolean status);
};