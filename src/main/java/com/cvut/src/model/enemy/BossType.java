package com.cvut.src.model.enemy;

/**
 * The class represents boss ship types
 * @author ulcheyev
 **/
public enum BossType {
    TITAN("/boss1.png", 1, 2, 1),
    PREDATOR("/bossn.png", 1, 2, 2),
    GIGANT("/boss2.png", 1, 1, 1);

    private String imgPath;
    private double damage;
    private double health;
    private double moveSpeed;

    /**
     * Initialize boss type
     * @param imgPath image path
     * @param damage boss's damages
     * @param health boss's health
     * @param moveSpeed boss's move speed
     **/
    BossType(String imgPath, double damage, double health, double moveSpeed) {
        this.imgPath = imgPath;
        this.damage = damage;
        this.health = health;
        this.moveSpeed = moveSpeed;
    }
    BossType(){}

    /** Returns image of this boss
     * @return boss's image
     **/
    public String getImgPath() {return imgPath;}

    /** Returns damage of this boss
     * @return boss's damage
     **/
    public double getDamage() {return damage;}

    /** Sets boss's damage
     * @param  damage damage to set
     **/
    public void setDamage(double damage) {this.damage = damage;}

    /** Returns boss's health
     * @return  boss's health
     **/
    public double getHealth() {return health;}

    /** Returns boss's move speed
     * @return  boss's speed
     **/
    public double getMoveSpeed() {return moveSpeed;}

}
