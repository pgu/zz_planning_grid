package com.pgu.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;

public class TaskPlanningActions extends PopupPanel {

    final ListBox inputHour = new ListBox();
    final ListBox inputMinutes = new ListBox();
    Label title = new Label("Modifier la durée: ");
    final FlowPanel container = new FlowPanel();
    final Button btnCancel = new Button("Annuler");
    final Button btnConfirm = new Button("Confirm");
    final Button btnSuppression = new Button("Supprimer la tâche");

    PlanningGrid planningGrid;

    public TaskPlanningActions(final PlanningGrid planningGrid) {
        super(true, false);
        this.planningGrid = planningGrid;

        setWidth("150px");

        add(container);

        setTitle();
        setModificationDuration();
        setUISeparator();
        setSuppressionTache();

        setActions();
    }

    private void setSuppressionTache() {
        container.add(btnSuppression);
    }

    private void setActions() {
        setActionCancel();
        setActionModifyDuration();
        setActionSuppressionTacke();
    }

    private void setActionSuppressionTacke() {
        btnSuppression.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                final boolean isSure = Window.confirm("Are you sure?");
                if (isSure) {
                    GWT.log("is sure " + isSure);
                    // TODO PGU delete the tache and remove it from the priority queue
                }
                hide();
            }
        });
    }

    private void setActionModifyDuration() {
        btnConfirm.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                final int hour = Integer.parseInt(inputHour.getItemText(inputHour.getSelectedIndex()));
                final int minutes = Integer.parseInt(inputMinutes.getItemText(inputMinutes.getSelectedIndex()));
                final int duration = hour * 60 + minutes;

                if (duration == 0) {
                    Window.alert("Une tâche ne peut pas être inférieure à 5 minutes");
                    return;
                }

                if (duration != task.getDurationInMinutes()) {

                    final boolean isModified = planningGrid.modifyDurationTask(task, duration);
                    if (isModified) {
                        hide();
                    }
                } else {
                    hide();
                }
            }
        });
    }

    private void setActionCancel() {
        btnCancel.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                hide();
            }
        });
    }

    private void setUISeparator() {
        final HTML sep1 = new HTML("");
        final HTML sep2 = new HTML("");
        sep1.setHeight("10px");
        sep2.setHeight("10px");
        sep1.getElement().getStyle().setProperty("borderBottom", "1px solid grey");
        container.add(sep1);
        container.add(sep2);
    }

    private void setModificationDuration() {
        container.add(new Label("Modifier la durée: "));

        final HorizontalPanel inputs = new HorizontalPanel();
        container.add(inputs);

        setHours();
        inputs.add(inputHour);

        final Label hh = new Label("h");
        hh.getElement().getStyle().setMarginTop(5, Unit.PX);
        inputs.add(hh);

        setMinutes();
        inputs.add(inputMinutes);

        final Label min = new Label("min");
        min.getElement().getStyle().setMarginTop(5, Unit.PX);
        inputs.add(min);

        final HorizontalPanel btns = new HorizontalPanel();
        container.add(btns);
        btns.add(btnCancel);
        btns.add(btnConfirm);

    }

    private void setTitle() {
        title.getElement().getStyle().setBackgroundColor("lightgrey");
        container.add(title);
    }

    private void setMinutes() {
        for (int i = 0; i < 60; i += 5) {
            inputMinutes.addItem("" + i, "" + i);
        }
    }

    private void setHours() {
        for (int i = 0; i < 9; i++) {
            inputHour.addItem("" + i, "" + i);
        }
    }

    private TaskPlanning task;

    public void showActions(final TaskPlanning task) {
        this.task = task;
        title.setText(task.getTitle());
        final int hour = task.getDurationInMinutes() / 60;
        inputHour.setSelectedIndex(hour);
        final int minutes = task.getDurationInMinutes() % 60;
        inputMinutes.setSelectedIndex(minutes / 5);
        show();
        center();
    }

}
