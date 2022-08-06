package com.behzad.finances.ui;

import com.behzad.finances.domain.Dollars;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.net.URL;

public class ForecastTable extends JTable {

    private static final  Long  serialVersionUID = 1L;
    public static final Color STANDARD_BACKGROUND_COLOR = Color.WHITE;
    public static final Color SELECTION_BACKGROUND_COLOR = Color.LIGHT_GRAY;
    public static final Color ALTERNATE_BACKGROUND_COLOR = new Color(223, 230, 236);

    public ForecastTable(TableModel model) {

        super(model);
        setDefaultRenderer(SelfRenderable.class, selfRenderer());
    }

    private TableCellRenderer selfRenderer(){
        return new DefaultTableCellRenderer(){
            private static final long serialVersionID = 1L;

            public void setValue(Object value){
                SelfRenderable renderable = (SelfRenderable) value;
                renderable.render(this);

            }
        };
    }
    public Component prepareRenderer(TableCellRenderer renderer , int row, int column){
        Component cell = super.prepareRenderer(renderer,row,column);

        if(isCellSelected(row,column)) cell.setBackground(SELECTION_BACKGROUND_COLOR);
        else if(alternatingRow(row)) cell.setBackground(ALTERNATE_BACKGROUND_COLOR);
        else cell.setBackground(STANDARD_BACKGROUND_COLOR);

        return  cell;
    }

    private boolean alternatingRow(int row) {
        return row % 2 == 1;
    }
}

