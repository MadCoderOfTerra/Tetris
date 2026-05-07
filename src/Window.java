import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Flow;

public class Window extends JFrame {
    public JPanel mainMenu;
    public GameCanvas gc;



    public Window(){
        mainMenu = createMainMenu();

        setTitle("Tetris Made by Anderson and da Boys");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainMenu);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


    }

    /*public Window(){
        GameCanvas gc = new GameCanvas();

        add(gc);
        pack(); //cái này biến dimension của canvas thành dimension của cái window

        setLocationRelativeTo(null);
        setVisible(true);

        gc.initControls();
        gc.LaunchGame();
    }*/

    public JPanel createMainMenu(){
        JPanel menu = new JPanel();

        menu.setLayout(null);
        menu.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        menu.setPreferredSize(new Dimension(800, 800));

        JButton startBt = new JButton("Start");
        startBt.setBounds(300,300,200,30);
        startBt.addActionListener(e -> {
            gc = new GameCanvas();
            remove(mainMenu);
            add(gc);
            pack();
            gc.initControls();
            gc.LaunchGame();
        });

        JButton leaderboardBt = new JButton("Leaderboard");
        leaderboardBt.setBounds(300,400,200,30);
        JButton quitBt = new JButton("Quit");
        quitBt.setBounds(300,500,200,30);

        menu.add(startBt);
        menu.add(leaderboardBt);
        menu.add(quitBt);

        return menu;
    }
}
