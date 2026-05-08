public class I_Shape extends TetrisBlock {
    public I_Shape(){
        super(new int[][]{ {1, 1, 1, 1} }, 0); 
    }

    @Override
    public void Rotate() {
        if(CurrentOrientation==1){
            y -= 1;
            x += 2;
        } else if(CurrentOrientation==2){
            x -= 2;
            y += 2;
        } else if(CurrentOrientation==3){
            y -= 2;
            x += 1;
        } else if(CurrentOrientation==0){
            x -= 1;
            y += 1;
        }

        super.Rotate();

    }
}