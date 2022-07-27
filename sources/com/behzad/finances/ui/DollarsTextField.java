package com.behzad.finances.ui;

import com.behzad.finances.domain.Dollars;
import org.junit.Assume;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.text.ParseException;

//if  you ever want to subclass this class, be careful of race conditions with the event handler in
//the constructor.It could execute before subclass constructor
public final class DollarsTextField extends JFormattedTextField {
    private static final long serialVersionUID = 1L;

    public DollarsTextField(Dollars dollars) {
        this.setText(dollars.toString());
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                DollarsTextField.this.setText(getDollars().toString());
            }
        });
    }

    public Dollars getDollars() {
        return Dollars.parse(getText());

    }
}
