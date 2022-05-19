package com.cvut.src.model.player.ship;

public enum ShipType {
    ALIEN("/hero3.png",0.5, 0.5, 2), SHARK("/hero2.png",0.5, 0.5, 3);

    private String imgPath;
    private double damage;
    private double health;
    private double moveSpeed;

    ShipType(String imgPath, double damage, double health, double moveSpeed) {
        this.imgPath = imgPath;
        this.damage = damage;
        this.health = health;
        this.moveSpeed = moveSpeed;
    }

    public double getDamage() {return damage;}
    public void setDamage(double damage) {this.damage = damage;}
    public double getHealth() {return health;}
    public void setHealth(double health) {this.health = health;}
    public double getMoveSpeed() {return moveSpeed;}
    public void setMoveSpeed(double moveSpeed) {this.moveSpeed = moveSpeed;}
    public String getImgPath() {return imgPath;}
}
