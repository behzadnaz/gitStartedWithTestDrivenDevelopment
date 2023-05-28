package com.behzad.finances.ui;

import com.behzad.finances.domain.Dollars;

import java.io.File;

public class __ApplicationModelSpy extends ApplicationModel {
    public Dollars setStartingBalanceCalledWith;
    public Dollars setCostBasisCalledWith;
    public Dollars setYearlySpendingCalledWith;
    public File saveCalledWith;

    @Override
    public void setStartingBalance(Dollars startingBalance) {
        setStartingBalanceCalledWith = startingBalance;
    }

    @Override
    public void setStartingCostBasis(Dollars startingCostBasis) {
        setCostBasisCalledWith = startingCostBasis;
    }

    @Override
    public void setYearlySpending(Dollars yearlySpending) {
        setYearlySpendingCalledWith = yearlySpending;
    }
    @Override
    public void save(File saveFile){
        saveCalledWith = saveFile;
    }
}
