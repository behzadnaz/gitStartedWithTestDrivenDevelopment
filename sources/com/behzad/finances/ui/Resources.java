package com.behzad.finances.ui;

import javax.swing.*;
import java.net.URL;

public class Resources {

    public ImageIcon invalidDollarIcon() {
        URL iconUrl = getClass().getResource("resources/invalid_dollars.png");
        return new ImageIcon(iconUrl, "Invalid dollar amount");
    }

}
