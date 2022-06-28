package com.behzad.finances.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class _RequireTest {

    @Test
    public void that(){
        try{
            Require.that(false, "some message");
            fail("expected exception");
        }
        catch (RequireException e){
            assertEquals("same message", e.getMessage());
        }
    }
}

