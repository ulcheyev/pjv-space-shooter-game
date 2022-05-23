package com.cvut.src.managers;
import com.cvut.src.controller.GameController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class used to work with a file with game saves
 * @author ulcheyev
 **/
public class SaveManager {
        private GameController controller;

        public SaveManager(GameController controller){
                this.controller = controller;
        }

        private final static Logger logger = Logger.getLogger(SaveManager.class.getName());
        private ObjectOutputStream out;
        private ObjectInputStream in;

        /**
         * The method saves the object to a file
         * @param object object to save
         **/
        public void save(Serializable object){
                clearFile();
                try{
                        out = new ObjectOutputStream(Files.newOutputStream(Paths.get("saves.txt")));
                        out.writeObject(object);
                        controller.showMessageOnPane("The game has been saved");
                        logger.log(Level.INFO, "Successful save to file");
                }catch (Exception e){
                        e.printStackTrace();
                        controller.showMessageOnPane("The game has not been saved");
                        logger.log(Level.SEVERE, "Writing to the save file failed");
                }
        }

        /**
         * The method return loaded object from saves file
         * @return  loaded object from file
         **/
        public Object load(){
                try{
                        in = new ObjectInputStream(Files.newInputStream(Paths.get("saves.txt")));
                        logger.log(Level.INFO, "Successful load from file");
                        return in.readObject();
                }catch (Exception e){
                        e.printStackTrace();
                        logger.log(Level.SEVERE, "Reading from the save file failed");
                        controller.showErrorMessage("Error while reading from a file");
                }
                return null;
        }

        /**
         * The method returns the status of the empty file
         * @return  true - file is empty, false - file is not empty
         **/
        public boolean isEmpty() {
                try{
                        BufferedReader br = new BufferedReader(new FileReader("saves.txt"));
                        if (br.readLine() != null) {
                                logger.log(Level.INFO, "Saves file is not empty");
                                return false;
                        }
                }catch (Exception e){
                        e.printStackTrace();
                }
                logger.log(Level.INFO, "Saves file is empty");
                return true;
        }

        //Clear file before writing
        private void clearFile(){
                try{
                        FileWriter fw = new FileWriter("saves.txt", false);
                        PrintWriter pw = new PrintWriter(fw, false);
                        pw.flush();
                        pw.close();
                        fw.close();
                        logger.log(Level.INFO, "Successful clear file");
                }catch(Exception exception){
                        logger.log(Level.SEVERE, "While clearing exception have been caught");
                }

        }


}
