import com.cvut.src.controller.GameController;
import com.cvut.src.model.Space;
import com.cvut.src.model.bonusItem.HPItem;
import com.cvut.src.model.bonusItem.SpeedItem;
import com.cvut.src.model.player.PlayerShield;
import com.cvut.src.model.player.ship.PlayerShip;
import com.cvut.src.model.player.ship.ShipType;
import com.cvut.src.view.components.MyProgressBar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.awt.*;

import static org.mockito.Mockito.*;

public class Tests {

    private Space mockedSpace;
    private GameController mockedController;
    private MyProgressBar mockedProgressBar;

    private HPItem hpItem;
    private SpeedItem speedItem;
    private PlayerShip ship;
    private PlayerShield shield;


    @BeforeEach
    private void setUp(){
        mockedController = mock(GameController.class);
        mockedSpace = mock(Space.class);
        ship = new PlayerShip(mockedController, ShipType.SHARK);
        mockedProgressBar = mock(MyProgressBar.class);

        when(mockedSpace.getPlayerShip()).thenReturn(ship);
        when(mockedSpace.getPlayerInventory()).thenReturn(ship.getInventory());

        when(mockedController.getGameSpace()).thenReturn(mockedSpace);
        when(mockedController.getChoosenShip()).thenReturn(ship);
        when(mockedController.getHPBar()).thenReturn(mockedProgressBar);
    }

    @Test
    public void playerShipGetHpItemBonus_activateBonus_increasePlayerShipHp(){
        //Increase quantity
        ship.getInventory().increaseItemQuantity(new HPItem(mockedController, 0 ,0));

        //Verify item quantity increased
        Assertions.assertEquals(1, ship.getInventory().getHp().getQuantity());


        //Save bonus attribute
        double attribute = ship.getInventory().getHp().getAttribute();

        //Decrease player hp
        ship.setHealth(0.1);

        //Activate bonus
        hpItem = new HPItem(mockedController, 0 , 0);
        hpItem.activateBonus();

        //Verify bonus activate
        Assertions.assertEquals(0.1 + attribute, ship.getHealth());
    }

    @Test
    public void playerShipGetSpeedItemBonus_activateBonus_increasePlayerShipSpeedMove(){
        //Increase quantity
        ship.getInventory().increaseItemQuantity(new SpeedItem(mockedController, 0 ,0));

        //Verify item quantity increased
        Assertions.assertEquals(1, ship.getInventory().getSpeed().getQuantity());

        //Save bonus attribute
        double attribute = ship.getInventory().getSpeed().getAttribute();

        //Activate bonus
        speedItem = new SpeedItem(mockedController, 0 , 0);
        speedItem.activateBonus();

        //Verify bonus activate
        Assertions.assertEquals(attribute, ship.getSpeedMove());
    }

    @Test
    public void playerShipShield_shieldDestroy_destroyedShieldAfter10Hits(){
        //Get
        shield = ship.getShield();
        double health = ship.getHealth();

        //Verify full (10) durability
        Assertions.assertEquals(10, shield.getDurability());

        //Player taking damage. After 10 hits shield must have 0 durability
        for(int i = 0; i < 10; i++){
            ship.takinDamage(0.000001);
        }

        //Verify,that player health doesnt change
        Assertions.assertEquals(health, ship.getHealth());

        //Verify destroyed shield durability
        Assertions.assertEquals(0, shield.getDurability());

        //Now player ship must take damage
        ship.takinDamage(0.1);
        Assertions.assertEquals(health - 0.1, ship.getHealth());

    }




}
