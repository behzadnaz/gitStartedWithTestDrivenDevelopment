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
    public void canRetrieveFileObject(){
        assertEquals(path,saveFile.path());
    }
    @Test
    public void hasSaved() throws IOException {
        assertFalse("should not be saved before save() called", saveFile.hasEverBeenSaved());
        saveFile.save(anyValue,anyValue,anyValue);
        assertTrue("should be saved after saved called", saveFile.hasEverBeenSaved());
    }
    @Test
    public void hasSavedNotTrueIfExceptionOccurredWhileSaving(){
        try {
            path.createNewFile();
            path.setWritable(false);
            saveFile.save(anyValue,anyValue,anyValue);
            fail("expected IOException");
        } catch (IOException e){
            assertFalse("should not be saved", saveFile.hasEverBeenSaved());
        }
        finally {
            path.setWritable(true);
        }
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
        assertFileMatches("1.23","10.24", "100.25");
    }
    @Test
    public void saveWritesOutUserEnteredValuesExactlyAsEntered() throws IOException{
            saveFile.save(new UserEnteredDollars("foo"), new UserEnteredDollars(" bar"), new UserEnteredDollars("baz\t"));
            assertFileMatches("foo"," bar", "baz\t");
    }
    @Test
    public void saveHandlesDelimitersFeedInUserInput() throws IOException {
        saveFile.save(new UserEnteredDollars("\n\n\n \\n"), anyValue,anyValue);
        assertFileMatches("\\n\\n\\n \\\\n","any","any");
    }

    private void assertFileMatches(String expectedStartingBalance, String expectedCostBasis, String expectedYearlySpending) throws IOException {
        assertFileMatches("file", expectedStartingBalance, expectedCostBasis,expectedYearlySpending);
    }

    private void assertFileMatches(String message, String expectedStartingBalance, String expectedCostBasis, String expectedYearlySpending) throws IOException {
        String expected = "com.behzad.finances,1\n";
        expected += expectedStartingBalance + "\n";
        expected += expectedCostBasis + "\n";
        expected += expectedYearlySpending + "\n";
        assertEquals(message, expected,readFile());
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
