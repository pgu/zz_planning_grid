package com.pgu.client;

import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class ToolbarTasks extends HorizontalPanel {

    public final PickupDragController dragController;

    public ToolbarTasks(final HTML logText) {
        dragController = new PickupDragController(RootPanel.get(), true);
        dragController.setBehaviorMultipleSelection(false);
        dragController.setBehaviorDragProxy(false);
        dragController.setBehaviorScrollIntoView(true);
        dragController.setBehaviorConstrainedToBoundaryPanel(true);
        dragController.addDragHandler(new DemoDragHandler(logText, dragController));
        add(new ToolbarTask("Montage TG", "#AAAAAA"));
        add(new ToolbarTask("Réimplantation", "#000000"));
        add(new ToolbarTask("Inventaire tournant", "#0099FF"));
        add(new ToolbarTask("Audit de prix", "#009900"));
        add(new ToolbarTask("Pose des labels", "#FF9900"));

    }

    public void add(final ToolbarTask w) {
        dragController.makeDraggable(w);
        super.add(w);
    }

    @Override
    public boolean remove(final Widget w) {
        final int index = getWidgetIndex(w);
        if (index != -1 && w instanceof ToolbarTask) {
            final ToolbarTask clone = ((ToolbarTask) w).cloneWidget();
            dragController.makeDraggable(clone);
            insert(clone, index);
        }

        return super.remove(w);
    }

}
