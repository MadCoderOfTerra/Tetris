import javax.print.attribute.standard.OrientationRequested;

public class I_Piece extends Block{
    public Block[] BlockOfIPiece = new Block[4];

    public I_Piece(int x, int y){
        super(x,y);

        LowestPointOrientation = new int[]{1,0,2,0};
        Orientation_X = new int[]{0,-1,0,2}; //0 up 1 right 2 down 3 left
        Orientation_Y = new int[]{-2,0,1,0};

        for(int i=0;i<4;i++) BlockOfIPiece[i] = new Square_1x1(2,2);

        update();
    }

    @Override
    public void update(){
        for(int i=0;i<4;i++){
            BlockOfIPiece[i].x = x + Orientation_X[Orientation-1] + ((Orientation_X[Orientation-1]!=0) ? i : 0);
            BlockOfIPiece[i].y = y + Orientation_Y[Orientation-1] + ((Orientation_Y[Orientation-1]!=0) ? i : 0);
            LowestPoint = y + LowestPointOrientation[Orientation-1];
        }
    }

    @Override
    public int[][] SetOnesInGrid(int[][] grid, boolean status) {
        for(int i=0;i<4;i++){
            grid = BlockOfIPiece[i].SetOnesInGrid(grid,status);
        }
        return grid;
    }
}
