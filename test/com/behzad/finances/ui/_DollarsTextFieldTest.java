package com.behzad.finances.ui;

import com.behzad.finances.values.ValidDollars;
import org.junit.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class _DollarsTextFieldTest {

    private DollarsTextField field;
    private JPanel iconPanel;
    private JTextField textComponent;
    private JLabel iconComponent;

    @Before
    public void setup(){

        field = new DollarsTextField(new ValidDollars(42));
        Component[] components = field.getComponents();
        iconPanel = (JPanel)components[0];
        iconComponent = (JLabel) (iconPanel.getComponents()[0]);
        textComponent =(JTextField) components[1];
    }

    @Test
    public void canGetAndSetArbitraryText(){
        field.setText("foo");
        assertEquals("foo",field.getText());
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
    public void canCallFunctionWhenTextChanges(){
        final boolean[] change = {false};
        DollarsTextField.ChangeListener listener = new DollarsTextField.ChangeListener(){
            public void textChanged(){
                    change[0] = true;
            }
        };
        field.addTextChangeListener(listener);
        assertFalse("text changed() should have not been called yet", change[0]);
        field.setText("1000");
        assertTrue("text changed() should have been called",change[0]);
    }
    @Test
    public void emptyStringIsZeroDollars(){
        field.setText("");
        assertEquals(new ValidDollars(0),field.getDollars());
    }
    @Test
    public void fieldTextIsNotReformattedWhenFieldLosesFocusAndTheValidIsInvalid() throws Exception {
        field.dispatchEvent(new FocusEvent(field, FocusEvent.FOCUS_GAINED));
        field.setText("xxx");
        field.dispatchEvent(new FocusEvent(field, FocusEvent.FOCUS_LOST));

        assertEquals("xxx", getTextUsingEventThread(field));
    }

    @Test
    public void layout(){

        Component[] components = field.getComponents();

        assertEquals("layout", OverlayLayout.class, field.getLayout().getClass());
        assertEquals("# of components", 2, components.length);

        FlowLayout iconLayout = (FlowLayout) iconPanel.getLayout();
        assertEquals("icon should be contained within a panel", JPanel.class,iconPanel.getClass());
        assertFalse("icon panel should be transparent", iconPanel.isOpaque());
        assertEquals("icon panel layout", FlowLayout.class, iconLayout.getClass());
        assertEquals("icon panel alignment", FlowLayout.RIGHT, iconLayout.getAlignment());

        assertEquals("layout should include text field", JTextField.class, textComponent.getClass());
        assertEquals("layout should include icon", JLabel.class, iconComponent.getClass());

        assertFalse("icon should not be invisible by default",iconComponent.isVisible());
    }
    @Test
    public void canSetAndClearIcon(){
        ImageIcon icon = new ImageIcon();
        field.setIcon(icon);

        assertEquals("icon image", icon, iconComponent.getIcon());
        assertTrue("icon label should be visible", iconComponent.isVisible());

        field.setIcon(null);
        assertFalse("icon label should not be visible", iconComponent.isVisible());
    }
    @Test
    public void settingForegroundColorChangesTextColor(){
        field.setForeground(Color.CYAN);
        assertEquals("can retrieve color ",Color.CYAN, field.getForeground());
        assertEquals("actual text color changed",Color.CYAN, textComponent.getForeground());
    }
    @Test
    public void getForegroundColorIsBasedOnTextColorNotPanelColor(){
        textComponent.setForeground(Color.PINK);
        assertEquals("color is based on text color", Color.PINK, field.getForeground());
    }

    @Test
    public void fieldIsRenderedByDomainClassWhenTextChanges() throws Exception{

        field.setText("10");
        assertEquals("starts black", Color.BLACK,textComponent.getForeground());
        assertFalse("start with no icon", iconComponent.isVisible());
        assertNull("starts with no tooltip", iconComponent.getToolTipText());

        field.setText("  -10");
        assertEquals("should not change text", "  -10", textComponent.getText());
        assertFalse("should change color",Color.BLACK.equals(textComponent.getForeground()));

        field.setText("xxx");
        assertTrue("should set icon", iconComponent.isVisible());
        assertNotNull("should set tooltip text", iconComponent.getToolTipText());
    }

    private String getTextUsingEventThread(DollarsTextField textField) throws InterruptedException, InvocationTargetException {
        final String[] testResult = {null};
        final DollarsTextField field  =  textField;
        SwingUtilities.invokeAndWait(new Runnable(){
            public void run() {
                testResult[0] = (field.getText());
            }
        });
        return testResult[0];
    }
}
