package com.pgu.client;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;

public class MoveTaskInRowDropController extends SimpleDropController {

    private TaskPlanning currentTask;
    private final PlanningGrid planningGrid;

    public MoveTaskInRowDropController(final AbsolutePanel dropTarget, final PlanningGrid planningGrid) {
        super(dropTarget);
        this.planningGrid = planningGrid;
    }

    @Override
    public void onEnter(final DragContext context) {
        GWT.log("..onEnter");
        for (final Widget widget : context.selectedWidgets) {
            currentTask = (TaskPlanning) widget;
        }
        super.onEnter(context);
    }

    @Override
    public void onDrop(final DragContext context) {
        GWT.log("..onDrop");
        super.onDrop(context);

        final Integer rowTask = planningGrid.searchRowTask(context);
        if (rowTask == null || rowTask != currentTask.getRowTask()) {
            planningGrid.restoreTask(context, currentTask);
            return;
        }

        final Integer colTask = planningGrid.searchColTask(context, currentTask.getAbsoluteLeft());
        if (colTask == null) {
            planningGrid.restoreTask(context, currentTask);
            return;
        }

        planningGrid.moveTaskInPlanning(colTask, currentTask, context);

    }

    @Override
    public void onLeave(final DragContext context) {
        GWT.log("..onLeave");
        super.onLeave(context);
    }

    @Override
    public void onMove(final DragContext context) {
        GWT.log("..onMove");
        super.onMove(context);
    }

    @Override
    public void onPreviewDrop(final DragContext context) throws VetoDragException {
        GWT.log("..onPreviewDrop");
        super.onPreviewDrop(context);
    }

}
