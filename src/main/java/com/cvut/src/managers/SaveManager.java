package com.cvut.src.managers;
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

        private final static Logger logger = Logger.getLogger(SaveManager.class.getName());
        private static ObjectOutputStream out;
        private static ObjectInputStream in;

        /**
         * The method saves the object to a file
         * @param object object to save
         **/
        public static void save(Serializable object){
                clearFile();
                try{
                        out = new ObjectOutputStream(Files.newOutputStream(Paths.get("saves.txt")));
                        out.writeObject(object);
                        logger.log(Level.INFO, "Successful save to file");
                }catch (Exception e){
                        e.printStackTrace();
                        logger.log(Level.SEVERE, "Writing to the save file failed");
                }
        }

        /**
         * The method return loaded object from saves file
         * @return  loaded object from file
         **/
        public static Object load(){
                try{
                        in = new ObjectInputStream(Files.newInputStream(Paths.get("saves.txt")));
                        logger.log(Level.INFO, "Successful load from file");
                        return in.readObject();
                }catch (Exception e){
                        e.printStackTrace();
                        logger.log(Level.SEVERE, "Reading from the save file failed");
                }
                return null;
        }

        /**
         * The method returns the status of the empty file
         * @return  true - file is empty, false - file is not empty
         **/
        public static boolean isEmpty() {
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
        private static void clearFile(){
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
