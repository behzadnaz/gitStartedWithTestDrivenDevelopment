package com.behzad.finances.ui;

import com.behzad.finances.Application;
import org.junit.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;

import static org.junit.Assert.*;

public class _ApplicationFrameTest {

    private ApplicationFrame frame;

    @Before
    public void setup(){
        frame = new ApplicationFrame();
    }

    @Test
    public void applicationWindowShouldHaveTitle(){
        assertEquals("title ",ApplicationFrame.TITLE,frame.getTitle());
    }
    @Test
    public void shouldHaveHardCodedPositionAndSize(){
        assertEquals("position",ApplicationFrame.INITIAL_POSITION,frame.getLocation());
        assertEquals("size", ApplicationFrame.INITIAL_SIZE,frame.getSize());
    }
    @Test
    public void shouldContainAppropriateComponent() {
        Component[] components = frame.getContentPane().getComponents();
        assertEquals("# of components", 1, components.length);
        assertEquals("component #0 ", JScrollPane.class, components[0].getClass());
        assertEquals("component #0 should contain table", ForecastTable.class, ((JScrollPane)components[0]).getViewport().getView().getClass());
    }
    @Test
    public void shouldExitApplicationWhenWindowsClose(){
        assertEquals("should exit on closed",WindowConstants.EXIT_ON_CLOSE,frame.getDefaultCloseOperation());
    }
    @Test
    public void forecastTableShouldContainCorrectModel(){
        JScrollPane scrollPane = (JScrollPane) frame.getContentPane().getComponent(0);
        TableModel model = ((ForecastTable) scrollPane.getViewport().getView()).getModel();
        assertEquals("forecast table model class",StockMarketTableModel.class, model.getClass());
        assertEquals("# of rows in model",41, model.getRowCount());
    }
}
