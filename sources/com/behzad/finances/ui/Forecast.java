package com.behzad.finances.ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class Forecast extends JTable {
    private static final  Long  serialVersionUID = 1L;
    public static final Color STANDARD_BACKGROUND_COLOR = Color.WHITE;
    public static final Color ALTERNATE_BACKGROUND_COLOR = new Color(223, 230, 236);

    public Forecast(TableModel model) {
        super(model);
    }
    public Component prepareRenderer(TableCellRenderer renderer , int row, int column){
        Component cell = super.prepareRenderer(renderer,row,column);
        if(alternatingRow(row)) cell.setBackground(ALTERNATE_BACKGROUND_COLOR);
        else cell.setBackground(STANDARD_BACKGROUND_COLOR);

        return  cell;

    }

    private boolean alternatingRow(int row) {
        return row % 2 == 1;
    }
}

