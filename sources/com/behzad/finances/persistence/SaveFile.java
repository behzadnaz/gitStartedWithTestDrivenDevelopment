package com.behzad.finances.persistence;

import com.behzad.finances.values.UserEnteredDollars;

import java.io.*;

public class SaveFile {
    private File path;
    private boolean hasSaved =false;
    public SaveFile(File path) {
        this.path =path;
    }
    public File path() {
        return path;
    }
    public void save(UserEnteredDollars startingBalance, UserEnteredDollars costBasis, UserEnteredDollars yearlySpending) throws IOException {

        Writer writer = new BufferedWriter(new FileWriter(path));
        try {
            writeLine(writer,"com.behzad.finances,1");
            writeLine(writer, startingBalance.getUserText());
            writeLine(writer,costBasis.getUserText() );
            writeLine(writer,yearlySpending.getUserText());
            hasSaved =true;
        } finally {
            writer.close();
        }
    }
    public void writeLine(Writer writer, String line)throws IOException{
        line = line.replace("\\", "\\\\");
        line = line.replace("\n", "\\n");
        writer.write(line+"\n");
    }


    public boolean hasEverBeenSaved() {
        return hasSaved;
    }
}
