package com.behzad.finances.domain;

import org.junit.*;
import static org.junit.Assert.*;

public class _StockMarketYearTest {

    public static final GrowthRate INTEREST_RATE = new GrowthRate(10);
    public static final Dollars STARTING_PRINCIPAL = ValidDollars.create(3000);
    public static final Dollars STARTING_BALANCE = ValidDollars.create(10000);
    private static final TaxRate CAPITAL_GAINS_TAX_RATE = new TaxRate(25);
    private static final Year YEAR = new Year(2010);

    @Test
    public void startingValues(){
        StockMarketYear year = newYear();
        assertEquals("year", YEAR,year.year());
        assertEquals("starting balance",STARTING_BALANCE,year.startingBalance());
        assertEquals("starting principal",STARTING_PRINCIPAL,year.startingCostBasis());
        assertEquals("interest rate", INTEREST_RATE, year.growthRate());
        assertEquals("capital gains tax rate", CAPITAL_GAINS_TAX_RATE, year.capitalGainsTaxRate());
        assertEquals("total withdrawn default", ValidDollars.create(0),year.totalSold());
    }
    @Test
    public void totalSold(){
        StockMarketYear year = newYear();
        assertEquals("nothing sold", ValidDollars.create(0),year.totalSellOrders());
        year.sell(ValidDollars.create(3000));
        assertEquals("one thing sold",ValidDollars.create(3000), year.totalSellOrders());
        year.sell(ValidDollars.create(750));
        year.sell(ValidDollars.create(1350));
        assertEquals("multiple sales", ValidDollars.create(5100), year.totalSellOrders());
    }
    @Test
    public void capitalGainsTax(){
        StockMarketYear year =  newYear();
        year.sell(ValidDollars.create(4000));
        assertEquals("capital gains tax include tax on withdrawal to cover capital gains", ValidDollars.create(1333), year.capitalGainsTaxIncurred());
        assertEquals("total withdrawn includes capital gains tax", ValidDollars.create(5333), year.totalSold());
    }

    @Test
    public void treatAllWithdrawalsAsSubjectToCapitalGainsTaxUntilAllCapitalGainsHaveBeenSold(){
        StockMarketYear year =  newYear();
        Dollars capitalGains = STARTING_BALANCE.minus(STARTING_PRINCIPAL);
        year.sell(ValidDollars.create(500));
        assertEquals("pay tax on all entire withdrawal", ValidDollars.create(167), year.capitalGainsTaxIncurred());
        year.sell(capitalGains);
        assertEquals("pay compounding tax  on capital gains even though that extra tax even when compounded amount is not capital gains",ValidDollars.create(2333),year.capitalGainsTaxIncurred());
        year.sell(ValidDollars.create(1000));
        assertEquals("pay no more tax once all capital gains withdrawn",ValidDollars.create(2333),year.capitalGainsTaxIncurred());
    }

    @Test
    public void interestEarned(){
        StockMarketYear year = newYear();
        assertEquals("basic interest earned", ValidDollars.create(1000),year.growth());
        year.sell(ValidDollars.create(2000));
        assertEquals("withdrawal (which pay capital gains tax) don't earn interest", ValidDollars.create(733),year.growth());
    }
    @Test
    public void endingPrincipal(){
        StockMarketYear year = newYear();
        // Dollars capitalGains = STARTING_BALANCE.minus(STARTING_PRINCIPAL);
        year.sell(ValidDollars.create(500));
        assertEquals( "withdrawals less than capital gains don't reduce principle",STARTING_PRINCIPAL, year.endingCostBasis());
        year.sell(ValidDollars.create(6500));
        Dollars totalWithdrawn = ValidDollars.create(9333);
        Dollars capitalGains = ValidDollars.create(7000);
        Dollars principalReducedBy= totalWithdrawn.minus(capitalGains);
        Dollars expectedPrincipal =STARTING_PRINCIPAL.minus(principalReducedBy);
        assertEquals("principle should be reduced by difference between total withdrawals and capital gains",expectedPrincipal, year.endingCostBasis());
        year.sell(ValidDollars.create(1000));
        assertEquals("principal goes negative when we are overdrawn",ValidDollars.create(-333), year.endingCostBasis());
    }

    /*@Test
    @Ignore
    public void endingPrincipal(){
        StockMarketYear year =newYear();
        year.withdraw(ValidDollars.create(1000));
        assertEquals("principal considers withdrawal", ValidDollars.create(2000), year.endingPrincipal());
        year.withdraw(ValidDollars.create(500));
        assertEquals("ending principal considers total multiple withdrawal", ValidDollars.create(1500), year.endingPrincipal());
        year.withdraw(ValidDollars.create(3000));
        assertEquals("ending principal neve goes below zero",ValidDollars.create(0),year.endingPrincipal());
    }*/

    @Test
    public void endingBalance(){
        StockMarketYear year =newYear();
        assertEquals("ending balance includes interest",ValidDollars.create(11000), year.endingBalance());
        year.sell(ValidDollars.create(1000));
        assertEquals("ending balance includes withdrawal (which pay capital gain tax) and interest",ValidDollars.create(9533),year.endingBalance());
    }

    @Test
    public void nextYearStartingValuesMatchesThisYearsEndingValues(){
        StockMarketYear thisYear = newYear();
        StockMarketYear nextYear = thisYear.nextYear();
        assertEquals("year", new Year(2011), nextYear.year());
        assertEquals("starting balance", thisYear.endingBalance(), nextYear.startingBalance());
        assertEquals("starting principal", thisYear.endingCostBasis(), nextYear.startingCostBasis());
        assertEquals("interest",thisYear.growthRate(), nextYear.growthRate());
        assertEquals("capital gains tax rate", thisYear.capitalGainsTaxRate(), nextYear.capitalGainsTaxRate());
    }

    @Test
    @Ignore
    public void withdrawnFundsDoNotEarnInterest(){
        StockMarketYear year = newYear();
        year.sell(ValidDollars.create(1000));
        assertEquals(ValidDollars.create(900), year.growth());
    }

    private StockMarketYear newYear() {
        return new StockMarketYear(YEAR,STARTING_BALANCE, STARTING_PRINCIPAL, INTEREST_RATE, CAPITAL_GAINS_TAX_RATE);
    }

}