package com.behzad.finances.values;

import com.behzad.finances.ui.RenderTarget;
import com.behzad.finances.ui.Resources;
import com.behzad.finances.util.UnreachableCodeException;


public class InvalidDollars extends Dollars{

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    protected double toCoreDataType() {
        throw new UnreachableCodeException();
    }

    @Override
    public Dollars plus(Dollars operand) {
        return new InvalidDollars();
    }

    @Override
    public Dollars minus(Dollars operand) {
        return new InvalidDollars();
    }

    @Override
    public Dollars subtractToZero(Dollars operand) {
        return new InvalidDollars();
    }

    @Override
    public Dollars percentage(double dollars) {
        return new InvalidDollars();
    }

    @Override
    public Dollars min(Dollars operand) {
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
        if (obj == null) return false;
        Dollars that =  (Dollars) obj;

        return !that.isValid();
    }

    @Override
    public int hashCode() {
        return 13;
    }

}
