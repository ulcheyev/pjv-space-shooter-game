import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Space extends JPanel implements ActionListener{

    private Timer timer = new Timer(2, this);;
    private Background background = new Background();;
    private PlayerShip playerShip = new PlayerShip();
    private int  enemiesWave = 2;
    private int enemiesQuantity = 3;

    private static ArrayList<Bullet> bullets = new ArrayList<>();
    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    private static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<Enemy> getEnemies() {return enemies;}

    private static ArrayList<Explosion> explosions = new ArrayList<>();
    private Random random = new Random();



    public Space(){
        addKeyListener(new InputManager(playerShip));
        setFocusable(true); // pri sozdanii objekta on budet v fokuse
        timer.start();


    }

    public void update() {
        background.update();

        playerShip.update();

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update();
        }

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }
    }

    @Override
    public void paint(Graphics graphics) {
        //risuem zadniy fon
        background.paint(graphics);

        //risuem puli. udalyaem puli, kogda oni uhodyat za ramki
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).paint(graphics);
            if(bullets.get(i).getImgParam().getY() < -50){
                bullets.remove(i);
                i--;
            }
        }

        //risuem playera
        playerShip.paint(graphics);


        //risuem vragov i proveryam na kolize s puleu
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).paint(graphics);
            for(int j = 0; j < bullets.size(); j++){
                if(enemies.get(i).getRectangle().intersects(bullets.get(j).getRectangle())){
                    enemies.get(i).takinDamage(bullets.get(j).getDamage());
                    bullets.remove(j);
                }
            }
        }
        enemyRemove();
        if(enemies.size() == 0){
            if(enemiesWave != 0){
                enemiesWave--;
                for(int i = 0; i <= enemiesQuantity; i++){
                    enemies.add(new EnemyBlue(random.nextInt(900), random.nextInt(400)));
                    enemies.add(new EnemyYellow(random.nextInt(900), random.nextInt(400)));
                    enemies.add(new EnemyGreen(random.nextInt(900), random.nextInt(400)));
                }
            }
        }
        updateExplosions();

        //risuem vybuchy
        for(int i = 0; i < explosions.size(); i++){
            explosions.get(i).paint(graphics);
        }
    }


    public void enemyRemove(){
        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).getHealth() <= 0){
                explosions.add(new Explosion(enemies.get(i).imgParam.getX(), enemies.get(i).imgParam.getY()));
                enemies.remove(i);
                i--;
            }
        }
    }

    public void updateExplosions () {
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (!explosions.get(i).getRun()) {
                explosions.remove(i);
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }
}
