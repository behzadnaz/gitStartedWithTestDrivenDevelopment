package com.behzad.finances;

import com.behzad.finances.domain.*;
import com.behzad.finances.ui.StockMarketTableModel;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame {

    public Application(){
        this.setSize(900,400);
        this.setLocation(400,300);

        Container content =this.getContentPane();
        content.add(table());
    }

    private JScrollPane table() {
        StockMarketTableModel model = new StockMarketTableModel(stockMarket());
        JTable table = new JTable(model);   
        return new JScrollPane(table);
    }

    private StockMarket stockMarket() {
        Year startingYear = new Year(2010);
        Year endingYear = new Year(2050);
        Dollars startingBalance = new Dollars(10000);
        Dollars startingPrincipal = new Dollars(7000);
        InterestRate interestRate = new InterestRate(10);
        TaxRate capitalGainsTaxRate = new TaxRate(25);
        return new StockMarket(startingYear, endingYear, startingBalance, startingPrincipal, interestRate, capitalGainsTaxRate);
    }

    public static void main(String[] args){
        new Application().setVisible(true);
    }
}
