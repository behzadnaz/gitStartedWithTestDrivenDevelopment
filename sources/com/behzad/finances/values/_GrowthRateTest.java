package com.behzad.finances.values;

import com.behzad.finances.values.GrowthRate;
import com.behzad.finances.values.ValidDollars;
import org.junit.*;
import static org.junit.Assert.*;

public class _GrowthRateTest {

    @Test
    public void interest(){
        GrowthRate rate = new GrowthRate(10);
        assertEquals(new ValidDollars(100), rate.growthFor(new ValidDollars(1000)));
    }

    @Test
    public void valueObject(){
        GrowthRate rate1a = new GrowthRate(10);
        GrowthRate rate2a= new GrowthRate(10);
        GrowthRate rate2 = new GrowthRate(20);

        assertEquals("10.0%", rate1a.toString());
        assertTrue("same rate is equal", rate1a.equals(rate2a));
        assertFalse("different rates are not equal",rate1a.equals(rate2));
        assertTrue("same rate has same hash code", rate1a.hashCode() == rate2a.hashCode() );
    }
}
