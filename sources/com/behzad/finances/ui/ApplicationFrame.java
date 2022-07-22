package com.behzad.finances.ui;

import com.behzad.finances.domain.Dollars;

import javax.swing.*;
import  java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationFrame extends JFrame {

    public static final long serialVersionID = 1L;
    public static final String TITLE = "Financial Projector";
    public static final Point INITIAL_POSITION = new Point(400, 300);
    public static final Dimension INITIAL_SIZE = new Dimension(900,400);
    public ApplicationModel applicationModel;

    public ApplicationFrame(ApplicationModel applicationModel){
        super(TITLE);
        this.applicationModel = applicationModel;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(INITIAL_POSITION);
        setSize(INITIAL_SIZE);
        addComponents();
    }

    private void addComponents() {
        Container contentPane = getContentPane();
        Component forecastTable = forecastTable();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(BorderLayout.CENTER, forecastTable);
        contentPane.add(BorderLayout.NORTH, startingBalanceField());
    }

    public JTextField startingBalanceField() {
        JTextField field = new JTextField();
        field.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                applicationModel.setStartingBalance(new Dollars(Integer.parseInt(field.getText())));
            }
        });
        return field;
    }

    private Component forecastTable() {
        return  new JScrollPane(new ForecastTable(applicationModel.stockMarketTableModel()));
    }

}
