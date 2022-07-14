package com.behzad.finances.ui;

import com.behzad.finances.domain.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import  java.awt.*;

public class ApplicationFrame extends JFrame {

    public static final long serialVersionID = 1L;
    public static final String TITLE = "Financial Projector";
    public static final Point INITIAL_POSITION = new Point(400, 300);
    public static final Dimension INITIAL_SIZE = new Dimension(900,400);

    public ApplicationFrame(){
        super(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(INITIAL_POSITION);
        setSize(INITIAL_SIZE);
        getContentPane().add(forecastTable());
    }

    private Component forecastTable() {
        return  new JScrollPane(new ForecastTable(new StockMarketTableModel(stockMarket())));
    }

    private StockMarket stockMarket() {
        Year startingYear = new Year(2010);
        Year endingYear = new Year(2050);
        Dollars startingBalance = new Dollars(10000);
        Dollars startingPrincipal = new Dollars(7000);
        GrowthRate interestRate = new GrowthRate(10);
        TaxRate capitalGainsTaxRate = new TaxRate(25);
        return new StockMarket(startingYear, endingYear, startingBalance, startingPrincipal, interestRate, capitalGainsTaxRate, new Dollars(695));
    }
}
