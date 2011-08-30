package com.pgu.client;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ToolbarTasks extends VerticalPanel {

    public final PickupDragController dragController;

    public ToolbarTasks(final HTML logText) {
        dragController = new PickupDragController(RootPanel.get(), true);
        dragController.setBehaviorMultipleSelection(false);
        dragController.setBehaviorDragProxy(false);
        dragController.setBehaviorScrollIntoView(true);
        dragController.setBehaviorConstrainedToBoundaryPanel(true);
        dragController.addDragHandler(new DemoDragHandler(logText, dragController));
    }

    public void add(final ToolbarTask w) {
        dragController.makeDraggable(w);
        super.add(w);
    }

    ToolbarTask clone;

    @Override
    public boolean remove(final Widget w) {
        final int index = getWidgetIndex(w);
        if (index != -1 && w instanceof ToolbarTask) {
            clone = ((ToolbarTask) w).cloneWidget();
            dragController.makeDraggable(clone);
            insert(clone, index);
        }

        return super.remove(w);
    }

    public String cleanAfterSuccessfulDrop() {
        super.remove(clone);
        return clone.label.getText();
    }

}
