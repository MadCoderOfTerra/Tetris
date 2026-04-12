public class I_Piece extends Block{
    public Block[] BlockOfIPiece = new Block[4];

    public I_Piece(int x, int y){
        super(x,y);
        for(int i=0;i<4;i++){
            BlockOfIPiece[i] = new Square_1x1(x,y+i-2);
        }
        LowestPoint = y+1;
    }

    @Override
    public int[][] SetOnesInGrid(int[][] grid, boolean status) {
        for(int i=0;i<4;i++){
            grid = BlockOfIPiece[i].SetOnesInGrid(grid,status);
        }
        return grid;
    }
}
