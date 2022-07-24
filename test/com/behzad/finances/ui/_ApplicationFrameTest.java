package com.behzad.finances.ui;

import com.behzad.finances.domain.Dollars;
import org.junit.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import static org.junit.Assert.*;

public class _ApplicationFrameTest {

    private ApplicationFrame frame;

    @Before
    public void setup(){
        frame = new ApplicationFrame(new ApplicationModel());
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
    public void shouldLayoutProperly() {
        assertEquals("layout",BorderLayout.class,frame.getContentPane().getLayout().getClass());
        Component[] components = frame.getContentPane().getComponents();
        assertEquals("# of components", 2, components.length);
        assertEquals("scroll pane ", JScrollPane.class, components[0].getClass());
        assertEquals("scroll pane should contain table", ForecastTable.class, ((JScrollPane)components[0]).getViewport().getView().getClass());
        assertEquals("starting balance field", DollarsTextField.class, components[1].getClass());
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
    @Test
    public void startingBalanceFieldShouldUpdateApplicationModel(){
        class MockApplicationModel extends ApplicationModel{
           public Dollars setStartingBalanceCalledWith;

           @Override
           public void setStartingBalance(Dollars startingBalance) {
               setStartingBalanceCalledWith = startingBalance;
           }
       }
        MockApplicationModel mockModel = new MockApplicationModel();
        frame = new ApplicationFrame(mockModel);

        DollarsTextField field = frame.startingBalanceField();
        field.setText("668");
        assertEquals("application model should be updated",new Dollars(668), mockModel.setStartingBalanceCalledWith);
    }
}
