package com.behzad.finances.domain;

public class StockMarketYear {

    private Dollars startingBalance;
    private Dollars  startingPrincipal;
    private InterestRate interestRate;
    private TaxRate capitalGainsTaxRate;
    private Dollars totalWithdrawals;
    private Year year;

    public  StockMarketYear(Year year, Dollars startingBalance, Dollars startingPrincipal, InterestRate interestRate, TaxRate capitalGainsTaxRate){
        this.year = year;
        this.startingBalance = startingBalance;
        this.startingPrincipal = startingPrincipal;
        this.interestRate = interestRate;
        this.capitalGainsTaxRate = capitalGainsTaxRate;
        this.totalWithdrawals = new Dollars(0);
    }
    public Year year() {
        return year;
    }
    public Dollars startingBalance(){
        return startingBalance;
    }
    public Dollars startingPrincipal() {
        return startingPrincipal;
    }
    public InterestRate interestRate(){
        return interestRate;
    }
    public TaxRate capitalGainsTaxRate() {
        return capitalGainsTaxRate;
    }
    public void withdraw(Dollars amount){
        this.totalWithdrawals = totalWithdrawals.add(amount);
    }
    private Dollars capitalGainsWithdrawn(){
        return totalWithdrawals.subtractToZero(startingPrincipal());
    }
    public Dollars capitalGainsTaxIncurred(){
        return capitalGainsTaxRate.compoundTaxFor(capitalGainsWithdrawn());
    }
    public Dollars totalWithdrawn(){

        return totalWithdrawals.add(capitalGainsTaxIncurred());
    }
    public Dollars appreciation(){
        return interestRate.interestOn(startingBalance.subtract(totalWithdrawn()));
    }

    public Dollars endingBalance(){
        return startingBalance.subtract(totalWithdrawn()).add(appreciation());
    }
    public Dollars endingPrincipal(){

        return startingPrincipal.subtractToZero(totalWithdrawals);
    }

    public  StockMarketYear nextYear(){
        return new StockMarketYear(year.nextYear(), endingBalance(),this.endingPrincipal(), this.interestRate(), this.capitalGainsTaxRate());
    }

}
