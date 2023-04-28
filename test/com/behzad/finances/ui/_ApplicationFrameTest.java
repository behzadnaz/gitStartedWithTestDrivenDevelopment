package com.behzad.finances.ui;

import com.behzad.finances.domain.Dollars;
import com.behzad.finances.domain.ValidDollars;
import org.junit.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static org.junit.Assert.*;

public class _ApplicationFrameTest {

    private ApplicationFrame frame;
    private ApplicationModel model;

    @Before
    public void setup(){
        model = new ApplicationModel();
        frame = new ApplicationFrame(model);
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
    public void shouldHaveMenu(){
        JMenuBar menuBar = frame.getJMenuBar();
        assertNotNull("should have menu bar", menuBar);
        assertEquals("# of menus", 1, menuBar.getMenuCount());
        JMenu fileMenu = menuBar.getMenu(0);
        assertEquals("file menu title", "File", fileMenu.getText());
        assertEquals("# menu items", 1, fileMenu.getItemCount());
        JMenuItem newMenuItem = fileMenu.getItem(0);
        assertEquals("'new' menu item", "New", newMenuItem.getText());

        KeyStroke newMenuItemAccelerator = newMenuItem.getAccelerator();
        assertNotNull("'new' menu item should have accelerator", newMenuItemAccelerator);
        assertEquals("'new' accelerator key", KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.META_MASK), newMenuItemAccelerator);
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
        assertEquals("should exit on closed",WindowConstants.EXIT_ON_CLOSE,frame.getDefaultCloseOperation());
    }

    @Test
    public void forecastTableShouldContainCorrectModel(){
        JScrollPane scrollPane = (JScrollPane) frame.getContentPane().getComponent(0);
        TableModel model = ((ForecastTable) scrollPane.getViewport().getView()).getModel();
        assertEquals("forecast table model class",StockMarketTableModel.class, model.getClass());
        assertEquals("# of rows in model",41, model.getRowCount());
    }

}
