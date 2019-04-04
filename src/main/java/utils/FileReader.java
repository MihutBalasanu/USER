package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FileReader {

    private static List<String> listOfLines = new ArrayList<>();
    private final static Logger LOGGER = Logger.getLogger(Logger.class.getName());

    public static List<String> readFromFile(String path){
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listOfLines.add(line);
            }
        } catch (FileNotFoundException e) {
            LOGGER.warning(e.getMessage());

        } catch (IOException e) {
            LOGGER.warning(e.getMessage());
        }
        return listOfLines;
    }
}
