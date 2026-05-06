public class I_Piece extends TetrisBlock {
    public I_Piece(){
        super(new int[][]{ {1, 1, 1, 1} });
    }

    public void Rotate() {
        super.Rotate();
        if(this.getWidth() == 1) {
            x += 1;
            y += 1;
        } else {
            x -= 1;
            y += 1;
        }

    }
}