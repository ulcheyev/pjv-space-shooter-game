import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputManager extends KeyAdapter {

    PlayerShip playerShip;

    public InputManager(PlayerShip playerShip) {
        this.playerShip = playerShip;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        playerShip.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        playerShip.keyReleased(e);
    }
}
