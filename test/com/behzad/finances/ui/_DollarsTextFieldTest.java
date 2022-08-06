package com.behzad.finances.ui;
import com.behzad.finances.domain.Dollars;

import com.behzad.finances.domain.ValidDollars;
import org.junit.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;

import static org.junit.Assert.*;

public class _DollarsTextFieldTest {

    private DollarsTextField field;

    @Before
    public void setup(){
        field = new DollarsTextField(new ValidDollars(42));
    }

    @Test
    public void canRetrieveAmount(){
        assertEquals(new ValidDollars(42), field.getDollars());
    }
    @Test
    public void textReflectsDollarAmountUponConstruction(){
        assertEquals("$42", field.getText());
    }

    @Test
    public void changingTextChangeDollarAmount(){
        field.setText("1024");
        assertEquals(new ValidDollars(1024), field.getDollars());
    }
    @Test
    public void emptyStringIsZeroDollars(){
        field.setText("");
        assertEquals(new ValidDollars(0),field.getDollars());
    }

    @Test
    public void fieldIsNotReformattedWhenTheValidIsInvalid() throws Exception {
        field.dispatchEvent(new FocusEvent(field, FocusEvent.FOCUS_GAINED));
        field.setText("xxx");
        field.dispatchEvent(new FocusEvent(field, FocusEvent.FOCUS_LOST));

        final String[] testResult = {null};
        SwingUtilities.invokeAndWait(new Runnable(){
            public void run() {
                testResult[0] = (field.getText());
            }
        });
        assertEquals("xxx", testResult[0]);
    }
}
