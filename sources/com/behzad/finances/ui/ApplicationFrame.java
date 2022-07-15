package com.behzad.finances.ui;

import com.behzad.finances.domain.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import  java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class ApplicationFrame extends JFrame {

    public static final long serialVersionID = 1L;
    public static final String TITLE = "Financial Projector";
    public static final Point INITIAL_POSITION = new Point(400, 300);
    public static final Dimension INITIAL_SIZE = new Dimension(900,400);
    private JTable forecastTable;

    public ApplicationFrame(){
        super(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(INITIAL_POSITION);
        setSize(INITIAL_SIZE);
        addComponents();
    }

    private void addComponents() {
        Container contentPane = getContentPane();
        Component forecastTable = forecastTable();
        contentPane.add(BorderLayout.CENTER, forecastTable);
        contentPane.add(BorderLayout.NORTH, startingBalanceField());
    }

    private JTextField startingBalanceField() {
        JTextField field = new JTextField();
        return field;
    }

    private Component forecastTable() {
        return  new JScrollPane(new ForecastTable(stockMarketTableModel()));
    }

    private StockMarketTableModel stockMarketTableModel() {
        return new StockMarketTableModel(stockMarket());
    }

    private StockMarketProjection stockMarket() {
        Year startingYear = new Year(2010);
        Year endingYear = new Year(2050);
        Dollars startingBalance = new Dollars(10000);
        Dollars startingPrincipal = new Dollars(7000);
        GrowthRate interestRate = new GrowthRate(10);
        TaxRate capitalGainsTaxRate = new TaxRate(25);
        return new StockMarketProjection(startingYear, endingYear, startingBalance, startingPrincipal, interestRate, capitalGainsTaxRate, new Dollars(695));
    }
}
