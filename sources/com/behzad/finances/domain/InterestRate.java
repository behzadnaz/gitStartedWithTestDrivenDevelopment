package com.behzad.finances.domain;

import java.util.Objects;

public class InterestRate {

    private  double rate;

    public InterestRate(double rateAsPercentage){
      rate =rateAsPercentage/100;
    }

    public Dollars interestOn(Dollars amount){
        return new Dollars((int)(amount.toInt() * rate));
    }

    @Override
    public String toString(){
       return (rate * 100) + "%";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestRate that = (InterestRate) o;
        return Double.compare(that.rate, rate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate);
    }
}
