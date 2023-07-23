package com.behzad.finances.ui;

import com.behzad.finances.domain.StockMarketProjection;
import com.behzad.finances.domain.StockMarketYear;
import com.behzad.finances.persistence.SaveFile;
import com.behzad.finances.values.UserEnteredDollars;
import com.behzad.finances.values.ValidDollars;
import org.junit.*;

import java.io.File;
import java.io.IOException;

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
        model.setStartingBalance(new UserEnteredDollars("123"));
        assertEquals(new ValidDollars(123),model.stockMarketTableModel().startingBalance());
    }
    @Test
    public void changingStartingCostBasisShouldChangeStockMarketModel(){
        model.setStartingCostBasis(new UserEnteredDollars("39"));
        assertEquals(new ValidDollars(39), model.stockMarketTableModel().startingCostBasis());
    }
    @Test
    public void changingYearlySpendingShouldChangeStockMarketTableModel(){
        model.setYearlySpending(new UserEnteredDollars("423"));
        assertEquals(new ValidDollars(423), model.stockMarketTableModel().yearlySpending());
    }
    @Test
    public void nameOfSaveFile() throws IOException{
        assertNull("should not have save file if save not called", model.saveFilePathOrNullIfNotSaved());
        File expectedFile = new File("foo");
        model.save(expectedFile);
        assertEquals("should have file after save called", expectedFile, model.saveFilePathOrNullIfNotSaved());
    }
    @Test
    public void save() throws IOException{
        model.save(new File("foo"));
        assertTrue("file should have been saved", model.fileHasEverBeenSaved());
    }

    @Test
    @Ignore
    //ToDO
    public void isave() throws IOException {
        class  SaveFileSpy extends SaveFile{
            public boolean saveCalled = false;

            public SaveFileSpy(){
                super(null);
            }

            public void save(UserEnteredDollars startingBalance, UserEnteredDollars costBasis, UserEnteredDollars yearlySpending){
                this.saveCalled = true;
            }
        }
        SaveFileSpy mockSaveFile = new SaveFileSpy();
        model = new ApplicationModel(mockSaveFile);
        model.save(null);
        assertTrue("saveFile.save() should have been called", mockSaveFile.saveCalled);
    }

}
