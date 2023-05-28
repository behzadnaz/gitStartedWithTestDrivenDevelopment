package com.behzad.finances.persistence;

import com.behzad.finances.domain.Dollars;

import java.io.*;

public class SaveFile {
    private File path;
    public SaveFile(File path) {
        this.path =path;
    }

    public void save(Dollars startingBalance) throws IOException {
        Writer writer = new BufferedWriter(new FileWriter(path));
        try {
            writer.write("com.behzad.finances,1\n");
            writer.write(startingBalance.toString() + "\n");
        } finally {
            writer.close();
        }
    }
}
