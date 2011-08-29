package com.pgu.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

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
        setClickHandler();
    }

    private void setClickHandler() {
        addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                final PopupPanel pop = new PopupPanel(false, true);
                final VerticalPanel container = new VerticalPanel();
                pop.add(container);
                container.add(new Label("Modifier la durée (en minutes): "));
                final TextBox inputDuree = new TextBox();
                container.add(inputDuree);
                inputDuree.setText(durationInMinutes + "");

                final Button btnConfirm = new Button("Confirm");
                container.add(btnConfirm);
                final Button btnSuppression = new Button("Supprimer la tâche");
                container.add(btnSuppression);

                pop.show();
                pop.center();

                btnConfirm.addClickHandler(new ClickHandler() {

                    @Override
                    public void onClick(final ClickEvent event) {
                        // TODO PGU modifier la cell, check it does not overlap
                        pop.hide();
                    }
                });
                btnSuppression.addClickHandler(new ClickHandler() {

                    @Override
                    public void onClick(final ClickEvent event) {
                        final boolean isSure = Window.confirm("Are you sure?");
                        if (isSure) {
                            GWT.log("is sure " + isSure);
                            // TODO PGU delete the tache and remove it from the priority queue
                        }
                        pop.hide();
                    }
                });

            }
        });
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
