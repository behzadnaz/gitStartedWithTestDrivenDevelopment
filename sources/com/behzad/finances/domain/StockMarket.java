package com.behzad.finances.domain;

import com.behzad.finances.util.Require;

public class StockMarket {
    private Year startingYear;
    private Year endingYear;
    private StockMarketYear[] years;

    public StockMarket(Year startingYear, Year endingYear, Dollars startingBalance, Dollars startingPrincipal, InterestRate interestRate, TaxRate capitalGainsTaxRate) {
        this.startingYear = startingYear;
        this.endingYear = endingYear;
        populateYears(startingBalance,startingPrincipal,interestRate, capitalGainsTaxRate);
    }

    private void populateYears(Dollars startingBalance, Dollars startingPrincipal, InterestRate interestRate, TaxRate capitalGainsTaxRate) {
        this.years = new StockMarketYear[numberOfYears()];
        years[0] = new StockMarketYear(startingYear, startingBalance, startingPrincipal, interestRate, capitalGainsTaxRate);
        for (int i = 1 ; i< numberOfYears(); i++){
            years[i] = years[i-1].nextYear();
        }
    }

    public int numberOfYears() {
        return startingYear.numberOfYearsInclusive(endingYear);
    }

    public StockMarketYear getYearOffset(int offSet) {
        Require.that(offSet >=0 && offSet < numberOfYears(),"offset needs to be between 0 and" + (numberOfYears()-1) + "; was" + offSet);
        return years[offSet];
    }


}
