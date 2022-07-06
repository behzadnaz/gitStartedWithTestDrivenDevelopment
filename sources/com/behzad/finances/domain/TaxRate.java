package com.behzad.finances.domain;


import com.behzad.finances.util.Require;

import java.util.Objects;

public class TaxRate {

    private double rateAsPercentage;

    public TaxRate(double rateAsPercentage) {
        Require.that(rateAsPercentage > 0 , "tax rate must be positive; was" + rateAsPercentage);
        this.rateAsPercentage = rateAsPercentage;
    }
    public Dollars simpleTaxFor(Dollars amount){
        return amount.percentage(rateAsPercentage);
    }

    public Dollars compoundTaxFor(Dollars amount) {
        double compoundRate = (100.0 /(100.0 -rateAsPercentage)) - 1;
        return amount.percentage(compoundRate * 100);
    }

    @Override
    public String toString(){
        return (rateAsPercentage) +"%";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxRate taxRate = (TaxRate) o;
        return Double.compare(taxRate.rateAsPercentage, rateAsPercentage) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rateAsPercentage);
    }
}
