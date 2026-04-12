public class Z_block extends Block{
	public Z_block(int x, int y){
		this.x = x;
		this.y = y;
		
		this.matrix = new int[][]{
			{0, 0, 0},
			{2, 2, 0},
			{0, 2, 2}
		};
	}
}