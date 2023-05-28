package com.behzad.finances.domain;

import com.behzad.finances.ui.Resources;
import com.behzad.finances.util.RequireException;
import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

import static org.junit.Assert.*;

public class _ValidDollarsTest {
    private Dollars twentyDollars = new ValidDollars(20);
    private Dollars minusTwentyDollars  = new ValidDollars(-20);
    private Dollars zeroDollars  = new ValidDollars(0);
    private Dollars MAX_VALID =  new ValidDollars(Dollars.MAX_VALUE);
    private Dollars MIN_VALID =  new ValidDollars(Dollars.MIN_VALUE);

    @Test
    public void cannotConstructDollarsOutsideValidRange(){
        try {
            new ValidDollars(Dollars.MAX_VALUE + 1);
            fail("expected overflow");
        }
        catch (RequireException ex) {
        }
        try {
            new ValidDollars(Dollars.MIN_VALUE - 1);
            fail("expected underflow");
        } catch (RequireException ex){

        }
        try {
            new ValidDollars(Double.NaN);
            fail("expected NA failure");
        } catch (RequireException ex){

        }
    }

    @Test
    public void  isInvalid(){
        assertTrue(new ValidDollars(42).isValid());
    }

    @Test
    //Resist the temptation to make the public! That is the design smell
    public void toCoreDataType(){
        assertEquals("", 12.34567891, ((ValidDollars)new ValidDollars(12.34567891)).toCoreDataType(), 0);

    }
    @Test
    public void addition(){
        assertEquals("addition", new ValidDollars(40),  new ValidDollars(10).plus( new ValidDollars(30)));
        assertEquals("overflow", new InvalidDollars(), MAX_VALID.plus( new ValidDollars(1)));
        assertEquals("underflow", new InvalidDollars(), MIN_VALID.plus( new ValidDollars(-1)));
    }
    @Test
    public void subtraction(){
        assertEquals("positive results", twentyDollars, new ValidDollars(50).minus(new ValidDollars(30)));
        assertEquals("negative result", new ValidDollars(-60), new ValidDollars(40).minus(new ValidDollars(100)));
        assertEquals("overflow", new InvalidDollars(), MAX_VALID.minus(new ValidDollars(-1)));
        assertEquals("underflow", new InvalidDollars(), MIN_VALID.minus(new ValidDollars(1)));
    }
    @Test
    public  void minusToZero(){
        assertEquals("positive result", twentyDollars, new ValidDollars(50).subtractToZero(new ValidDollars(30)));
        assertEquals("no negative result-- return zero instead", new ValidDollars(0), new ValidDollars(40).subtractToZero(new ValidDollars(100)));
        assertEquals("overflow", new InvalidDollars(), MAX_VALID.subtractToZero(new ValidDollars(-1)));
    }
    @Test
    public void percentage(){
        assertEquals("percent",new ValidDollars(20), new ValidDollars(100).percentage(20));
        assertEquals("overflow", new InvalidDollars(), MAX_VALID.percentage(200));
    }
    @Test
    public void flipSign(){
       assertEquals("zero to zero", zeroDollars, zeroDollars.flipSign());
       assertEquals("positive to negative", minusTwentyDollars, twentyDollars.flipSign());
       assertEquals("negative to positive",twentyDollars,  minusTwentyDollars.flipSign());
    }

    @Test
    public void min(){
        Dollars value1 = twentyDollars;
        Dollars value2 = new ValidDollars(30);
        assertEquals("Value 1",twentyDollars, Dollars.min(value1, value2));
        assertEquals("Value 2",twentyDollars, Dollars.min(value2, value1));
    }

    @Test
    public void renderItself(){
        __RenderTargetStub target = new __RenderTargetStub();
        twentyDollars.render(new Resources(),target);
        assertEquals("label text should be toString() value",twentyDollars.toString(),target.text);
    }
    @Test
    public void rendersNegativeValueInRed(){
        __RenderTargetStub target = new __RenderTargetStub();
        minusTwentyDollars.render(new Resources(), target);
        assertEquals("red when negative", Color.RED, target.foregroundColor);
    }
    @Test
    public void rendersZeroAndPositiveInBlack(){
        __RenderTargetStub target = new __RenderTargetStub();
        zeroDollars.render(new Resources(), target);
        assertEquals("black when 0",Color.BLACK,target.foregroundColor);

        target = new __RenderTargetStub();
        twentyDollars.render(new Resources(), target);
        assertEquals("black when 0",Color.BLACK,target.foregroundColor);
    }
    @Test
    public void renderingResetLabelToDefaultState(){
        __RenderTargetStub target = new __RenderTargetStub();
        target.icon = new ImageIcon();
        target.toolTipText = ("bogus tooltip");
        target.foregroundColor= Color.CYAN;
        twentyDollars.render(new Resources(), target);
        assertNull("should not have icon",target.icon);
        assertNull("should not have tooltip", target.toolTipText);
        assertEquals("foreground color",Color.BLACK,target.foregroundColor);
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

        assertTrue("valid dollars should be comparable to user-entered dollars when equal", dollars1a.equals(new UserEnteredDollars("10")));
        assertFalse("valid dollars should be comparable to user-entered dollars when unequal", dollars1a.equals(new UserEnteredDollars("20")));
        assertFalse("valid dollar aren't equal to invalid dollar",  dollars1a.equals(new InvalidDollars()));

        assertTrue("equal dollars should have same hash code", dollars1a.hashCode() == dollars1b.hashCode());
        assertFalse("shouldn't blow up when compare to null", dollars1a.equals(null));

    }
}
