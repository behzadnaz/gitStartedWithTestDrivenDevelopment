package com.behzad.finances.ui;

import com.behzad.finances.domain.StockMarketProjection;
import org.junit.*;
import static org.junit.Assert.*;

public class _ApplicationModelTest {

    @Test
    public void shouldStartWithDefaultStockMarket(){
        ApplicationModel  model = new ApplicationModel();
        StockMarketProjection projection = model.stockMarketProjection();

    }
}
