package com.behzad.finances.values;

import java.util.Objects;

public class Year {

    private int year;

    public Year(int year){

        this.year = year;
    }

    public Year nextYear() {
        return new Year(year +1);
    }
    public int numberOfYearsInclusive(Year endingYear) {
        return endingYear.year - this.year +1;
    }

    public String toString(){
        return "" +year;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Year year1 = (Year) o;
        return year == year1.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(year);
    }



}
