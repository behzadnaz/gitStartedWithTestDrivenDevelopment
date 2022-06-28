package com.behzad.finances.domain;

import org.junit.*;
import static org.junit.Assert.*;

public class _YearTest {

    @Test
    public void nextYear(){
        Year thisYear = new Year(2010);
        assertEquals(new Year(2011), thisYear.nextYear());
    }
    @Test
    public void numberOfYearsInclusive(){
        Year thisYear =  new Year(2010);
        assertEquals(41, thisYear.numberOfYearsInclusive(new Year(2050)));
    }
    @Test
    public void valueObject(){
        Year year1a = new Year(2010);
        Year year1b = new Year(2010);
        Year year2 = new Year(2012);

        assertEquals("2010", year1a.toString());
        assertTrue("year with same value should be equal", year1a.equals(year1b));
        assertFalse("year with different value shouldn't be equal", year1a.equals(year2));
        assertTrue("year with same value should have same hash code", year1a.hashCode() == year1b.hashCode());
    }
}
