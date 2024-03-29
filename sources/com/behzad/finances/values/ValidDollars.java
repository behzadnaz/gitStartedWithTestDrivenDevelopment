package com.behzad.finances.values;

import com.behzad.finances.ui.RenderTarget;
import com.behzad.finances.ui.Resources;
import com.behzad.finances.util.Require;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class ValidDollars extends Dollars {

    private double amount;

    public ValidDollars(double rangeLimitedAmount){
        Require.that(inRange(rangeLimitedAmount), "dollar rangeLimitedAmount [" + rangeLimitedAmount + "] outside valid range");
        this.amount = rangeLimitedAmount;
    }

    public boolean isValid() {
        return true;
    }
    @Override
    protected double toCoreDataType() {
        return amount;
    }

    public Dollars plus(Dollars operand) {
        if (!operand.isValid()) return new InvalidDollars();
        if(operand instanceof UserEnteredDollars) return operand.plus(this);
        return create(this.amount + operand.toCoreDataType());
    }
    public Dollars minus(Dollars operand) {
        if (!operand.isValid()) return new InvalidDollars();
        return create(this.amount- operand.toCoreDataType());
    }
    public Dollars subtractToZero(Dollars operand) {
        if (!operand.isValid()) return new InvalidDollars();
        double result = this.amount - operand.toCoreDataType();
        return create(Math.max(0, result));
    }
    public Dollars percentage(double percent) {
        return create(amount * percent / 100.0);
    }

    public Dollars min(Dollars operand) {
        if (!operand.isValid()) return new InvalidDollars();
        return new ValidDollars(Math.min(this.amount, operand.toCoreDataType()));
    }
    private boolean isNegative() {
        return amount < 0;
    }

    private long roundOffPennies() {
        return roundOffPennies(this.amount);
    }

    private long roundOffPennies(double amount) {
        return Math.round(amount);
    }

    public void render(Resources resources, RenderTarget target){
        target.setText(this.toString());
        target.setIcon(null, null);
        target.setForeGroundColor(Color.BLACK);
        if(amount < 0) target.setForeGroundColor(Color.red);
    }

    @Override
    public String toString() {
        if(isNegative()){
            return "("+ toAbsoluteValueString()+")";
        } else{
            return toAbsoluteValueString();
        }
    }
    private String toAbsoluteValueString() {
        long roundedAmount = roundOffPennies();
        roundedAmount = Math.abs(roundedAmount);
        return "$" + NumberFormat.getInstance(Locale.US).format(roundedAmount);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)  return false;
        Dollars that = (Dollars) obj;

        if(!that.isValid()) return false;
        return roundOffPennies(this.toCoreDataType()) == roundOffPennies(that.toCoreDataType());
    }

    @Override
    public int hashCode() {
        return (int)roundOffPennies();
    }


}
