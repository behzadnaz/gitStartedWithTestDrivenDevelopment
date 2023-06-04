package com.behzad.finances.persistence;

import com.behzad.finances.values.UserEnteredDollars;

import java.io.*;

import static org.mockito.BDDMockito.willReturn;

public class SaveFile {
    private File path;
    public SaveFile(File path) {
        this.path =path;
    }

    public void save(UserEnteredDollars startingBalance, UserEnteredDollars costBasis, UserEnteredDollars yearlySpending) throws IOException {
        Writer writer = new BufferedWriter(new FileWriter(path));
        try {
            writeLine(writer,"com.behzad.finances,1");
            writeLine(writer, startingBalance.getUserText());
            writeLine(writer,costBasis.getUserText() );
            writeLine(writer,yearlySpending.getUserText());
        } finally {
            writer.close();
        }
    }
    public void writeLine(Writer writer, String line)throws IOException{
        line = line.replace("\\", "\\\\");
        line = line.replace("\n", "\\n");
        writer.write(line+"\n");
    }
    
}
