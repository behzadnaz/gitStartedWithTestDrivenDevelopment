package com.behzad.finances.domain;

import com.behzad.finances.domain.Dollars;
import com.behzad.finances.domain.InterestRate;
import com.behzad.finances.domain.StockMarketYear;
import com.behzad.finances.domain.TaxRate;
import org.junit.*;
import static org.junit.Assert.*;

public class _StockMarketYearTest {

    public static final InterestRate INTEREST_RATE = new InterestRate(10);
    public static final Dollars STARTING_PRINCIPAL = new Dollars(3000);
    public static final Dollars STARTING_BALANCE = new Dollars(10000);
    private static final TaxRate CAPITAL_GAINS_TAX_RATE = new TaxRate(25);
    private static final Year YEAR = new Year(2010);

    @Test
    public void startingValues(){
        StockMarketYear year = newYear();
        assertEquals("year", YEAR,year.year());
        assertEquals("starting balance",STARTING_BALANCE,year.startingBalance());
        assertEquals("starting principal",STARTING_PRINCIPAL,year.startingPrincipal());
        assertEquals("interest rate", INTEREST_RATE, year.interestRate());
        assertEquals("capital gains tax rate", CAPITAL_GAINS_TAX_RATE, year.capitalGainsTaxRate());
        assertEquals("total withdrawn default", new Dollars(0),year.totalWithdrawn());
    }

    @Test
    public void capitalGainsTax(){
        StockMarketYear year =  newYear();
        year.withdraw(new Dollars(4000));
        assertEquals("capital gains tax include tax on withdrawal to cover capital gains", new Dollars(333), year.capitalGainsTaxIncurred());
        assertEquals("total withdrawn includes capital gains tax", new Dollars(4333), year.totalWithdrawn());
    }

    @Test
    public void interestEarned(){
        StockMarketYear year =  newYear();
        assertEquals("basic interest earned", new Dollars(1000),year.appreciation());
        year.withdraw(new Dollars(2000));
        assertEquals("withdrawal don't earn interest", new Dollars(800),year.appreciation());
        year.withdraw(new Dollars(2000));
        assertEquals("capital gains tax withdrawals don't earn tax",new Dollars(566), year.appreciation());
    }

    @Test
    public void endingPrincipal(){
        StockMarketYear year =newYear();
        year.withdraw(new Dollars(1000));
        assertEquals("principal considers withdrawal", new Dollars(2000), year.endingPrincipal());
        year.withdraw(new Dollars(500));
        assertEquals("ending principal considers total multiple withdrawal", new Dollars(1500), year.endingPrincipal());
        year.withdraw(new Dollars(3000));
        assertEquals("ending principal neve goes below zero",new Dollars(0),year.endingPrincipal());
    }

    @Test
    public void endingBalance(){
        StockMarketYear year =newYear();
        assertEquals("ending balance includes interest",new Dollars(11000), year.endingBalance());
        year.withdraw(new Dollars(1000));
        assertEquals("ending balance includes withdrawal",new Dollars(9900),year.endingBalance());
        year.withdraw(new Dollars(3000));
        assertEquals(" ending balance include capital gains tax withdrawals",new Dollars(6233),year.endingBalance());
    }

    @Test
    public void nextYearStartingValuesMatchesThisYearsEndingValues(){
        StockMarketYear thisYear = newYear();
        StockMarketYear nextYear = thisYear.nextYear();
        assertEquals("year", new Year(2011), nextYear.year());
        assertEquals("starting balance", thisYear.endingBalance(), nextYear.startingBalance());
        assertEquals("starting principal", thisYear.endingPrincipal(), nextYear.startingPrincipal());
        assertEquals("interest",thisYear.interestRate(), nextYear.interestRate());
        assertEquals("capital gains tax rate", thisYear.capitalGainsTaxRate(), nextYear.capitalGainsTaxRate());
    }

    @Test
    public void withdrawnFundsDoNotEarnInterest(){
        StockMarketYear year = newYear();
        year.withdraw(new Dollars(1000));
        assertEquals(new Dollars(900), year.appreciation());
    }

    private StockMarketYear newYear() {
        return new StockMarketYear(YEAR,STARTING_BALANCE, STARTING_PRINCIPAL, INTEREST_RATE, CAPITAL_GAINS_TAX_RATE);
    }

}