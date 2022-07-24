package com.behzad.finances.ui;

import com.behzad.finances.domain.Dollars;

import javax.swing.*;

public class DollarsTextField extends JFormattedTextField {
    private static final long serialVersionUID = 1L;
    private final Dollars dollars;

    public DollarsTextField(Dollars dollars) {
        this.dollars = dollars;
        this.add(new JFormattedTextField());
    }

    public Dollars getDollars() {
        return dollars;
    }
}
