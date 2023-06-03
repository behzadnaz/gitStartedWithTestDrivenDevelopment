package com.behzad.finances.domain;

import com.behzad.finances.util.Require;
import com.behzad.finances.values.Dollars;
import com.behzad.finances.values.Year;

public class StockMarketProjection {
    private Year startingYear;
    private final Year endingYear;
    private final Dollars sellEveryYear;
    private StockMarketYear[] years;

    public StockMarketProjection(StockMarketYear firstYear, Year endingYear, Dollars sellEveryYear) {
        this.startingYear = firstYear.year();
        this.endingYear = endingYear;
        this.sellEveryYear = sellEveryYear;
        populateYears(firstYear);
    }

    private void populateYears(StockMarketYear firstYear) {
        this.years = new StockMarketYear[numberOfYears()];
        years[0] = firstYear;
        years[0].sell(sellEveryYear);
        for (int i = 1 ; i< numberOfYears(); i++){
            years[i] = years[i-1].nextYear();
            years[i].sell(sellEveryYear);

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
