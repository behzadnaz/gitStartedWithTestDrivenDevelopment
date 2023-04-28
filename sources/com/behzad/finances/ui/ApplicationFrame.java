package com.behzad.finances.ui;

import javax.swing.*;
import  java.awt.*;

public class ApplicationFrame extends JFrame {

    public static final long serialVersionID = 1L;
    public static final String TITLE = "Financial Projector";
    public static final Point INITIAL_POSITION = new Point(400, 300);
    public static final Dimension INITIAL_SIZE = new Dimension(900,400);
    public ApplicationModel model;

    public ApplicationFrame(ApplicationModel applicationModel){
        super(TITLE);
        this.model = applicationModel;
        configureWindow();
        addComponents();
    }

    private void configureWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(INITIAL_POSITION);
        setSize(INITIAL_SIZE);
    }

    private void addComponents() {
        Container contentPane = getContentPane();
        Component forecastTable = forecastTable();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(BorderLayout.CENTER, forecastTable);
        contentPane.add(BorderLayout.NORTH, configurationPanel());
    }

    private Component forecastTable() {
        return  new JScrollPane(new ForecastTable(model.stockMarketTableModel()));
    }

    private ConfigurationPanel configurationPanel() {
        return new ConfigurationPanel(model);
    }
}
