package com.pgu.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;

public class TaskPlanning extends HTML {

    private final Integer rowTask;
    private final Integer colTask;
    private final ToolbarTask task;
    private final String perimetre;
    private final PlanningGrid planningGrid;
    private int durationInMinutes;

    public TaskPlanning(final Integer rowTask, final Integer colTask, final int durationInMinutes,
            final ToolbarTask task, final String perimetre, final PlanningGrid planningGrid) {
        MyResources.INSTANCE.css().ensureInjected();
        this.rowTask = rowTask;
        this.colTask = colTask;
        this.durationInMinutes = durationInMinutes;
        this.task = task;
        this.perimetre = perimetre;
        this.planningGrid = planningGrid;
        setColors();
        showLabel();
        setClickHandler();
    }

    private void setClickHandler() {
        addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                planningGrid.showTaskPlanningMenu(TaskPlanning.this);
            }
        });
    }

    private void setColors() {
        final Style style = getElement().getStyle();

        addStyleName(MyResources.INSTANCE.css().taskPlanning());

        style.setBackgroundColor(task.getBgColor());
        if (task.isBgDark()) {
            style.setColor("white");
        }
    }

    public TaskPlanning cloneTask(final int colTask) {
        return new TaskPlanning(rowTask, colTask, durationInMinutes, task, perimetre, planningGrid);
    }

    private void showLabel() {
        final String labelHours = getLabelHours();
        setHTML("&nbsp;&nbsp;" + //
                labelHours + //

                "<br/>" + //
                "&nbsp;&nbsp;" + //
                perimetre + //

                "<br/>" + //
                "&nbsp;&nbsp;" + //
                task.label.getText() //
        );

        setTitle(labelHours + " " + perimetre + " " + task.label.getText());
    }

    public int getStartInMinutes() {
        return 5 * colTask;
    }

    private String getLabelHours() {
        final int totalMin = getStartInMinutes();
        return PlanningHelper.toStringHHmm(totalMin) //
                + " - " //
                + PlanningHelper.toStringHHmm(totalMin + durationInMinutes) //
        ;
    }

    public Integer getRowTask() {
        return rowTask;
    }

    public Integer getColTask() {
        return colTask;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public ToolbarTask getTask() {
        return task;
    }

    public String getPerimetre() {
        return perimetre;
    }

    public int getEndInMinutes() {
        return getStartInMinutes() + durationInMinutes;
    }

    public void setDurationInMinutes(final int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
        showLabel();
    }

}
