package com.behzad.finances.ui;
import com.behzad.finances.domain.Dollars;

import org.junit.*;
import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;

public class _DollarsTextFieldTest {

    @Test
    public void canRetrieveAmount(){
        DollarsTextField field = new DollarsTextField(new Dollars(42));
        assertEquals(new Dollars(42), field.getDollars());
    }

    @Test
    public void internals_ShouldThisBeDeleted(){
        DollarsTextField field= new DollarsTextField(new Dollars(42));
        assertTrue( "component should be JFormattedTextField", field instanceof JFormattedTextField);

    }
}
