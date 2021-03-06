package com.behzad.finances.domain;

import org.junit.*;
import static org.junit.Assert.*;

public class _DollarsTest {

    @Test
    public void addition(){

        assertEquals("positive result",new Dollars(40), new Dollars(10).plus(new Dollars(30)));
    }
    @Test
    public void subtraction(){
        assertEquals("positive results",new Dollars(20), new Dollars(50).minus(new Dollars(30)));
        assertEquals("negative result", new Dollars(-60), new Dollars(40).minus(new Dollars(100)));
    }
    @Test
    public  void minusToZero(){
        assertEquals("positive result",new Dollars(20), new Dollars(50).subtractToZero(new Dollars(30)));
        assertEquals("no negative result-- return zero instead", new Dollars(0), new Dollars(40).subtractToZero(new Dollars(100)));
    }
    @Test
    public void percentage(){
        assertEquals(new Dollars(20), new Dollars(100).percentage(20));
    }
    @Test
    public void min(){
        Dollars value1 = new Dollars(20);
        Dollars value2 = new Dollars(30);
        assertEquals("Value 1",new Dollars(20), Dollars.min(value1, value2));
        assertEquals("Value 2",new Dollars(20), Dollars.min(value2, value1));
    }

    @Test
    public void equalIgnorePennies(){
        assertTrue("should round down", new Dollars(10).equals(new Dollars(10.10)));
        assertTrue("should round up", new Dollars(10).equals(new Dollars(9.90)));
        assertTrue("should round up when we have exactly 50 cents",new Dollars(11).equals(new Dollars(10.5)));
    }
    @Test
    public void hashCodeIgnoresPenniesToo(){
        assertTrue("should round down", new Dollars(10).hashCode() == new Dollars(10.10).hashCode());
        assertTrue("should round up", new Dollars(10).hashCode() == new Dollars(9.90).hashCode());
        assertTrue("should round up when we have exactly 50 cents",new Dollars(11).hashCode() == new Dollars(10.5).hashCode());
    }
    @Test
    public void toStringIgnoresPennies(){
        assertEquals("should round down", "$10", new Dollars(10.10).toString());
        assertEquals("should round up", "$10",new Dollars(9.90).toString());
        assertEquals("should round up when we have exactly 50 cents","$11",new Dollars(10.5).toString());
    }

    @Test
    public void valueObject(){
        Dollars dollars1a = new Dollars(10);
        Dollars dollars1b = new Dollars(10);
        Dollars dollars2 = new Dollars(20);
        assertEquals("$10", dollars1a.toString());
        assertTrue("dollars with same amount should be equal", dollars1a.equals(dollars1b));
        assertFalse("dollars with different amount shoukd not be equal", dollars1a.equals(dollars2));
        assertTrue("equal dollars should have same hash code", dollars1a.hashCode() == dollars1b.hashCode());
    }
}
