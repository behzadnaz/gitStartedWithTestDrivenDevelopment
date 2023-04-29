package com.behzad.finances;
import com.behzad.finances.ui.*;

import javax.swing.*;


public class Application {

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ApplicationFrame.newWindow();
            }
        });
    }
}
