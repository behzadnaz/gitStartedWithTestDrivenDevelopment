package com.behzad.finances.domain;

import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

import static org.junit.Assert.*;

public class _ValidDollarsTest {
    private ValidDollars twentyDollars;

    @Before
    public void setup(){
        twentyDollars = new ValidDollars(20);
    }

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
    public void renderToSwingLabel(){
        JLabel label = new JLabel();
        twentyDollars.render(label);
        assertEquals("label text should be toString() value",twentyDollars.toString(),label.getText());
    }
    @Test
    public void renderNegativeValueInRed(){
        JLabel label = new JLabel();
        ValidDollars minusTwenty  =new ValidDollars(-20);
        minusTwenty.render(label);
        assertEquals("red when negative", Color.RED, label.getForeground());
    }
    @Test
    public void renderZeroAndPositiveInBlack(){
        JLabel label = new JLabel();
        ValidDollars zero  =new ValidDollars(0);
        zero.render(label);
        assertEquals("black when 0",Color.BLACK,label.getForeground());

        label = new JLabel();
        twentyDollars.render(label);
        assertEquals("black when 0",Color.BLACK,label.getForeground());
    }
    @Test
    public void renderingShouldResetLabelToDefaultState(){
        JLabel label= new JLabel();
        label.setIcon(new ImageIcon());
        label.setToolTipText("bogus tooltip");
        label.setForeground(Color.CYAN);
        twentyDollars.render(label);
        assertNull("should not have icon",label.getIcon());
        assertNull("should not have tooltip", label.getToolTipText());
        assertEquals("foreground color",Color.BLACK,label.getForeground());
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
        assertFalse("valid dollar aren't equal to invalid dollar",  dollars1a.equals(new InvalidDollars()));
        assertTrue("equal dollars should have same hash code", dollars1a.hashCode() == dollars1b.hashCode());
        assertFalse("shouldn't blow up when compare to null", dollars1a.equals(null));
    }
}
