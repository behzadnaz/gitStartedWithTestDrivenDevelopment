package com.behzad.finances.domain;

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
    public void totalSold(){
        StockMarketYear year = newYear();
        assertEquals("nothing sold", new Dollars(0),year.totalSold());
        year.sell(new Dollars(3000));
        assertEquals("one thing sold",new Dollars(3000), year.totalSold());
        year.sell(new Dollars(750));
        year.sell(new Dollars(1350));
        assertEquals("multiple sales", new Dollars(5100), year.totalSold());
    }

    @Test
    public void capitalGainsTax(){
        StockMarketYear year =  newYear();
        year.sell(new Dollars(4000));
        assertEquals("capital gains tax include tax on withdrawal to cover capital gains", new Dollars(1333), year.capitalGainsTaxIncurred());
        assertEquals("total withdrawn includes capital gains tax", new Dollars(5333), year.totalWithdrawn());
    }

    @Test
    public void treatAllWithdrawalsAsSubjectToCapitalGainsTaxUntilAllCapitalGainsHaveBeenSold(){
        StockMarketYear year =  newYear();
        Dollars capitalGains = STARTING_BALANCE.minus(STARTING_PRINCIPAL);
        year.sell(new Dollars(500));
        assertEquals("pay tax on all entire withdrawal", new Dollars(167), year.capitalGainsTaxIncurred());
        year.sell(capitalGains);
        assertEquals("pay compounding tax  on capital gains even though that extra tax even when compounded amount is not capital gains",new Dollars(2333),year.capitalGainsTaxIncurred());
        year.sell(new Dollars(1000));
        assertEquals("pay no more tax once all capital gains withdrawn",new Dollars(2333),year.capitalGainsTaxIncurred());
    }

    @Test
    public void interestEarned(){
        StockMarketYear year = newYear();
        assertEquals("basic interest earned", new Dollars(1000),year.appreciation());
        year.sell(new Dollars(2000));
        assertEquals("withdrawal (which pay capital gains tax) don't earn interest", new Dollars(733),year.appreciation());
    }
    @Test
    public void endingPrincipal(){
        StockMarketYear year = newYear();
        // Dollars capitalGains = STARTING_BALANCE.minus(STARTING_PRINCIPAL);
        year.sell(new Dollars(500));
        assertEquals( "withdrawals less than capital gains don't reduce principle",STARTING_PRINCIPAL, year.endingPrincipal());
        year.sell(new Dollars(6500));
        Dollars totalWithdrawn = new Dollars(9333);
        Dollars capitalGains = new Dollars(7000);
        Dollars principalReducedBy= totalWithdrawn.minus(capitalGains);
        Dollars expectedPrincipal =STARTING_PRINCIPAL.minus(principalReducedBy);
        assertEquals("principle should be reduced by difference between total withdrawals and capital gains",expectedPrincipal, year.endingPrincipal());
        year.sell(new Dollars(1000));
        assertEquals("principal goes negative when we are overdrawn",new Dollars(-333), year.endingPrincipal());
    }

    /*@Test
    @Ignore
    public void endingPrincipal(){
        StockMarketYear year =newYear();
        year.withdraw(new Dollars(1000));
        assertEquals("principal considers withdrawal", new Dollars(2000), year.endingPrincipal());
        year.withdraw(new Dollars(500));
        assertEquals("ending principal considers total multiple withdrawal", new Dollars(1500), year.endingPrincipal());
        year.withdraw(new Dollars(3000));
        assertEquals("ending principal neve goes below zero",new Dollars(0),year.endingPrincipal());
    }*/

    @Test
    public void endingBalance(){
        StockMarketYear year =newYear();
        assertEquals("ending balance includes interest",new Dollars(11000), year.endingBalance());
        year.sell(new Dollars(1000));
        assertEquals("ending balance includes withdrawal (which pay capital gain tax) and interest",new Dollars(9533),year.endingBalance());
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
    @Ignore
    public void withdrawnFundsDoNotEarnInterest(){
        StockMarketYear year = newYear();
        year.sell(new Dollars(1000));
        assertEquals(new Dollars(900), year.appreciation());
    }

    private StockMarketYear newYear() {
        return new StockMarketYear(YEAR,STARTING_BALANCE, STARTING_PRINCIPAL, INTEREST_RATE, CAPITAL_GAINS_TAX_RATE);
    }

}