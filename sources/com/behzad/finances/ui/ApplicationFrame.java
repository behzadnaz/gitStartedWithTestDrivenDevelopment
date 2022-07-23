package com.behzad.finances.ui;

import com.behzad.finances.domain.Dollars;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
        final JTextField field = new JTextField();
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {updateApplicationModel();}
            @Override
            public void removeUpdate(DocumentEvent e) {updateApplicationModel();}
            @Override
            public void changedUpdate(DocumentEvent e) {updateApplicationModel();}
            private void updateApplicationModel(){
                int value = Integer.parseInt(field.getText());
                Dollars startingBalance = new Dollars(value);
                applicationModel.setStartingBalance(startingBalance);}
        });
        return field;
    }

    private Component forecastTable() {
        return  new JScrollPane(new ForecastTable(applicationModel.stockMarketTableModel()));
    }

}
