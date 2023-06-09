package com.behzad.finances.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SaveAsDialog extends FileDialog {
    private static final long serialVersionUIID = 1L;
    private ApplicationModel model;

    public SaveAsDialog(Frame parentWindow, ApplicationModel model) {
        super(parentWindow, "Save as", FileDialog.SAVE);
        this.model = model;

    }
    public void displayModally() {
        this.setVisible(true);
        this.doSave();
    }
    //non-private for testing purposes
    public void doSave(){
        try {
            String directory = this.getDirectory();
            String file = this.getFile();
            if(file != null) model.save(new File(directory, file));
        }catch (IOException e){
            JOptionPane.showMessageDialog(this.getParent(), "Could not save file:" + e.getLocalizedMessage(),"Save File", JOptionPane.WARNING_MESSAGE);
        }
    }

}
