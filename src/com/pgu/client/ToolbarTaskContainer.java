package com.pgu.client;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ToggleButton;

public class ToolbarTaskContainer extends DialogBox {

    private static final int HEIGHT_DEFAULT_CONTAINER = 300;
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

        container.setPixelSize(300, HEIGHT_DEFAULT_CONTAINER);
        add(container);

        setPopupPosition(Document.get().getClientWidth() / 2, 0);

        setClickManuelles();
        setClickRestantes();

    }

    @Override
    public void show() {
        updateContainerHeight();
        super.show();

        btnManuelles.setDown(true);
        updateTaskManuelles();
    }

    private void setClickRestantes() {
        btnRestantes.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                if (btnRestantes.isDown()) {
                    btnManuelles.setDown(false);

                    updateTaskRestantes();

                } else {
                    containerTasks.clear();
                }
            }

        });
    }

    private void updateTaskRestantes() {
        containerTasks.clear();

        if (!taskRestantes.isEmpty()) {

            final ToolbarTasks tasks = new ToolbarTasks(logText);
            for (final Entry<String, String> e : taskRestantes.entrySet()) {
                tasks.add(new ToolbarTask(e.getKey(), e.getValue()));
            }

            planningGrid.setDropControllerFromTaskRestantes(tasks);
            containerTasks.add(tasks);
        }
    }

    private void updateContainerHeight() {
        final int maxElements = Math.max(taskRestantes.size(), taskManuelles.size());
        final int heightCalculated = 25 * maxElements + btnManuelles.getElement().getAbsoluteBottom()
                - container.getAbsoluteTop();
        if (heightCalculated > HEIGHT_DEFAULT_CONTAINER && heightCalculated != container.getOffsetHeight()) {
            GWT.log("h _offs > " + container.getOffsetHeight());
            container.setHeight(heightCalculated + "px");
            GWT.log("h calc > " + heightCalculated);
            GWT.log("h offs > " + container.getOffsetHeight());
        }
    }

    private void setClickManuelles() {
        btnManuelles.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                if (btnManuelles.isDown()) {
                    btnRestantes.setDown(false);

                    updateTaskManuelles();
                } else {
                    containerTasks.clear();
                }
            }

        });
    }

    private void updateTaskManuelles() {
        containerTasks.clear();
        if (!taskManuelles.isEmpty()) {
            final ToolbarTasks tasks = new ToolbarTasks(logText);
            for (final Entry<String, String> e : taskManuelles.entrySet()) {
                tasks.add(new ToolbarTask(e.getKey(), e.getValue()));
            }

            planningGrid.setDropControllerFromTaskDuJours(tasks.dragController);
            containerTasks.add(tasks);
        }
    }

    public void cleanTaskRestantesAfterSuccessfulDrop() {
        if (btnRestantes.isDown()) {
            final ToolbarTasks tt = (ToolbarTasks) containerTasks.getWidget(0);
            taskRestantes.remove(tt.cleanAfterSuccessfulDrop());
        }
    }

    final Map<String, String> taskRestantes = new LinkedHashMap<String, String>();
    private final Map<String, String> taskManuelles = new LinkedHashMap<String, String>();

    public void setTaskRestantes(final Map<String, String> taskRestantes) {
        this.taskRestantes.clear();
        this.taskRestantes.putAll(taskRestantes);

    }

    public void setTaskManuelles(final Map<String, String> taskManuelles) {
        this.taskManuelles.clear();
        this.taskManuelles.putAll(taskManuelles);
    }

    public void refreshTaskRestantes() {
        updateContainerHeight();
        if (btnRestantes.isDown()) {
            updateTaskRestantes();
        }
    }

}
