import javax.swing.*;

public class Menu extends JFrame{
    public JPanel mainMenu = new JPanel();
    public GameCanvas gc = new GameCanvas();


    public Menu(){
        mainMenu.setLayout(null);
        GameCanvas gc = new GameCanvas();

        setTitle("Tetris Made by Anderson and da Boys");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(gc);
        pack(); //cái này biến dimension của canvas thành dimension của cái window

        setLocationRelativeTo(null);
        setVisible(true);

        gc.initControls();
        gc.LaunchGame();
    }
}
