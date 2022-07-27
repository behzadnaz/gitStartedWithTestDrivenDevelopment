package com.behzad.finances.domain;

public class ValidDollars extends Dollars{

    public ValidDollars (int amount){
        super(amount);
    }
    public ValidDollars(double amount){
        super(amount);
    }
    public Dollars plus(Dollars dollars) {
        return new ValidDollars(this.amount + dollars.amount);
    }
    public Dollars minus(Dollars dollars) {
        return new ValidDollars(this.amount - dollars.amount);
    }
    public Dollars subtractToZero(Dollars dollars) {
        double result = this.amount -dollars.amount;
        return new ValidDollars(Math.max(0, result));
    }
}
