package com.pgu.client;

import com.allen_sauer.gwt.dnd.client.DragEndEvent;
import com.allen_sauer.gwt.dnd.client.DragHandler;
import com.allen_sauer.gwt.dnd.client.DragStartEvent;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class DemoDragHandler implements DragHandler {

    private static final String BLUE = "#4444BB";
    private static final String GREEN = "#44BB44";
    private static final String RED = "#BB4444";

    private final HTML eventTextArea;
    private final PickupDragController dragController;

    DemoDragHandler(final HTML dragHandlerHTML, final PickupDragController dragController) {
        eventTextArea = dragHandlerHTML;
        this.dragController = dragController;
    }

    @Override
    public void onDragEnd(final DragEndEvent event) {
        log("onDragEnd: " + event, RED);

        // remove the widgets in order to clear the screen from an unsuccessful drag'n'drop
        for (final Widget widget : dragController.getSelectedWidgets()) {
            widget.removeFromParent();
        }
    }

    @Override
    public void onDragStart(final DragStartEvent event) {
        log("onDragStart: " + event, GREEN);
    }

    @Override
    public void onPreviewDragEnd(final DragEndEvent event) throws VetoDragException {
        log("<br>onPreviewDragEnd: " + event, BLUE);
    }

    @Override
    public void onPreviewDragStart(final DragStartEvent event) throws VetoDragException {
        clear();
        log("onPreviewDragStart: " + event, BLUE);
    }

    private void clear() {
        eventTextArea.setHTML("");
    }

    private void log(final String text, final String color) {
        eventTextArea.setHTML(eventTextArea.getHTML() + (eventTextArea.getHTML().length() == 0 ? "" : "<br>")
                + "<span style='color: " + color + "'>" + text + "</span>");
    }
}
