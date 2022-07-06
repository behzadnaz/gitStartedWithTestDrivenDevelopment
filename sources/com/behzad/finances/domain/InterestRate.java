package com.behzad.finances.domain;

import com.behzad.finances.util.Require;

import java.util.Objects;

public class InterestRate {

    private  double rateAsPercentage;

    public InterestRate(double rateAsPercentage){
        Require.that(rateAsPercentage > 0 , "tax rate must be positive; was" + rateAsPercentage);
        this.rateAsPercentage =rateAsPercentage;
    }

    public Dollars interestOn(Dollars amount){
        return amount.percentage(rateAsPercentage);
    }

    @Override
    public String toString(){
       return rateAsPercentage + "%";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestRate that = (InterestRate) o;
        return Double.compare(that.rateAsPercentage, rateAsPercentage) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rateAsPercentage);
    }
}
