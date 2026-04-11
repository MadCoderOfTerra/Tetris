import java.awt.Color;
import java.awt.Graphics;

public class Tetromino {
    private int[][] shape; // Hình dáng khối gạch (mảng 2 chiều)
    private Color color;   // Màu sắc
    private int x, y;      // Tọa độ hiện tại trên bàn cờ

    public Tetromino(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
        
        // Vị trí xuất phát mặc định (Cột 4 là ở giữa bàn cờ 10 cột, hàng 0 là trên cùng)
        this.x = 4; 
        this.y = 0;
    }

    // Hàm này vẽ khối gạch lên màn hình dựa vào mảng 2 chiều
    public void draw(Graphics g, int cellSize) {
        g.setColor(color);
        for (int row = 0; row < shape.length; row++) {
            for (int col = 0; col < shape[0].length; col++) {
                // Nếu giá trị trong mảng khác 0, tức là có gạch -> vẽ hình vuông
                if (shape[row][col] != 0) {
                    g.fillRect((x + col) * cellSize, (y + row) * cellSize, cellSize, cellSize);
                }
            }
        }
    }

    // Các hàm Lấy (Get) và Đặt (Set) tọa độ để sau này di chuyển khối gạch
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    // --- CÁC HÀM MỚI BỔ SUNG ĐỂ HOÀN THIỆN GAME ---

    public int[][] getShape() { return shape; }
    public Color getColor() { return color; }

    // Hàm xoay ma trận 90 độ theo chiều kim đồng hồ
    public void rotate() {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] newShape = new int[cols][rows];
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                newShape[c][rows - 1 - r] = shape[r][c];
            }
        }
        shape = newShape;
    }

    // Hàm hoàn tác xoay (dùng khi lỡ xoay mà bị kẹt vào tường)
    // Xoay 3 lần 90 độ = quay về vị trí cũ
    public void undoRotate() {
        rotate();
        rotate();
        rotate();
    }
}