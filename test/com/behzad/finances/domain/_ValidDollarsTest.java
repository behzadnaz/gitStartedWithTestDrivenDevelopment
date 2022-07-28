package com.behzad.finances.domain;

import org.junit.*;

import java.util.Locale;

import static org.junit.Assert.*;

public class _ValidDollarsTest {

    @Test
    public void  isInvalid(){
        assertTrue(new ValidDollars(42).isValid());
    }
    @Test
    public void addition(){

        assertEquals("positive result",new ValidDollars(40), new ValidDollars(10).plus(new ValidDollars(30)));
    }
    @Test
    public void subtraction(){
        assertEquals("positive results",new ValidDollars(20), new ValidDollars(50).minus(new ValidDollars(30)));
        assertEquals("negative result", new ValidDollars(-60), new ValidDollars(40).minus(new ValidDollars(100)));
    }
    @Test
    public  void minusToZero(){
        assertEquals("positive result",new ValidDollars(20), new ValidDollars(50).subtractToZero(new ValidDollars(30)));
        assertEquals("no negative result-- return zero instead", new ValidDollars(0), new ValidDollars(40).subtractToZero(new ValidDollars(100)));
    }
    @Test
    public void percentage(){
        assertEquals(new ValidDollars(20), new ValidDollars(100).percentage(20));
    }
    @Test
    public void min(){
        Dollars value1 = new ValidDollars(20);
        Dollars value2 = new ValidDollars(30);
        assertEquals("Value 1",new ValidDollars(20), Dollars.min(value1, value2));
        assertEquals("Value 2",new ValidDollars(20), Dollars.min(value2, value1));
    }

    @Test
    public void equalIgnorePennies(){
        assertTrue("should round down", new ValidDollars(10).equals(new ValidDollars(10.10)));
        assertTrue("should round up", new ValidDollars(10).equals(new ValidDollars(9.90)));
        assertTrue("should round up when we have exactly 50 cents",new ValidDollars(11).equals(new ValidDollars(10.5)));
    }
    @Test
    public void hashCodeIgnoresPenniesToo(){
        assertTrue("should round down", new ValidDollars(10).hashCode() == new ValidDollars(10.10).hashCode());
        assertTrue("should round up", new ValidDollars(10).hashCode() == new ValidDollars(9.90).hashCode());
        assertTrue("should round up when we have exactly 50 cents",new ValidDollars(11).hashCode() == new ValidDollars(10.5).hashCode());
    }

    @Test
    public void toStringIgnoresPennies(){
        assertEquals("should round down", "$10", new ValidDollars(10.10).toString());
        assertEquals("should round up", "$10",new ValidDollars(9.90).toString());
        assertEquals("should round up when we have exactly 50 cents","$11",new ValidDollars(10.5).toString());
    }
    @Test
    public void toStringFormatsLongNumbersWithCommas(){
        assertEquals("$1,234", new ValidDollars(1234).toString());
        assertEquals("$12,345,678", new ValidDollars(12345678).toString());
        assertEquals("$123,456,789", new ValidDollars(123456789).toString());
    }
    @Test
    public void toStringFormatsLongNumbersWithParentheses(){
        assertEquals("($500)", new ValidDollars(-500).toString());
    }
    @Test
    public void toStringFormatsInUsaStyleEvenWhenInDifferentLocales(){
        try{
            Locale.setDefault(Locale.FRANCE);
            assertEquals("$1,234",new ValidDollars(1234).toString());

        }finally {
                Locale.setDefault(Locale.US);
        }

    }
    @Test
    public void valueObject(){
        Dollars dollars1a = new ValidDollars(10);
        Dollars dollars1b = new ValidDollars(10);
        Dollars dollars2 = new ValidDollars(20);
        assertEquals("$10", dollars1a.toString());
        assertTrue("dollars with same amount should be equal", dollars1a.equals(dollars1b));
        assertFalse("dollars with different amount should not be equal", dollars1a.equals(dollars2));
        assertTrue("equal dollars should have same hash code", dollars1a.hashCode() == dollars1b.hashCode());
    }
}
