package com.cvut.src.managers;
import com.cvut.src.controller.GameController;
import com.cvut.src.model.enemy.BossType;
import com.fasterxml.jackson.databind.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class that provides methods for working with the json file of the game card configuration
 * @author ulcheyev
 **/
public class JsonManager {
    private ObjectMapper objectMapper;
    private SpaceConfigurationData data;
    private GameController controller;
    private final static Logger logger = Logger.getLogger(SaveManager.class.getName());

    /**
     * Initialize Json Manager
     * @param controller game controller
     **/
    public JsonManager(GameController controller){
        objectMapper = new ObjectMapper();
        this.controller = controller;
    }

    /**
     * Initialize JsonManager
     **/
    public JsonManager(){}

    /**
     * The method parses json file with gaming space configuration
     **/
    public void parseJSON() {
        try {
            data = objectMapper.readValue(new File("space_configuration.json"), SpaceConfigurationData.class);
            logger.log(Level.INFO, "Successful read from file");
        }catch (Exception e){
            e.printStackTrace();
            controller.showErrorMessage("Error while parsing json file");
            logger.log(Level.INFO, "Error while parsing json file");
        }
    }

    /**
     * The method write space configuration data object to json file
     * @param data data object to write to json file
     **/
    public void writeJSON(SpaceConfigurationData data) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("space_configuration.json"), data);
            logger.log(Level.INFO, "Successful write to file");
        } catch (Exception e) {
            logger.log(Level.INFO, "Error while write to json file");
            e.printStackTrace();
        }

    }

    /**
     * The method returns space data configuration
     * @return space data configuration
     **/
    public SpaceConfigurationData getData() {
        return data;
    }

}
