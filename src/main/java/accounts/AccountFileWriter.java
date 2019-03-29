package accounts;

import java.io.*;

public class AccountFileWriter {

    AccountWriter accountWriter = new AccountWriter();

    public static void writeOnFile(String path, String str) {

       try {
           BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(path));
           writer.write(str);

           writer.close();
       }catch (FileNotFoundException e) {
            e.printStackTrace();
       } catch (IOException e){
           e.printStackTrace();
       }
    }
}
