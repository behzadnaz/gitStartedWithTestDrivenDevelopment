package com.behzad.finances.ui;

import com.behzad.finances.values.Dollars;
import com.behzad.finances.values.UserEnteredDollars;

import java.io.File;
import java.io.IOException;

public class __ApplicationModelSpy extends ApplicationModel {
    public UserEnteredDollars setStartingBalanceCalledWith;
    public UserEnteredDollars setCostBasisCalledWith;
    public UserEnteredDollars setYearlySpendingCalledWith;
    public File saveCalledWith;

    @Override
    public void setStartingBalance(UserEnteredDollars startingBalance) {
        setStartingBalanceCalledWith = startingBalance;
    }

    @Override
    public void setStartingCostBasis(UserEnteredDollars startingCostBasis) {
        setCostBasisCalledWith = startingCostBasis;
    }

    @Override
    public void setYearlySpending(UserEnteredDollars yearlySpending) {
        setYearlySpendingCalledWith = yearlySpending;
    }
    @Override
    public void save(File saveFile) throws IOException {
        saveCalledWith = saveFile;
    }
}
