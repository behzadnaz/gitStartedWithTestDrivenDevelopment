package com.behzad.finances.domain;

public class Dollars {

    private double amount;

    public Dollars (int amount){
        this.amount = (double) amount;
    }

    public Dollars(double amount){
        this.amount = amount;
    }
    public Dollars plus(Dollars dollars) {
        return new Dollars(this.amount + dollars.amount);
    }

    public Dollars minus(Dollars dollars) {
        return new Dollars(this.amount - dollars.amount);
    }
    public Dollars subtractToZero(Dollars dollars) {
        double result = this.amount -dollars.amount;
        return new Dollars(Math.max(0, result));
    }
    public Dollars percentage(double percent) {
        return new Dollars(amount * percent / 100.0);
    }

    public static Dollars min(Dollars value1, Dollars value2) {
        return new Dollars(Math.min(value1.amount, value2.amount));
    }

    private long roundOffPennies() {
        return Math.round(this.amount);
    }
    @Override
    public String toString() {
        return "$" + roundOffPennies();
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
