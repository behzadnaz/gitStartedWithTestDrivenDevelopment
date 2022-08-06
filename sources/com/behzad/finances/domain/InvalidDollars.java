package com.behzad.finances.domain;

import javax.swing.*;
import java.net.URL;

public class InvalidDollars extends Dollars{

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Dollars plus(Dollars dollars) {
        return new InvalidDollars();
    }

    @Override
    public Dollars minus(Dollars dollars) {
        return new InvalidDollars();
    }

    @Override
    public Dollars subtractToZero(Dollars dollars) {
        return new InvalidDollars();
    }

    @Override
    public Dollars percentage(double dollars) {
        return new InvalidDollars();
    }

    @Override
    public Dollars min(Dollars value2) {
        return new InvalidDollars();
    }

    public void render(JLabel label) {
        URL iconUrl = getClass().getResource("invalid_dollars.png");
        label.setIcon(new ImageIcon(iconUrl,"Invalid dollar amount"));
        label.setText(null);
        label.setToolTipText(null);
    }

    @Override
    public String toString(){
        return "$???";
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof InvalidDollars);
    }

    @Override
    public int hashCode() {
        return 13;
    }


}
