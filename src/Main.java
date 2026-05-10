import java.awt.*;
import javax.swing.*;
import java.util.prefs.Preferences;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContainer;
    private GameCanvas gameCanvas;
    private Preferences prefs = Preferences.userNodeForPackage(Main.class);
    public static Sound menuMusic = new Sound();
    public static Sound gameMusic = new Sound();
    public static Sound gameOverMusic = new Sound();
    public static Sound menuClickSound = new Sound();
    public static Sound blockLandSound = new Sound();
    public static Sound rotationSound = new Sound();
    public static Sound lineClearSound = new Sound();
    private JLabel highScoreLabel;

    public Main() {
        setTitle("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        menuMusic.loadSound("materials/music/menu.wav");
        gameMusic.loadSound("materials/music/tetris.wav");
        gameOverMusic.loadSound("materials/music/gameover.wav");
        menuClickSound.loadSound("materials/music/menusound.wav");
        blockLandSound.loadSound("materials/music/blockland.wav");
        rotationSound.loadSound("materials/music/rotation.wav");
        lineClearSound.loadSound("materials/music/lineclear.wav");

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        JPanel leaderboardPanel = createLeaderboardPanel();
        JPanel creditsPanel = createCreditsPanel();
        JPanel menuPanel = createMenuPanel();

        gameCanvas = new GameCanvas();
        mainContainer.add(menuPanel, "Menu");
        mainContainer.add(leaderboardPanel, "Leaderboard");
        mainContainer.add(creditsPanel, "Credits");
        mainContainer.add(gameCanvas, "Game");
        add(mainContainer);
        setVisible(true);
        menuMusic.playLoop();
    }

    private void styleButton(JButton btn, Color bgColor) {
        Dimension fixedSize = new Dimension(350, 80);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Monospaced", Font.BOLD, 30));
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMinimumSize(fixedSize);
        btn.setPreferredSize(fixedSize);
        btn.setMaximumSize(fixedSize);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 4), 
            BorderFactory.createEmptyBorder(15, 40, 15, 40)
        ));
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        Box box = Box.createVerticalBox();
        JButton playBtn = new JButton("PLAY GAME");
        JButton leaderBtn = new JButton("HIGH SCORE");
        JButton creditsBtn = new JButton("CREDITS");
        JButton quitBtn = new JButton("QUIT");

        JLabel gameTitle = new JLabel("<html>" +
                "<font color='#FD3F59'>T</font>" +
                "<font color='#FFC82E'>E</font>" +
                "<font color='#FEFB34'>T</font>" +
                "<font color='#53DA3F'>R</font>" +
                "<font color='#01EDFA'>I</font>" +
                "<font color='#DD0AB2'>S</font>" +
                "</html>");

        gameTitle.setFont(new Font("Monospaced", Font.BOLD, 300));
        gameTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        styleButton(playBtn, new Color(0, 180, 0));
        styleButton(leaderBtn, new Color(0, 100, 255));
        styleButton(creditsBtn, new Color(255, 128, 0));
        styleButton(quitBtn, new Color(200, 0, 0));

        playBtn.addActionListener(e -> {
            menuClickSound.play();
            menuMusic.stop();
            gameMusic.playLoop();
            cardLayout.show(mainContainer, "Game");
            gameCanvas.resetGame(); 
            gameCanvas.initControls(); 
            gameCanvas.LaunchGame(); 
            gameCanvas.requestFocusInWindow(); 
        });

        leaderBtn.addActionListener(e -> {
            menuClickSound.play();
            highScoreLabel.setText("<html><center><h1 style='font-size: 80px; color: cyan;'>HIGHEST SCORE</h1><br><h2 style='font-size: 70px; color: yellow;'>" + getHighScore() + "</h2></center></html>");
            cardLayout.show(mainContainer, "Leaderboard");
        });

        creditsBtn.addActionListener(e -> {
            menuClickSound.play();
            cardLayout.show(mainContainer, "Credits");
        });

        quitBtn.addActionListener(e -> {
            menuClickSound.play();
            System.exit(0);
        });

        box.add(gameTitle);
        box.add(Box.createVerticalStrut(100));
        box.add(playBtn);
        box.add(Box.createVerticalStrut(25));
        box.add(leaderBtn);
        box.add(Box.createVerticalStrut(25));
        box.add(creditsBtn);
        box.add(Box.createVerticalStrut(25));
        box.add(quitBtn);
        panel.add(box);
        return panel;
    }

    private JPanel createLeaderboardPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        highScoreLabel = new JLabel();
        highScoreLabel.setForeground(Color.WHITE);
        highScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton backBtn = new JButton("BACK TO MENU");
        styleButton(backBtn, new Color(128, 0, 128));

        backBtn.addActionListener(e -> {
            menuClickSound.play();
            cardLayout.show(mainContainer, "Menu");
        });

        Box box = Box.createVerticalBox();
        box.add(highScoreLabel);
        box.add(Box.createVerticalStrut(80));
        box.add(backBtn);
        panel.add(box);
        return panel; 
    }

    private JPanel createCreditsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        JLabel creditsLabel = new JLabel("<html><center><h1 style='font-size: 80px; color: #FF8000;'>CREDITS</h1><br><h2 style='font-size: 50px;'>Made by Group C<br><i><br>Bùi Gia Bảo<br>Trần Nguyễn Thành An<br>Phan Hoàng An<br>Võ Tuấn Kiệt<br>Trần Khánh Duy</i></h2></center></html>");
        creditsLabel.setForeground(Color.WHITE);
        creditsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton backBtn = new JButton("BACK TO MENU");
        styleButton(backBtn, new Color(128, 0, 128));

        backBtn.addActionListener(e -> {
            menuClickSound.play();
            cardLayout.show(mainContainer, "Menu");
        });

        Box box = Box.createVerticalBox();
        box.add(creditsLabel);
        box.add(Box.createVerticalStrut(100));
        box.add(backBtn);
        panel.add(box);
        return panel;
    }

    public int getHighScore() { return prefs.getInt("HighScore", 0); }

    public static void main(String[] args) {
        System.setProperty("sun.java2d.uiScale", "1.0"); // fixes the scale 125% problem
        SwingUtilities.invokeLater(() -> new Main());
    }
}