package com.behzad.finances.domain;

import java.text.NumberFormat;
import java.util.Locale;

public abstract class Dollars {

    protected double amount;

    public static Dollars parse(String text){
        boolean parenthesis = false;
        if (text.startsWith("(")) { text = text.substring(1); parenthesis = true;}
        if (text.endsWith(")")) { text = text.substring(0, text.length() - 1); parenthesis = true;}
        if (parenthesis) text = "-" + text;

        if(text.startsWith("$")) text = text.substring(1);
        if(text.startsWith("-$")) text = "-" + text.substring(2);
        if(text.isEmpty()) return new ValidDollars(0);
        if (text.equals("-")) return new ValidDollars(0);
        text = text.replace(",", "");
        return new ValidDollars(Double.parseDouble(text));
    }
    protected Dollars (int amount){
        this.amount = amount;
    }
    protected Dollars(double amount){
        this.amount = amount;
    }
    public abstract Dollars plus(Dollars dollars);
    public abstract Dollars minus(Dollars dollars);
    public abstract Dollars subtractToZero(Dollars dollars);

    public Dollars percentage(double percent) {
        return new ValidDollars(amount * percent / 100.0);
    }

    public static Dollars min(Dollars value1, Dollars value2) {
        return new ValidDollars(Math.min(value1.amount, value2.amount));
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
            return "("+convertNumberToString()+")";
        } else{
            return convertNumberToString();
        }
    }
    private String convertNumberToString() {
        long roundedAmount = roundOffPennies();
        roundedAmount = Math.abs(roundedAmount);
        return "$" + NumberFormat.getInstance(Locale.US).format(roundedAmount);
    }

    @Override
    public boolean equals(Object o) {
        Dollars that = (Dollars) o;
        return this.roundOffPennies() == that.roundOffPennies();
    }

    @Override
    public int hashCode() {
        return (int)roundOffPennies();
    }

}
