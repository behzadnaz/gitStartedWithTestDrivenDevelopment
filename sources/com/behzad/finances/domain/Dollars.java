package com.behzad.finances.domain;

import java.util.Objects;

public class Dollars {

    private int amount;

    public Dollars(int amount){
        this.amount = amount;
    }
    public int toInt(){
        return amount;
    }
    public Dollars add(Dollars dollars) {
        return new Dollars(this.amount + dollars.amount);
    }

    public Dollars subtract(Dollars dollars) {
        return new Dollars(this.amount - dollars.amount);
    }
    public Dollars subtractToZero(Dollars dollars) {
        int result = this.amount -dollars.amount;
        return new Dollars(Math.max(0, result));
    }

    @Override
    public String toString() {
        return "$" + amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dollars dollars = (Dollars) o;
        return amount == dollars.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }


}
