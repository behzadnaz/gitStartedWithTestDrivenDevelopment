package com.behzad.finances.domain;

import com.behzad.finances.ui.RenderTarget;
import com.behzad.finances.ui.Resources;


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

    public void render(Resources resources, RenderTarget target) {
        target.setIcon(resources.invalidDollarIcon(), "invalid dollar amount");
        target.setText(null);
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
