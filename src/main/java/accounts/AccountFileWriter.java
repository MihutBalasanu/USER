package accounts;

import java.io.*;
import java.util.logging.Logger;

/**
 * Writes a string in a file.
 * @author Mihut Balasanu
 */
public class AccountFileWriter {

    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    /**
     * Writes a string in a file.
     * @param path A string containing the path to the file to write on
     * @param str A string containing the text to be written on the file
     */
    public static void writeOnFile(String path, String str) {

        // Append the text if the file exists
       try {
           BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(path,true));
               writer.newLine();
               writer.write(str);
               writer.close();

       }catch (FileNotFoundException e) {
            LOGGER.warning(e.getMessage());
       } catch (IOException e){
           LOGGER.warning(e.getMessage());
       }
    }
}
