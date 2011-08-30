package com.pgu.client;

import java.util.Arrays;
import java.util.List;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Zz_planning_grid implements EntryPoint {
    @Override
    public void onModuleLoad() {
        // final Planning planning = new Planning();
        // RootPanel.get().add(planning);

        //        final AbsolutePanel boundaryPanel = new AbsolutePanel();
        //        RootPanel.get().add(boundaryPanel);
        //        boundaryPanel.setPixelSize(400, 100);
        //        boundaryPanel.getElement().getStyle().setProperty("border", "1px solid green");

        final PickupDragController dragController = new PickupDragController(RootPanel.get(), true);
        dragController.setBehaviorMultipleSelection(false);

        final HTML logText = new HTML("");
        final DemoDragHandler demoDragHandler = new DemoDragHandler(logText, dragController);
        dragController.addDragHandler(demoDragHandler);

        final HTML a = new HTML();
        //        boundaryPanel.add(a, 200, 10);
        final Style style = a.getElement().getStyle();
        style.setPosition(Position.ABSOLUTE);
        style.setTop(0, Unit.PX);
        style.setLeft(50, Unit.PX);
        style.setHeight(50, Unit.PX);
        style.setWidth(50, Unit.PX);
        style.setBackgroundColor("orange");
        a.setHTML("DRAG THIS!");
        //        final SimplePanel fp = new SimplePanel();
        //        fp.add(a);

        dragController.makeDraggable(a);

        final FlowPanel tf = new FlowPanel();
        PlanningHelper.setBorder(tf, "pink");
        tf.setPixelSize(50, 50);

        final FlexTable t = new FlexTable();
        PlanningHelper.setBorder(t, "grey");
        t.setWidget(0, 0, tf);

        final AbsolutePanel container = new AbsolutePanel();
        PlanningHelper.setBorder(container, "blue");
        container.add(t);

        //        RootPanel.get().add(container);
        container.add(a);
        //        t.getFlexCellFormatter().getElement(0, 0).appendChild(fp.getElement());
        //        RootPanel.get().add(fp);

        final List<Person> persons = Arrays.asList(new Person(1L, "John Isner", 5, 12), //
                new Person(2L, "René Char", 4, 12), //
                new Person(3L, "Jacques Lelièvre", 7, 13));

        // //////////////////////////////////////////////////////////////////////

        final PlanningGrid pg = new PlanningGrid(persons, logText);
        final FlowPanel sep = new FlowPanel();
        sep.setPixelSize(10, 100);

        RootPanel.get().add(sep);
        RootPanel.get().add(pg);
        RootPanel.get().add(logText);

        pg.setHourMarkers();
        pg.setPersonMarkers();

        final ToolbarTaskContainer tasksPanel = new ToolbarTaskContainer(logText, pg);
        tasksPanel.show();
    }
}
