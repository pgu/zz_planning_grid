package com.pgu.client;

import java.util.Arrays;
import java.util.List;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DropTaskInPlanningFromToolbarDropController extends SimpleDropController {

    private ToolbarTask currentTask;
    private final PlanningGrid planningGrid;

    public DropTaskInPlanningFromToolbarDropController(final Widget dropTarget, final PlanningGrid planningGrid) {
        super(dropTarget);
        this.planningGrid = planningGrid;
    }

    @Override
    public void onEnter(final DragContext context) {
        GWT.log("onEnter");
        for (final Widget widget : context.selectedWidgets) {
            currentTask = (ToolbarTask) widget;
        }
        super.onEnter(context);
    }

    Integer rowTask;
    Integer colTask;
    DragContext context;

    @Override
    public void onDrop(final DragContext context) {
        GWT.log("onDrop");
        this.context = context;

        rowTask = planningGrid.searchRowTask(context);
        if (rowTask == null) {
            return;
        }

        colTask = planningGrid.searchColTask(context);
        if (colTask == null) {
            return;
        }

        selectFamille();
    }

    private void selectFamille() {
        final PopupPanel pop = new PopupPanel(false, true);
        final VerticalPanel container = new VerticalPanel();
        pop.add(container);

        final List<RadioButton> families = Arrays.asList(new RadioButton("famille", "Biscuits"), //
                new RadioButton("famille", "Café"), //
                new RadioButton("famille", "Chips"), //
                new RadioButton("famille", "Pâtes") //
                );

        for (final RadioButton famille : families) {
            container.add(famille);
        }

        final Button btnOk = new Button("Ok");
        container.add(btnOk);

        pop.show();
        pop.center();

        btnOk.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(final ClickEvent event) {
                String selectedFamille = "";
                for (final RadioButton famille : families) {
                    if (famille.getValue()) {
                        selectedFamille = famille.getText();
                    }
                }

                if ("".equals(selectedFamille)) {
                    Window.alert("You must choose a family!");
                    return;
                }

                pop.removeFromParent();

                final TaskPlanning taskPlanning = new TaskPlanning(rowTask, colTask, //
                        calculDurationInMinutes(selectedFamille), // 
                        currentTask, selectedFamille);
                planningGrid.dropTaskInPlanning(taskPlanning, context);
            }

        });
    }

    private int calculDurationInMinutes(final String selectedFamille) {
        int minutes = (selectedFamille.length() - 3) * 30;
        if ("Chips".equals(selectedFamille)) {
            minutes += 10;
        }
        return minutes;
    }

    @Override
    public void onLeave(final DragContext context) {
        GWT.log("onLeave");
        super.onLeave(context);
    }

}
