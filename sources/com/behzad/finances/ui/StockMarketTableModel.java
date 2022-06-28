package com.behzad.finances.ui;

import com.behzad.finances.domain.StockMarket;
import com.behzad.finances.domain.StockMarketYear;

import javax.swing.table.*;

public class StockMarketTableModel extends AbstractTableModel {

    private static final long serialVersionUID= 1l;
    private static final String [] COLUMN_TITLES = {"Year", "Starting Balance", "Starting Principal", "Withdrawals", "Appreciation", "Ending Balance"};


    private StockMarket market;

    public StockMarketTableModel(StockMarket market) {
        this.market = market;
    }

    @Override
    public int getRowCount() {
        return market.numberOfYears();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_TITLES.length;
    }

    @Override
    public String getColumnName(int column){
        return COLUMN_TITLES[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        StockMarketYear currentYear = market.getYearOffset(rowIndex);

        switch(columnIndex){
            case 0: return currentYear.year();
            case 1: return currentYear.startingBalance();
            case 2: return currentYear.startingPrincipal();
            case 3: return currentYear.totalWithdrawn();
            case 4: return currentYear.appreciation();
            case 5: return currentYear.endingBalance();
            default:return "";
        }
    }
}
