package com.cvut.src.model.player.ship;
/**
 * The class represents player ship types
 * @author ulcheyev
 **/
public enum ShipType {
    ALIEN("/hero3.png",1, 1, 2), SHARK("/hero2.png",1, 1.5, 3);

    private String imgPath;
    private double damage;
    private double health;
    private double moveSpeed;

    /**
     * Initialize boss type
     * @param imgPath image path
     * @param damage player's ship damages
     * @param health player's ship health
     * @param moveSpeed player's ship move speed
     **/
    ShipType(String imgPath, double damage, double health, double moveSpeed) {
        this.imgPath = imgPath;
        this.damage = damage;
        this.health = health;
        this.moveSpeed = moveSpeed;
    }

    /** Returns player's ship damage
     * @return  player's ship damage
     **/
    public double getDamage() {return damage;}

    /** Sets player's ship damage
     * @param damage damage to set
     **/
    public void setDamage(double damage) {this.damage = damage;}

    /** Returns player's ship health
     * @return  player's ship health
     **/
    public double getHealth() {return health;}

    /** Sets player's ship health
     * @param health health to set
     **/
    public void setHealth(double health) {this.health = health;}

    /** Returns player's ship speed
     * @return  player's ship speed
     **/
    public double getMoveSpeed() {return moveSpeed;}

    /** Sets player's ship speed
     * @param moveSpeed move speed to set
     **/
    public void setMoveSpeed(double moveSpeed) {this.moveSpeed = moveSpeed;}

    /** Returns player's ship image path
     * @return player's ship image path
     **/
    public String getImgPath() {return imgPath;}
}
