package gitlet.TDDtest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static gitlet.Repository.INDEX;
import static org.junit.Assert.*;

import gitlet.Main;
import org.junit.Test;

public class InedxTest {
    @Test
     public void addTest () throws IOException {
        File tempFile = new File(INDEX,"temp.txt");
        tempFile.createTempFile("testFile", ".txt");
        FileWriter writer= new FileWriter(tempFile);
        writer.write("Hello Gitet.");

        File tempFile2 = new File(INDEX,"temp2.txt");
        tempFile2.createTempFile("testFile2", ".txt");
        FileWriter writer2= new FileWriter(tempFile2);
        writer2.write("Hello word!");

        String[] input1 = {"add","tempFile"};
        Main.main(input1);

        assertTrue(tempFile.exists());

        String[] input2 = {"add","tempFile2"};
        Main.main(input2);

        assertTrue(tempFile.exists());
        assertTrue(tempFile2.exists());


     }
}
