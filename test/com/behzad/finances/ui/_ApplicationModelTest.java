package com.behzad.finances.ui;

import com.behzad.finances.domain.Dollars;
import com.behzad.finances.domain.StockMarketProjection;
import com.behzad.finances.domain.StockMarketYear;
import com.behzad.finances.domain.ValidDollars;
import org.junit.*;
import static org.junit.Assert.*;

public class _ApplicationModelTest {

    private ApplicationModel model;

    @Before
    public  void setup(){
        model = new ApplicationModel();
    }
    @Test
    public void shouldStartWithDefaultStockMarket(){
        StockMarketProjection projection = model.stockMarketTableModel().stockMarketProjection();
        StockMarketYear startingYear = projection.getYearOffset(0);
        assertEquals(ApplicationModel.DEFAULT_STARTING_YEAR,startingYear.year());
        assertEquals(ApplicationModel.DEFAULT_STARTING_BALANCE, startingYear.startingBalance());
        assertEquals(ApplicationModel.DEFAULT_STARTING_COST_BASIS,startingYear.startingCostBasis());
        assertEquals(ApplicationModel.DEFAULT_GROWTH_RATE,startingYear.growthRate());
        assertEquals(ApplicationModel.DEFAULT_CAPITAL_GAINS_TAX_RATE,startingYear.capitalGainsTaxRate());
        assertEquals(ApplicationModel.DEFAULT_YEARLY_SPENDING, startingYear.totalSellOrders());
        assertEquals(41,projection.numberOfYears());
    }
    @Test
    public void  shouldOnlyHaveOneInstanceOfStockMarketTableModel(){
        assertTrue("should be same instance",model.stockMarketTableModel() ==model.stockMarketTableModel());
    }
    @Test
    public void changingStartingBalanceShouldChangeStockMarketModel(){
        model.setStartingBalance(new ValidDollars(123));
        assertEquals(new ValidDollars(123),model.stockMarketTableModel().startingBalance());
    }
    @Test
    public void changingStartingCostBasisShouldChangeStockMarketModel(){
        model.setStartingCostBasis(new ValidDollars(39));
        assertEquals(new ValidDollars(39), model.stockMarketTableModel().startingCostBasis());
    }
    @Test
    public void changingYearlySpendingShouldChangeStockMarketTableModel(){
        model.setYearlySpending(new ValidDollars(423));
        assertEquals(new ValidDollars(423), model.stockMarketTableModel().yearlySpending());
    }

}
