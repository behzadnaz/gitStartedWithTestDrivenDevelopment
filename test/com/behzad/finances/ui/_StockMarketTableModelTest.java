package com.behzad.finances.ui;

import com.behzad.finances.domain.*;
import org.junit.*;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import static org.junit.Assert.*;

public class _StockMarketTableModelTest {

    public static final Year STARTING_YEAR = new Year(2010);
    private static final Year ENDING_YEAR = new Year(2050);
    public static final Dollars STARTING_BALANCE = new ValidDollars(10000);
    public static final Dollars STARTING_PRINCIPAL = new ValidDollars(7000);
    private StockMarketYear startingYear;
    private StockMarketTableModel model;


    @Before
    public void setup(){
        startingYear = new StockMarketYear(STARTING_YEAR, STARTING_BALANCE, STARTING_PRINCIPAL,new GrowthRate(10), new TaxRate(25));
        StockMarketProjection projection = new StockMarketProjection(startingYear, ENDING_YEAR, new ValidDollars(0));
        model =new StockMarketTableModel(projection);
    }
    @Test
    public void columns(){

        assertEquals("Year", model.getColumnName(0));
        assertEquals("Starting Balance", model.getColumnName(1));
        assertEquals("Cost Basis", model.getColumnName(2));
    }

    @Test
    public void oneRow(){
        assertEquals("year", STARTING_YEAR, model.getValueAt(0,0));
        assertEquals("staring balance", STARTING_BALANCE, model.getValueAt(0,1));
        assertEquals("starting principal", STARTING_PRINCIPAL, model.getValueAt(0,2));
        assertEquals("withdrawals", new ValidDollars(0), model.getValueAt(0,3));
        assertEquals("appreciation", new ValidDollars(1000), model.getValueAt(0,4));
        assertEquals("ending balance", new ValidDollars(11000), model.getValueAt(0,5));
    }

    @Test
    public void multipleRows(){
        assertEquals(41, model.getRowCount());
        assertEquals( STARTING_YEAR, model.getValueAt(0,0));
        assertEquals(STARTING_BALANCE , model.getValueAt(0,1));
        assertEquals(new ValidDollars(11000) , model.getValueAt(1,1));
        assertEquals(ENDING_YEAR , model.getValueAt(40,0));
    }
    @Test
    public void setProjection_ShouldChangeTableModel(){
        StockMarketProjection projection = new StockMarketProjection(startingYear, startingYear.year(), new ValidDollars(0));
        model.setProjection(projection);
        assertEquals("projection should have changed",projection, model.stockMarketProjection());
        assertEquals("change to projection should reflect in method", 1 ,model.getRowCount());
    }
    @Test
    public void startingBalance(){
        assertEquals(STARTING_BALANCE, model.startingBalance());
    }
    @Test
    public void setProjection_ShouldFireUpdateEvent() {
        StockMarketProjection projection = new StockMarketProjection(startingYear, ENDING_YEAR, new ValidDollars(0));
        class TestListener  implements TableModelListener{
            public boolean eventField = false;
            public Integer firstRowChanged = null;
            public Integer lastRowChanged = null;

            public void tableChanged(TableModelEvent e) {
                eventField = true;
                firstRowChanged = e.getFirstRow();
                lastRowChanged = e.getLastRow();
            }
        }
        TestListener listener = new TestListener();
        model.addTableModelListener(listener);
        model.setProjection(projection);
        assertTrue("event should have been fired", listener.eventField);
        assertEquals("the whole table should have changed (first row)",0, listener.firstRowChanged.intValue());
        assertEquals("the whole table should have changed (last row)",Integer.MAX_VALUE, listener.lastRowChanged.intValue());


    }

}
