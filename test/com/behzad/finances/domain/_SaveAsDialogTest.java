package com.behzad.finances.domain;

import com.behzad.finances.ui.*;
import org.junit.After;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class _SaveAsDialogTest {

    private __ApplicationModelSpy mockModel = new __ApplicationModelSpy();
    private SaveAsDialog dialog = new SaveAsDialog(null, mockModel);

    @After
    public void teardown(){
        dialog.dispose();
    }

    @Test
    public void saveAsDialogShouldTellApplicationModelToSaveWhenSaveButtonPushed(){
        dosave(dialog,"/example","filename");
        assertEquals("applicationModel should be told to save",new File("/example/filename"), mockModel.saveCalledWith);
    }
    @Test
    public void layout(){
        assertEquals("Save as dialog mode should be 'save'", FileDialog.SAVE, dialog.getMode());
        assertEquals("Save as dialog title", "Save as",  dialog.getTitle());
    }

    @Test
    public void saveAsDialogShouldDoNothingWhenCancelButtonPushed(){
        dosave(dialog, null, null);
        assertNull("application model should not have been told to save", mockModel.saveCalledWith);
    }

    @Test
    public void saveAsDialogShouldHandleSaveExceptionGracefully(){
        final Frame frame = new Frame();
        final SaveAsDialog exceptionThrowingDialog = createExceptionThrowingSaveAsDialog(frame);

        __Invocation.invokeAndWaitFor("warning dialog", 1000, new __Invocation() {
            @Override
            public void invoke(){
                dosave(exceptionThrowingDialog, "/example", "filename");
            }
            @Override
            public boolean stopWaitingWhen() {
                Dialog dialog = warningDialogOrNullIfNotFound(frame);
                return dialog != null && dialog.isVisible();
            }
        });
        JDialog dialogWindow = (JDialog) warningDialogOrNullIfNotFound(frame);
        JOptionPane dialogPane = (JOptionPane) dialogWindow.getContentPane().getComponent(0);

        assertEquals("Warning dialog parent", frame, dialogWindow.getParent());
        assertEquals("Warning dialog title", "Save File", dialogWindow.getTitle());
        assertEquals("Warning dialog message", "Could not save file:generic Exception", dialogPane.getMessage());
        assertEquals("Warning dialog type should be 'warning'",JOptionPane.WARNING_MESSAGE,dialogPane.getMessageType());
        // assertEquals("Warning dialog title", "Save File", dialog.getTitle());
    }
    private SaveAsDialog createExceptionThrowingSaveAsDialog(Frame frame) {
        final ApplicationModel exceptionThrower = new __ApplicationModelSpy(){
            @Override
            public void save(File saveFile) throws IOException {
                throw new IOException("generic Exception");
            }
        };
        return new SaveAsDialog(frame,exceptionThrower);
    }

    private Dialog warningDialogOrNullIfNotFound(Frame frame){
        Window[] childWindows =frame.getOwnedWindows();
        if(childWindows.length < 2) return null;
        return (Dialog)childWindows[1];
    }

    private void dosave( SaveAsDialog exceptionThrowingDialog,String directory, String filename) {
        exceptionThrowingDialog.setDirectory(directory);
        exceptionThrowingDialog.setFile(filename);
        exceptionThrowingDialog.doSave();
    }
}
