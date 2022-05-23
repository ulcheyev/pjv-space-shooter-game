package com.cvut.src.managers;
import com.cvut.src.model.enemy.BossType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Data that represents the configuration for the game
 * @author ulcheyev
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpaceConfigurationData {

    private BossType bossTypeFirstLevel;
    private int enemiesQuantityFirstLevel;
    private int enemiesWaveFirstLevel;

    private BossType bossTypeSecondLevel;
    private int enemiesQuantitySecondLevel;
    private int enemiesWaveSecondLevel;


    /**
     * Initialize SpaceConfigurationData
     * @param bossTypeFirstLevel boss type for the first level
     * @param enemiesQuantityFirstLevel enemies quantity for the first level
     * @param enemiesWaveFirstLevel enemies waves quantity for the first level
     * @param bossTypeSecondLevel boss type for the second level
     * @param enemiesQuantitySecondLevel enemies quantity for the second level
     * @param enemiesWaveSecondLevel enemies waves quantity for the second level
     **/
    public SpaceConfigurationData(BossType bossTypeFirstLevel, int enemiesQuantityFirstLevel,
                                  int enemiesWaveFirstLevel, BossType bossTypeSecondLevel, int enemiesQuantitySecondLevel, int enemiesWaveSecondLevel) {
        this.bossTypeFirstLevel = bossTypeFirstLevel;
        this.enemiesQuantityFirstLevel = enemiesQuantityFirstLevel;
        this.enemiesWaveFirstLevel = enemiesWaveFirstLevel;
        this.bossTypeSecondLevel = bossTypeSecondLevel;
        this.enemiesQuantitySecondLevel = enemiesQuantitySecondLevel;
        this.enemiesWaveSecondLevel = enemiesWaveSecondLevel;
    }

    /**
     * Initialize SpaceConfigurationData
     **/
    public SpaceConfigurationData(){}


    /**
     * The method returns boss type for the first level
     * @return boss type for the first level
     **/
    public BossType getBossTypeFirstLevel() {
        return bossTypeFirstLevel;
    }

    /**
     * The method returns enemies quantity for the first level
     * @return enemies quantity for the first level
     **/
    public int getEnemiesQuantityFirstLevel() {
        return enemiesQuantityFirstLevel;
    }

    /**
     * The method returns enemies waves quantity for the first level
     * @return enemies waves quantity for the first level
     **/
    public int getEnemiesWaveFirstLevel() {
        return enemiesWaveFirstLevel;
    }

    /**
     * The method returns boss type for the second level
     * @return boss type for the second level
     **/
    public BossType getBossTypeSecondLevel() {
        return bossTypeSecondLevel;
    }

    /**
     * The method returns enemies quantity for the second level
     * @return enemies quantity for the second level
     **/
    public int getEnemiesQuantitySecondLevel() {
        return enemiesQuantitySecondLevel;
    }

    /**
     * The method returns enemies waves quantity for the second level
     * @return enemies waves quantity for the second level
     **/
    public int getEnemiesWaveSecondLevel() {
        return enemiesWaveSecondLevel;
    }





}
