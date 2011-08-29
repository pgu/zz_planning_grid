package com.pgu.client;

import com.google.gwt.user.client.ui.HTML;

public class TaskPlanning extends HTML {

    private final Integer rowTask;
    private final Integer colTask;
    private final int durationInMinutes;
    private final ToolbarTask task;
    private final String perimetre;

    public TaskPlanning(final Integer rowTask, final Integer colTask, final int durationInMinutes,
            final ToolbarTask task, final String perimetre) {
        this.rowTask = rowTask;
        this.colTask = colTask;
        this.durationInMinutes = durationInMinutes;
        this.task = task;
        this.perimetre = perimetre;
        setColors();
        showLabel();
    }

    private void setColors() {
        getElement().getStyle().setBackgroundColor(task.getBgColor());
        if (task.isBgDark()) {
            getElement().getStyle().setColor("white");
        }
    }

    public TaskPlanning cloneTask(final int colTask) {
        return new TaskPlanning(rowTask, colTask, durationInMinutes, task, perimetre);
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

    public String getLabelHours() {
        final int totalMin = getStartInMinutes();
        return toStringHHmm(totalMin) //
                + " - " //
                + toStringHHmm(totalMin + durationInMinutes) //
        ;
    }

    public static String toStringHHmm(final int totalMin) {
        final int hour = totalMin / 60;
        final int min = totalMin % 60;
        final String minRestantes = (min < 10 ? "0" : "") + Integer.toString(min);

        return hour + ":" + minRestantes;
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

}
