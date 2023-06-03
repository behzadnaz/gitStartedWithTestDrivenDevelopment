package com.behzad.finances.values;

import com.behzad.finances.util.Require;

import java.util.Objects;

public class GrowthRate {

    private  double rateAsPercentage;

    public GrowthRate(double rateAsPercentage){
        Require.that(rateAsPercentage > 0 , "tax rate must be positive; was" + rateAsPercentage);
        this.rateAsPercentage =rateAsPercentage;
    }

    public Dollars growthFor(Dollars amount){
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
        GrowthRate that = (GrowthRate) o;
        return Double.compare(that.rateAsPercentage, rateAsPercentage) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rateAsPercentage);
    }
}
