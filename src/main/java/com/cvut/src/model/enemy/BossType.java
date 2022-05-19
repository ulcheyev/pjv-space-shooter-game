package com.cvut.src.model.enemy;

public enum BossType {
    TITAN("/boss1.png", 1, 2, 1), PREDATOR("/bossn.png", 1, 2, 1);

    private String imgPath;
    private double damage;
    private double health;
    private double moveSpeed;

    BossType(String imgPath, double damage, double health, double moveSpeed) {
        this.imgPath = imgPath;
        this.damage = damage;
        this.health = health;
        this.moveSpeed = moveSpeed;
    }

    public String getImgPath() {return imgPath;}
    public void setImgPath(String imgPath) {this.imgPath = imgPath;}
    public double getDamage() {return damage;}
    public void setDamage(double damage) {this.damage = damage;}
    public double getHealth() {return health;}
    public void setHealth(double health) {this.health = health;}
    public double getMoveSpeed() {return moveSpeed;}
    public void setMoveSpeed(double moveSpeed) {this.moveSpeed = moveSpeed;}
}
