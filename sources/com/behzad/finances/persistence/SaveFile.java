package com.behzad.finances.persistence;

import com.behzad.finances.values.UserEnteredDollars;

import java.io.*;

public class SaveFile {
    private File path;
    public SaveFile(File path) {
        this.path =path;
    }

    public void save(UserEnteredDollars startingBalance, UserEnteredDollars costBasis, UserEnteredDollars yearlySpending) throws IOException {
        Writer writer = new BufferedWriter(new FileWriter(path));
        try {
            writer.write("com.behzad.finances,1\n");
            writer.write(startingBalance.getUserText() + "\n");
            writer.write(costBasis.getUserText() + "\n");
            writer.write(yearlySpending.getUserText()+ "\n");
        } finally {
            writer.close();
        }
    }
}
