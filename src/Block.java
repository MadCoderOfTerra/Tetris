public abstract class Block {
    public int x = 0, y = 0;
    public boolean status = true;

    public int matrix[][];


    public int[][] SetOnesInGrid(int[][] grid, boolean status){
        int value = status ? 2 : 1;

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] == 2) {
                    grid[this.y + row][this.x + col] = value;
                }
            }
        }
        return grid;
    }

    public boolean canMoveDown(int[][] grid){
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] == 2) {
                    int newY = this.y + row;
                    int newX = this.x + col;

                    if (newY + 1 > 21) {
                        return false;
                    }

                    if (grid[newY+1][newX] == -1 || grid[newY+1][newX] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
