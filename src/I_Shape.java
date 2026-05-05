public class I_Shape extends TetrisBlock {
    public I_Shape(){
        super(new int[][]{ {1, 1, 1, 1} }, 0); 
    }

    @Override
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