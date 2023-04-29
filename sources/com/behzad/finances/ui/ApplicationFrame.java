package com.behzad.finances.ui;

import javax.swing.*;
import  java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class ApplicationFrame extends JFrame {

    public static final long serialVersionID = 1L;
    public static final String TITLE = "Financial Projector";
    public static final Point INITIAL_POSITION = new Point(400, 300);
    public static final Dimension INITIAL_SIZE = new Dimension(900,400);
    public ApplicationModel model;


    public static void newWindow() {
        new ApplicationFrame(new ApplicationModel()).setVisible(true);
    }

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

        setJMenuBar(menuBar());

    }

    private Component forecastTable() {
        return  new JScrollPane(new ForecastTable(model.stockMarketTableModel()));
    }

    private ConfigurationPanel configurationPanel() {
        return new ConfigurationPanel(model);
    }

    private JMenuBar menuBar(){
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(newMenuItem());
        fileMenu.add(closeMenuItem());
        menuBar.add(fileMenu);
        return menuBar;
    }

    private JMenuItem newMenuItem() {
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.META_MASK));
        newMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newWindow();
            }
        });
        return newMenuItem;
    }

    private JMenuItem closeMenuItem() {
        JMenuItem closeMenuItem = new JMenuItem("Close");
        closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.META_DOWN_MASK));
        closeMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        return closeMenuItem;
    }
}
