package com.behzad.finances.domain;

import com.behzad.finances.domain.Dollars;
import com.behzad.finances.domain.InterestRate;
import org.junit.*;
import static org.junit.Assert.*;

public class _InterestRateTest {

    @Test
    public void interest(){
        InterestRate rate = new InterestRate(10);
        assertEquals(new Dollars(100), rate.interestOn(new Dollars(1000)));
    }

    @Test
    public void valueObject(){
        InterestRate rate1a = new InterestRate(10);
        InterestRate rate2a= new InterestRate(10);
        InterestRate rate2 = new InterestRate(20);

        assertEquals("10.0%", rate1a.toString());
        assertTrue("same rate is equal", rate1a.equals(rate2a));
        assertFalse("different rates are not equal",rate1a.equals(rate2));
        assertTrue("same rate has same hash code", rate1a.hashCode() == rate2a.hashCode() );
    }
}
