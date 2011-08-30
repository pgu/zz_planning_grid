package com.pgu.client;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ToggleButton;

public class ToolbarTaskContainer extends DialogBox {

    FlowPanel container = new FlowPanel();
    FlowPanel containerTasks = new FlowPanel();
    final ToggleButton btnManuelles = new ToggleButton("Manuelles");
    final ToggleButton btnRestantes = new ToggleButton("A replanifier");
    HTML logText;
    PlanningGrid planningGrid;

    public ToolbarTaskContainer(final HTML logText, final PlanningGrid planningGrid) {
        this.logText = logText;
        this.planningGrid = planningGrid;

        setAnimationEnabled(true);
        setAutoHideEnabled(false);
        setAutoHideOnHistoryEventsEnabled(true);
        setGlassEnabled(false);
        setModal(false);
        setText("Tâches");

        final HorizontalPanel toggleBtns = new HorizontalPanel();
        toggleBtns.setSpacing(2);
        toggleBtns.setWidth("100%");
        toggleBtns.add(btnManuelles);
        toggleBtns.add(btnRestantes);
        container.add(toggleBtns);
        container.add(containerTasks);

        container.setPixelSize(300, 300);
        add(container);

        setPopupPosition(Document.get().getClientWidth() / 2, 0);

        setClickManuelles();
        setClickRestantes();
    }

    private void setClickRestantes() {
    }

    private void setClickManuelles() {
        btnManuelles.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                if (btnManuelles.isDown()) {
                    containerTasks.clear();
                    final ToolbarTasks tasks = new ToolbarTasks(logText);
                    planningGrid.setDropControllerFromTasks(tasks.dragController);
                    containerTasks.add(tasks);
                } else {
                    containerTasks.clear();
                }
            }
        });
    }

}
