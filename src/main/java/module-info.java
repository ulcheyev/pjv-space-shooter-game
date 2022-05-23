module com.cvut.spacehunter {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.media;
    requires java.logging;
    requires com.fasterxml.jackson.databind;
    exports com.cvut.src;
    exports com.cvut.src.model;
    exports com.cvut.src.view;
    exports com.cvut.src.controller;
    exports com.cvut.src.model.explosion;
    exports com.cvut.src.model.enemy;
    exports com.cvut.src.model.player;
    exports com.cvut.src.view.components;
    exports com.cvut.src.model.bullet.ammo;
    exports com.cvut.src.model.bullet.rocket;
    exports com.cvut.src.model.bonusItem;
    exports com.cvut.src.model.bullet;
    exports com.cvut.src.model.player.ship;
    exports com.cvut.src.managers;
    opens com.cvut.src.managers;
}
