import javax.swing.*;

public class Game {
    private static JFrame jFrame; // object jframe
    public static final int WIDTH = 1000;
    public static final int HIGHT = 1000;


    public static void main(String[] args) {
        jFrame = new JFrame("Space Hunter v1"); // nazvanie okna
        jFrame.setSize(WIDTH, HIGHT);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null); // igra po centru pri zapuske
        jFrame.add(new Space());
        jFrame.setVisible(true); // sdelat vidimym okno pri zapuske. Vazhno poradi, potomuchto posle togo, kak okno poyavilos, logika idti uzhe ne budet.
    }
}
