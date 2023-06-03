package com.behzad.finances.persistence;

import com.behzad.finances.values.UserEnteredDollars;
import org.junit.Rule;
import org.junit.*;
import org.junit.rules.TemporaryFolder;

import java.io.*;

import static org.junit.Assert.*;

public class _SaveFileTest {
    private File path;
    private SaveFile saveFile;
    private UserEnteredDollars anyValue = new UserEnteredDollars("any");

    @Rule
    public TemporaryFolder tempDir = new TemporaryFolder();

    @Before
    public void setup(){
        path = new File(tempDir.getRoot(), "test file");
        saveFile = new SaveFile(path);
    }
    @Test
    public void saveCreatesFile() throws IOException {

        assertFalse("assume test file does not exists", path.exists());

        saveFile.save(anyValue, anyValue, anyValue);
        assertTrue("file should now exists", path.exists());
    }
    @Test
    public void saveOverwritesAnExistingFile() throws IOException {
        writeFile("test");
        assertEquals("file size setup assumption", 4, path.length());
        saveFile.save(anyValue, anyValue, anyValue);
        String fileContents = readFile();
        assertFalse("file should have been overwritten", fileContents.startsWith("test"));
    }
   @Test
   public void saveWritersFileContents() throws IOException {
        saveFile.save(new UserEnteredDollars("1.23"), new UserEnteredDollars("10.24"), new UserEnteredDollars("100.25"));
        String expected = "com.behzad.finances,1\n1.23\n10.24\n100.25\n";
        assertEquals(expected, readFile());
    }
    @Test
    public void saveWritesStartingBalance(){
        // saveFile.save(startingBalance);
        // assertEquals("10000", saveFile.contents());
    }

    private void writeFile(String text) throws IOException {
        Writer writer = new BufferedWriter(new FileWriter(path));
        try{
            writer.write(text);
        }finally {
            writer.close();  //ToDO: Exception handling
        }
    }

    private String readFile() throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(path));
        try {
            StringBuffer result= new StringBuffer();
            for(int c = input.read(); c != -1; c = input.read()){
                result.append((char)c);
            }
            return  result.toString();
        }
        finally {
            input.close();
        }

    }
}
