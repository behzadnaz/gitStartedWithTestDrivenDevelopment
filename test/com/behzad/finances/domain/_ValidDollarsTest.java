package com.behzad.finances.domain;

import com.behzad.finances.ui.Resources;
import org.junit.*;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;

import static org.junit.Assert.*;

public class _ValidDollarsTest {
    private Dollars twentyDollars = ValidDollars.create(20);
    private Dollars minusTwentyDollars  = ValidDollars.create(-20);
    private Dollars zeroDollars  = ValidDollars.create(0);
    private Dollars MAX_VALID =  ValidDollars.create(ValidDollars.MAX_VALUE);
    private Dollars MIN_VALID =  ValidDollars.create(ValidDollars.MIN_VALUE);

    @Test
    public void cannotConstructDollarsOutsideValidRange(){
       assertEquals("overflow", new InvalidDollars(), ValidDollars.create(ValidDollars.MAX_VALUE + 1));
        assertEquals("underflow", new InvalidDollars(),ValidDollars.create(ValidDollars.MIN_VALUE - 1));
        assertEquals("not a number", new InvalidDollars(),ValidDollars.create(Double.NaN));
    }
    @Test
    public void  isInvalid(){
        assertTrue(ValidDollars.create(42).isValid());
    }
    @Test
    public void addition(){
        assertEquals("addition", ValidDollars.create(40),  ValidDollars.create(10).plus( ValidDollars.create(30)));
        assertEquals("overflow", new InvalidDollars(), MAX_VALID.plus( ValidDollars.create(1)));
        assertEquals("underflow", new InvalidDollars(), MIN_VALID.plus( ValidDollars.create(-1)));
    }
    @Test
    public void subtraction(){
        assertEquals("positive results", twentyDollars, ValidDollars.create(50).minus(ValidDollars.create(30)));
        assertEquals("negative result", ValidDollars.create(-60), ValidDollars.create(40).minus(ValidDollars.create(100)));
        assertEquals("overflow", new InvalidDollars(), MAX_VALID.minus(ValidDollars.create(-1)));
        assertEquals("underflow", new InvalidDollars(), MIN_VALID.minus(ValidDollars.create(1)));
    }
    @Test
    public  void minusToZero(){
        assertEquals("positive result", twentyDollars, ValidDollars.create(50).subtractToZero(ValidDollars.create(30)));
        assertEquals("no negative result-- return zero instead", ValidDollars.create(0), ValidDollars.create(40).subtractToZero(ValidDollars.create(100)));
        assertEquals("overflow", new InvalidDollars(), MAX_VALID.subtractToZero(ValidDollars.create(-1)));
    }
    @Test
    public void percentage(){
        assertEquals("percent",ValidDollars.create(20), ValidDollars.create(100).percentage(20));
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
        Dollars value2 = ValidDollars.create(30);
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
        assertTrue("should round down", ValidDollars.create(10).equals(ValidDollars.create(10.10)));
        assertTrue("should round up", ValidDollars.create(10).equals(ValidDollars.create(9.90)));
        assertTrue("should round up when we have exactly 50 cents",ValidDollars.create(11).equals(ValidDollars.create(10.5)));
    }
    @Test
    public void hashCodeIgnoresPenniesToo(){
        assertTrue("should round down", ValidDollars.create(10).hashCode() == ValidDollars.create(10.10).hashCode());
        assertTrue("should round up", ValidDollars.create(10).hashCode() == ValidDollars.create(9.90).hashCode());
        assertTrue("should round up when we have exactly 50 cents",ValidDollars.create(11).hashCode() == ValidDollars.create(10.5).hashCode());
    }

    @Test
    public void toStringIgnoresPennies(){
        assertEquals("should round down", "$10", ValidDollars.create(10.10).toString());
        assertEquals("should round up", "$10",ValidDollars.create(9.90).toString());
        assertEquals("should round up when we have exactly 50 cents","$11",ValidDollars.create(10.5).toString());
    }
    @Test
    public void toStringFormatsLongNumbersWithCommas(){
        assertEquals("$1,234", ValidDollars.create(1234).toString());
        assertEquals("$12,345,678", ValidDollars.create(12345678).toString());
        assertEquals("$123,456,789", ValidDollars.create(123456789).toString());
    }
    @Test
    public void toStringFormatsLongNumbersWithParentheses(){
        assertEquals("($500)", ValidDollars.create(-500).toString());
    }
    @Test
    public void toStringFormatsInUsaStyleEvenWhenInDifferentLocales(){
        try{
            Locale.setDefault(Locale.FRANCE);
            assertEquals("$1,234",ValidDollars.create(1234).toString());

        }finally {
                Locale.setDefault(Locale.US);
        }

    }
    @Test
    public void valueObject(){
        Dollars dollars1a = ValidDollars.create(10);
        Dollars dollars1b = ValidDollars.create(10);
        Dollars dollars2 = ValidDollars.create(20);
        assertEquals("$10", dollars1a.toString());
        assertTrue("dollars with same amount should be equal", dollars1a.equals(dollars1b));
        assertFalse("dollars with different amount should not be equal", dollars1a.equals(dollars2));
        assertFalse("valid dollar aren't equal to invalid dollar",  dollars1a.equals(new InvalidDollars()));
        assertTrue("equal dollars should have same hash code", dollars1a.hashCode() == dollars1b.hashCode());
        assertFalse("shouldn't blow up when compare to null", dollars1a.equals(null));
    }
}
