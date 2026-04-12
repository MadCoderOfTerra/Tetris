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

    public boolean isValidPosition(int[][] grid) {
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                if (matrix[row][col] == 2) {
                    int newX = this.x + col;
                    int newY = this.y + row;

                    if (newX < 0 || newX > 11 || newY > 21) {
                        return false;
                    }

                    if (grid[newY][newX] == -1 || grid[newY][newX] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void rotate (int [][] grid){
        int n = matrix.length;
        int [][] rotatedMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotatedMatrix[j][n - 1 - i] = matrix[i][j];
            }
        }

        int [][] originalMatrix = this.matrix;
        this.matrix = rotatedMatrix;

        if (!isValidPosition(grid)) {
            this.matrix = originalMatrix;
        }
    }

    
}
