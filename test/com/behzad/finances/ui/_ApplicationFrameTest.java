package com.behzad.finances.ui;

import org.junit.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static org.junit.Assert.*;

public class _ApplicationFrameTest {

    private ApplicationFrame frame;
    private __ApplicationModelSpy mockModel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem newMenuItem;
    private JMenuItem closeMenuItem;
    private JMenuItem  saveAsMenuItem;

    @Before
    public void setup() throws Exception {
        mockModel = new __ApplicationModelSpy();
        frame = new ApplicationFrame(mockModel);
        menuBar = frame.getJMenuBar();
        fileMenu = menuBar.getMenu(0);
        newMenuItem = fileMenu.getItem(0);
        closeMenuItem = fileMenu.getItem(1);
        saveAsMenuItem = fileMenu.getItem(2);
    }
    @After
    public void teardown(){
        frame.setVisible(false);
        //I could 'frame.dispose()' here, which would release the resources consumed
        //by creating the frame. However, that could cause tests to fail if they are
        //assuming a certain number of frames, because the number of frames decrements
        //according to the whims of garbage collector.
        //
        //Also,dispose() is very slow. so I've decided not to dispose of frames at this
        //time.As a result, many frames are being created and then hanging around in
        //memory consuming graphic resources. This could cause out-of-memory errors or
        //some other problems in the future.
    }
    @Test
    public void newWindow() throws Throwable{

        int frameCount = Frame.getFrames().length;
        ApplicationFrame.newWindow();
        Frame[] allFrames = Frame.getFrames();
        assertEquals("number of windows should increase by 1", frameCount + 1, allFrames.length);
        assertTrue("new  window should be visible", allFrames[allFrames.length-1].isVisible());
    }

    @Test
    public void applicationWindowShouldHaveTitle(){
        assertEquals("title ",ApplicationFrame.TITLE,frame.getTitle());
    }
    @Test
    public void shouldHaveHardCodedPositionAndSize(){
        assertEquals("position",ApplicationFrame.INITIAL_POSITION,frame.getLocation());
        assertEquals("size", ApplicationFrame.INITIAL_SIZE,frame.getSize());
    }
    @Test
    public void shouldLayoutProperly() {
        assertEquals("layout",BorderLayout.class,frame.getContentPane().getLayout().getClass());
        Component[] components = frame.getContentPane().getComponents();
        assertEquals("# of components", 2, components.length);
        assertEquals("scroll pane ", JScrollPane.class, components[0].getClass());
        assertEquals("scroll pane should contain table", ForecastTable.class, ((JScrollPane)components[0]).getViewport().getView().getClass());
        assertEquals("configuration panel", ConfigurationPanel.class, components[1].getClass());
    }
    @Test
    public void shouldExitApplicationWhenWindowsClose(){
        assertEquals("should exit on closed",WindowConstants.DISPOSE_ON_CLOSE,frame.getDefaultCloseOperation());
    }

    @Test
    public void forecastTableShouldContainCorrectModel(){
        JScrollPane scrollPane = (JScrollPane) frame.getContentPane().getComponent(0);
        TableModel model = ((ForecastTable) scrollPane.getViewport().getView()).getModel();
        assertEquals("forecast table model class",StockMarketTableModel.class, model.getClass());
        assertEquals("# of rows in model",41, model.getRowCount());
    }

    @Test
    public void shouldHaveMenu(){
        assertNotNull("should have menu bar", menuBar);
        assertEquals("# of menus", 1, menuBar.getMenuCount());

        assertEquals("file menu title", "File", fileMenu.getText());
        assertEquals("# menu items", 3, fileMenu.getItemCount());

        assertMenuItemEquals(newMenuItem, "New", KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.META_MASK));
        assertMenuItemEquals(closeMenuItem, "Close", KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.META_MASK));
        assertMenuItemEquals(saveAsMenuItem, "Save As ...", KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_MASK | InputEvent.META_MASK));
    }

    private void assertMenuItemEquals(JMenuItem menuItem, String expectedName, KeyStroke expectedAccelerator) {
        assertEquals(expectedName +"menu item name", expectedName, menuItem.getText());
        assertEquals(expectedName + "accelerator key", expectedAccelerator, menuItem.getAccelerator());
    }

    @Test
    public void newMenuItemShouldCreateANewWindow() throws Throwable{

        int frameCount = Frame.getFrames().length;
        newMenuItem.doClick();

        Frame[] allFrames = Frame.getFrames();
        assertEquals("number of windows should increase by 1", frameCount + 1, allFrames.length);
        assertTrue("new  window should be visible", allFrames[allFrames.length-1].isVisible());
    }
    @Test
    public void closeMenuItemShouldCloseTheWindow() throws Throwable{
        // This test sometimes fails saying frame is not disposed. Difficult reliably reproduce; seems to be race condition
        // that appears when Swing tests are running slow.
        // Tried invokeAndWait around doClick (did not work)
        // Currently trying: run whole test on event handler thread using  invokeAndWait?
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
                assertTrue("before disposable, frame is displayable", frame.isDisplayable());
                closeMenuItem.doClick();
                assertTrue("frame should have been disposed", !frame.isDisplayable());
            }
        });
        frame.setVisible(true);
        assertTrue("before disposal, frame is displayable", frame.isDisplayable());
        closeMenuItem.doClick();
        assertTrue("frame should have been disposed",!frame.isDisplayable());
    }
    @Test
    public void saveAsMenuItemShouldShowSaveAsDialog() throws Throwable{
        final FileDialog saveAsDialog = (SaveAsDialog) frame.getOwnedWindows()[0];

        __Invocation.invokeAndWaitFor("Save as dialog", 1000, new __Invocation(){
            @Override
            public void invoke() {
                saveAsMenuItem.doClick();
            }

            @Override
            public boolean stopWaitingWhen() {
                return saveAsDialog.isVisible();
            }
        });
    }
}


