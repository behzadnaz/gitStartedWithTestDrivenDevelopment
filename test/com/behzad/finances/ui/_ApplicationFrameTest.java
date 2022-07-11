package com.behzad.finances.ui;

import com.behzad.finances.Application;
import org.junit.*;
import static org.junit.Assert.*;

public class _ApplicationFrameTest {
    @Test
    public void applicationWindowShouldHaveTitle(){
        ApplicationFrame frame = new ApplicationFrame();
        assertEquals("title ",ApplicationFrame.TITLE,frame.getTitle());
    }
}
