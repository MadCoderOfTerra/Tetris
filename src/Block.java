public abstract class Block implements SetOnesInGrid_{
    public int x = 0, y = 0;
    public boolean status = true; //true is falling false is stopped\
    public int Orientation = 1; //1 up 2 right 3 down 4 left
    public int LowestPoint = 0;

    public Block(int x, int y){
        this.x = x;
        this.y = y;
    }
}

interface SetOnesInGrid_ {
    public int[][] SetOnesInGrid(int[][] grid, boolean status);
};

