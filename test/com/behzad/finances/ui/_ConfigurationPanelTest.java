package com.behzad.finances.ui;


import static org.junit.Assert.*;


import com.behzad.finances.values.ValidDollars;
import net.miginfocom.swing.MigLayout;
import org.junit.*;

import javax.swing.*;
import java.awt.*;


public class _ConfigurationPanelTest {

    private ConfigurationPanel panel;
    private ApplicationModel model;

    private DollarsTextField startingBalanceField(){
        return (DollarsTextField) panel.getComponents()[1];
    }
    private DollarsTextField yearlySpendingField(){return (DollarsTextField)panel.getComponent(5); }

    @Before
    public void setup(){
        model = new ApplicationModel();
        panel =  new ConfigurationPanel(model);
    }

    @Test
    public void layout(){
        MigLayout manager = (MigLayout) panel.getLayout();
        assertEquals("layout", MigLayout.class, panel.getLayout().getClass() );
        assertEquals("layout constraints", "fillx, wrap 2", manager.getLayoutConstraints());
        assertEquals("column constraints", "[right]rel[grow]", manager.getColumnConstraints());

        Component[] components = panel.getComponents();
        assertEquals("# of components", 6, components.length);
        assertFormField("starting balance", components[0], components[1]);
        assertFormField("cost basis", components[2],components[3]);
        assertFormField("yearly spending", components[4], components[5]);

        assertEquals("cost basis label", JLabel.class,components[2].getClass());
        assertEquals("cost basis field", DollarsTextField.class,costBasisField().getClass());
        assertEquals("cost basis field constraint","growx", manager.getComponentConstraints(costBasisField()));
    }

    private void assertFormField(String message, Component label, Component field) {
        MigLayout manager=(MigLayout) panel.getLayout();
        assertEquals(message  +"label", JLabel.class, label.getClass());
        assertEquals(message+ "field", DollarsTextField.class, field.getClass());
        assertEquals(message + "field constraint", "growx", manager.getComponentConstraints(field));
    }

    private DollarsTextField costBasisField() {
        return (DollarsTextField) panel.getComponent(3);
    }

    @Test
    public void fieldInitializedToModelsValue(){
        assertEquals("starting balance field text", model.startingBalance(), startingBalanceField().getDollars());
        assertEquals("cost basis field text", model.startingCostBasis(), costBasisField().getDollars());
        assertEquals("yearly spending field text", model.yearlySpending(), yearlySpendingField().getDollars());
    }

    @Test
    public void startingBalanceFieldShouldUpdateApplicationModel(){
        __ApplicationModelSpy mockModel = new __ApplicationModelSpy();
        panel = new ConfigurationPanel(mockModel);
        startingBalanceField().setText("668");
        assertEquals("application model should be updated", new ValidDollars(668), mockModel.setStartingBalanceCalledWith);
    }

    @Test
    public void costBasisFieldUpdatesApplicationModel(){
        __ApplicationModelSpy mockModel = new __ApplicationModelSpy();
        panel = new ConfigurationPanel(mockModel);

        costBasisField().setText("670");
        assertEquals("application model should be updated", new ValidDollars(670), mockModel.setCostBasisCalledWith);
    }
    @Test
    public void yearlySpendingFieldUpdatesApplicationModel(){
        __ApplicationModelSpy mockModel = new __ApplicationModelSpy();
        panel = new ConfigurationPanel(mockModel);

        yearlySpendingField().setText("672");
        assertEquals("application model should be updated", new ValidDollars(672), mockModel.setYearlySpendingCalledWith);
    }


}
