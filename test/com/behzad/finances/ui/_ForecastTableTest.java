package com.behzad.finances.ui;

import com.behzad.finances.values.SelfRenderable;
import org.junit.*;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import static org.junit.Assert.*;

public class _ForecastTableTest {

    @Test
    public void tableRowsShouldUseStandardColor_WhenJustOneRow(){
        DefaultTableModel tableModel= new DefaultTableModel(0,1);
        tableModel.addRow(new String[]{""});
        JTable table = new ForecastTable(tableModel);
        assertEquals("row 0 should have standard background", ForecastTable.STANDARD_BACKGROUND_COLOR, getCellBackground(table, 0, 0));
    }

    @Test
    public void tableRowsShouldAlternateColors_WhenThereAreNoHeader(){
        DefaultTableModel tableModel= new DefaultTableModel(0,1);
        tableModel.addRow(new String[]{""});
        tableModel.addRow(new String[]{""});
        tableModel.addRow(new String[]{""});
        tableModel.addRow(new String[]{""});
        JTable table = new ForecastTable(tableModel);

        assertEquals("row 0 should have standard background", ForecastTable.STANDARD_BACKGROUND_COLOR, getCellBackground(table, 0, 0));
        assertEquals("row 1 should have alternate background", ForecastTable.ALTERNATE_BACKGROUND_COLOR, getCellBackground(table,1,0));
        assertEquals("row 2 should have standard background", ForecastTable.STANDARD_BACKGROUND_COLOR, getCellBackground(table, 2, 0));
        assertEquals("row 3 should have alternate background", ForecastTable.ALTERNATE_BACKGROUND_COLOR, getCellBackground(table,3,0));
    }

    @Test
    public void tableRowsShouldAlternateColors_WhenThereAreColumnHeader(){
        DefaultTableModel tableModel= new DefaultTableModel(0,1);
        tableModel.setColumnIdentifiers(new Object[]{"Header"});
        tableModel.addRow(new String[]{""});
        tableModel.addRow(new String[]{""});
        tableModel.addRow(new String[]{""});
        tableModel.addRow(new String[]{""});

        JTable table = new ForecastTable(tableModel);
        assertEquals("row 0 should have standard background", ForecastTable.STANDARD_BACKGROUND_COLOR, getCellBackground(table, 0, 0));
        assertEquals("row 1 should have alternate background", ForecastTable.ALTERNATE_BACKGROUND_COLOR, getCellBackground(table,1,0));
        assertEquals("row 2 should have standard background", ForecastTable.STANDARD_BACKGROUND_COLOR, getCellBackground(table, 2, 0));
        assertEquals("row 3 should have alternate background", ForecastTable.ALTERNATE_BACKGROUND_COLOR, getCellBackground(table,3,0));
    }
    @Test
    public void selectedRowsShouldUseSelectionBackgroundColor_WhenRowIsSelected(){
        DefaultTableModel tableModel= new DefaultTableModel(0,1);
        tableModel.addRow(new String[]{""});
        JTable table = new ForecastTable(tableModel);

        table.setRowSelectionInterval(0,0);
        assertEquals("row 0 should have selection background", ForecastTable.SELECTION_BACKGROUND_COLOR, getCellBackground(table, 0, 0));
    }
    @Test
    @SuppressWarnings("serial")
    public void tableShouldHaveSelfRenderableObjectsRenderThemselves(){
        SelfRenderable renderable = new SelfRenderable() {
            @Override
            public void render(Resources resources, RenderTarget target){
                target.setText("I render myself");
            }
        };
        DefaultTableModel tableModel = new DefaultTableModel(0,1){
          @Override
            public  Class<?> getColumnClass(int column){
                return SelfRenderable.class;
            }
        };
        tableModel.addRow(new SelfRenderable[] {renderable});
        JTable table = new ForecastTable(tableModel);
        String cellText = getCellText(table, 0, 0);
        assertEquals("I render myself", cellText);
    }

    private String getCellText(JTable table,int row, int column) {
        TableCellRenderer renderer = table.getCellRenderer(row,column);
        JLabel label = (JLabel) table.prepareRenderer(renderer,row,column);
        return label.getText();
    }

    private Color getCellBackground(JTable table, int row, int column) {
        TableCellRenderer renderer = table.getCellRenderer(row, column);
        Component component = table.prepareRenderer(renderer, row, column);
        Color actualColor = component.getBackground();
        return actualColor;
    }

}
