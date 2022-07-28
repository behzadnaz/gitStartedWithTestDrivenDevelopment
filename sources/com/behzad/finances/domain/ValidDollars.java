package com.behzad.finances.domain;

import java.text.NumberFormat;
import java.util.Locale;

public class ValidDollars extends Dollars{
    private double amount;

    public ValidDollars (int amount){
        this.amount = amount;
    }
    public ValidDollars(double amount){
        this.amount = amount;
    }

    private double amount(Dollars dollars){
        return ((ValidDollars)dollars).amount;
    }

    public boolean isValid() {
        return true;
    }

    public Dollars plus(Dollars dollars) {
        if (!dollars.isValid()) return new InvalidDollars();
        return new ValidDollars(this.amount + amount(dollars));
    }
    public Dollars minus(Dollars dollars) {
        if (!dollars.isValid()) return new InvalidDollars();
        return new ValidDollars(this.amount - amount(dollars));
    }
    public Dollars subtractToZero(Dollars dollars) {
        if (!dollars.isValid()) return new InvalidDollars();
        double result = this.amount -amount(dollars);
        return new ValidDollars(Math.max(0, result));
    }
    public Dollars percentage(double percent) {
        return new ValidDollars(amount * percent / 100.0);
    }

    public Dollars min(Dollars value2) {
        if (!value2.isValid()) return new InvalidDollars();
        return new ValidDollars(Math.min(this.amount, amount(value2)));
    }
    private boolean isNegative() {
        return amount < 0;
    }

    private long roundOffPennies() {
        return Math.round(this.amount);
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
    public boolean equals(Object o) {
        ValidDollars that = (ValidDollars) o;
        return this.roundOffPennies() == that.roundOffPennies();
    }

    @Override
    public int hashCode() {
        return (int)roundOffPennies();
    }
}
