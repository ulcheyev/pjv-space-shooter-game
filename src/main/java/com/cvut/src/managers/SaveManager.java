package com.cvut.src.managers;

import com.cvut.src.model.Space;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveManager {
        private final static Logger logger = Logger.getLogger(SaveManager.class.getName());
        private static ObjectOutputStream out;
        private static ObjectInputStream in;


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
