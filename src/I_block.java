public class I_Block extends Block{
	public I_block(int x, int y){
		this.x = x;
		this.y = y;
		
		this.matrix = new int[][]{
			{0, 2, 0},
			{0, 2, 0},
			{0, 2, 0},
			{0, 2, 0}
		};
	}
}