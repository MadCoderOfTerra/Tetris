public class S_block extends Block{
	public S_block(int x, int y){
		this.x = x;
		this.y = y;
		
		this.matrix = new int[][]{
			{0, 0, 0},
			{0, 2, 2},
			{2, 2, 0}
		};
	}
}