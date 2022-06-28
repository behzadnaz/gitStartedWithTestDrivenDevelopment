package com.behzad.finances.domain;

import com.behzad.finances.domain.Dollars;
import com.behzad.finances.domain.InterestRate;
import com.behzad.finances.domain.StockMarket;
import com.behzad.finances.domain.TaxRate;
import org.junit.*;
import static org.junit.Assert.*;

public class _StockMarketTest {

    private static final Year STARTING_YEAR = new Year(2010);
    private static final Year ENDING_YEAR = new Year(2050);
    private static final Dollars STARTING_BALANCE = new Dollars(10000);
    private static final Dollars STARTING_PRINCIPAL = new Dollars(7000);
    private static final InterestRate INTEREST_RATE = new InterestRate(10);
    private static final TaxRate CAPITAL_GAINS_TAX_RATE = new TaxRate(25);

    @Test
    public void stockMarketContainsMultipleYears(){
        StockMarket account = new StockMarket(STARTING_YEAR, ENDING_YEAR,STARTING_BALANCE,STARTING_PRINCIPAL, INTEREST_RATE,CAPITAL_GAINS_TAX_RATE);
        assertEquals("# of years", 41, account.numberOfYears());
        assertEquals(STARTING_BALANCE, account.getYearOffset(0).startingBalance());
        assertEquals(new Dollars(11000), account.getYearOffset(1).startingBalance());
        assertEquals(new Dollars(12100), account.getYearOffset(2).startingBalance());
        assertEquals(new Year(2050), account.getYearOffset(40).year());
    }
}
