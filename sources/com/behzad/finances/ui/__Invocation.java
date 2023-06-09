package com.behzad.finances.ui;

import javax.swing.*;
import java.util.Date;

import static org.junit.Assert.fail;

//returns null if not found
public abstract class __Invocation {
    abstract public void invoke();

    public abstract boolean stopWaitingWhen();

    public static void invokeAndWaitFor(String message, int timeout, final __Invocation check) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                check.invoke();
            }
        });

        long startTime = new Date().getTime();
        while(!check.stopWaitingWhen()){
            Thread.yield();
            long elapsedMilliseconds = new Date().getTime() - startTime;
            if(elapsedMilliseconds > timeout) fail("expected" + message + " within " + timeout + " milliseconds ");
        }
    }
}

