package accounts;

import java.io.*;
import java.util.logging.Logger;


public class AccountFileWriter {

    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    public static void writeOnFile(String path, String str) {

       try {
           BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(path,true));
               writer.newLine();
               writer.write(str);
               writer.close();

       }catch (FileNotFoundException e) {
//            e.printStackTrace();
            LOGGER.warning(e.getMessage());
       } catch (IOException e){
//           e.printStackTrace();
           LOGGER.warning(e.getMessage());
       }
    }
}
