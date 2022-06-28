package com.behzad.finances.domain;


import java.util.Objects;

public class TaxRate {

    private double rate;

    public TaxRate(double rateAsPercentage) {
        this.rate = rateAsPercentage / 100.0;
    }
    public Dollars simpleTaxFor(Dollars amount){
        return new Dollars((int) (rate * amount.toInt()));
    }

    public Dollars compoundTaxFor(Dollars amount) {
        int amountAsInt = amount.toInt();
        return new Dollars((int)((amountAsInt / (1 - rate)) - amountAsInt));
    }

    @Override
    public String toString(){
        return (rate * 100) +"%";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxRate taxRate = (TaxRate) o;
        return Double.compare(taxRate.rate, rate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate);
    }
}
