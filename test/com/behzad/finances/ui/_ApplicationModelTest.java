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
        model.setStartingBalance(ValidDollars.create(123));
        assertEquals(ValidDollars.create(123),model.stockMarketTableModel().startingBalance());
    }
    @Test
    public void changingStartingCostBasisShouldChangeStockMarketModel(){
        model.setStartingCostBasis(ValidDollars.create(39));
        assertEquals(ValidDollars.create(39), model.stockMarketTableModel().startingCostBasis());
    }
    @Test
    public void changingYearlySpendingShouldChangeStockMarketTableModel(){
        model.setYearlySpending(ValidDollars.create(423));
        assertEquals(ValidDollars.create(423), model.stockMarketTableModel().yearlySpending());
    }

}
