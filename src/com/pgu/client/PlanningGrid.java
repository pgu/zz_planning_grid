package com.pgu.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.PickupDragController;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class PlanningGrid extends Composite {

    public static final int NB_HOURS = 24;
    public static final int NB_COL_BY_HOUR = 12; // colonne de 5min
    public static final int NB_COL_BY_HALF_HOUR = NB_COL_BY_HOUR / 2;
    public static final int NB_COL = NB_COL_BY_HOUR * NB_HOURS;

    public static final int COL_WIDTH_BY_HOUR = 150;
    public static final int COL_WIDTH_UNIT = COL_WIDTH_BY_HOUR / NB_COL_BY_HOUR;

    final ScrollPanel scroll = new ScrollPanel();
    final AbsolutePanel container = new AbsolutePanel();
    final FlowPanel hourHeaders = new FlowPanel();
    final FlexTable ruler_5min = new FlexTable();
    final FlexTable ruler_persons = new FlexTable();

    FlexCellFormatter ruler_5min_fmt;
    FlexCellFormatter ruler_persons_fmt;

    HTML logText;
    FlowPanel contour = new FlowPanel();

    public PlanningGrid(final List<Person> persons, final HTML logText) {
        this.logText = logText;
        this.persons.clear();
        this.persons.addAll(persons);

        ruler_5min.setCellSpacing(0);
        ruler_5min.setCellPadding(0);
        container.add(ruler_5min);

        hourHeaders.setWidth("1px");
        hourHeaders.setHeight("30px");
        container.add(hourHeaders);

        ruler_persons.setCellSpacing(0);
        ruler_persons.setCellPadding(0);
        container.add(ruler_persons);

        container.getElement().getStyle().setProperty("overflow", "auto");

        contour.setWidth(RootPanel.getBodyElement().getOffsetWidth() - 20 + "px");
        PlanningHelper.setBorder(contour, "lightgrey");
        contour.add(container);

        initWidget(contour);
        ruler_5min_fmt = ruler_5min.getFlexCellFormatter();
        ruler_persons_fmt = ruler_persons.getFlexCellFormatter();

        // /////////////////////////

        for (int col = 0; col < NB_COL; col++) {
            final FlowPanel w = new FlowPanel();
            w.setPixelSize(COL_WIDTH_UNIT, 1);
            ruler_5min.setWidget(0, col, w);
        }

        // /////////////////////////

        int row = 0;
        for (final Person person : persons) {

            ruler_persons.setWidget(row, 0, rowSep());
            rowsSep1.add(row);
            row++;

            ruler_persons.setWidget(row++, 0, rowSepWork());
            ruler_persons.setWidget(row++, 0, rowWorkLine());

            ruler_persons.setWidget(row, 0, rowSep());
            rowsSep2.add(row);
            row++;

            ruler_persons.setWidget(row, 0, rowTask());
            person2rowTask.put(person, row);
            row++;

        }

        // /////////////////////////

    }

    List<Integer> rowsSep1 = new ArrayList<Integer>();
    List<Integer> rowsSep2 = new ArrayList<Integer>();

    public static final int ROW_RULER_HOURS = 0;
    public static final int COL_RULER_PERSONS = 0;

    public void setHourMarkers() {

        final int heightHours = hourHeaders.getElement().getAbsoluteBottom() - ruler_5min.getAbsoluteTop();
        final int heightBody = ruler_persons.getElement().getOffsetHeight();

        final Element td0 = ruler_5min_fmt.getElement(ROW_RULER_HOURS, 0);

        final Element tdNext5min = ruler_5min_fmt.getElement(ROW_RULER_HOURS, 1);
        final int width5min = tdNext5min.getAbsoluteLeft() - td0.getAbsoluteLeft();

        final Element tdNextHour = ruler_5min_fmt.getElement(ROW_RULER_HOURS, NB_COL_BY_HOUR);
        final int widthHour = tdNextHour.getAbsoluteLeft() - td0.getAbsoluteLeft();

        for (int col = 0; col < NB_COL; col++) {

            final Element td = ruler_5min_fmt.getElement(ROW_RULER_HOURS, col);

            td.getStyle().setPosition(Position.RELATIVE);

            if (isStartingHour(col)) {
                final FlowPanel hourHeaderSep = new FlowPanel();
                final Style styleSp = hourHeaderSep.getElement().getStyle();
                styleSp.setPosition(Position.ABSOLUTE);
                styleSp.setTop(0, Unit.PX);
                styleSp.setLeft(0, Unit.PX);
                styleSp.setWidth(1, Unit.PX);
                styleSp.setHeight(heightHours, Unit.PX);
                styleSp.setBackgroundColor("white");
                td.appendChild(hourHeaderSep.getElement());

                final FlowPanel hourHeader = new FlowPanel();
                final Style styleH = hourHeader.getElement().getStyle();
                styleH.setPosition(Position.ABSOLUTE);
                styleH.setTop(0, Unit.PX);
                styleH.setLeft(1, Unit.PX);
                styleH.setWidth(widthHour - 1, Unit.PX);
                styleH.setHeight(heightHours, Unit.PX);
                styleH.setColor("white");
                styleH.setBackgroundColor("blue");
                td.appendChild(hourHeader.getElement());

                final Label hourLabel = new Label(col / NB_COL_BY_HOUR + ":00");
                final Style styleL = hourLabel.getElement().getStyle();
                styleL.setPaddingTop(15, Unit.PX);
                styleL.setPaddingLeft(2, Unit.PX);
                hourHeader.add(hourLabel);

                final FlowPanel hourSepBody = new FlowPanel();
                final Style styleSpBody = hourSepBody.getElement().getStyle();
                styleSpBody.setPosition(Position.ABSOLUTE);
                styleSpBody.setTop(heightHours, Unit.PX);
                styleSpBody.setLeft(0, Unit.PX);
                styleSpBody.setWidth(1, Unit.PX);
                styleSpBody.setHeight(heightBody, Unit.PX);
                styleSpBody.setBackgroundColor("blue");
                td.appendChild(hourSepBody.getElement());

            } else if (isHalfHour(col)) {
                final FlowPanel halfHourSepBody = new FlowPanel();
                final Style styleSpHalfBody = halfHourSepBody.getElement().getStyle();
                styleSpHalfBody.setPosition(Position.ABSOLUTE);
                styleSpHalfBody.setTop(heightHours, Unit.PX);
                styleSpHalfBody.setLeft(0, Unit.PX);
                styleSpHalfBody.setWidth(1, Unit.PX);
                styleSpHalfBody.setHeight(heightBody, Unit.PX);
                styleSpHalfBody.setBackgroundColor("lightgrey");
                td.appendChild(halfHourSepBody.getElement());

            } else if (is5minSpan(col)) {

                final FlowPanel bg5min = new FlowPanel();
                final Style styleBg5min = bg5min.getElement().getStyle();
                styleBg5min.setPosition(Position.ABSOLUTE);
                styleBg5min.setTop(heightHours, Unit.PX);
                styleBg5min.setHeight(heightBody, Unit.PX);
                styleBg5min.setBackgroundColor("#F8f8ff");
                styleBg5min.setWidth(width5min, Unit.PX);

                styleBg5min.setLeft(0, Unit.PX);
                td.appendChild(bg5min.getElement());

                bg5min.setTitle(PlanningHelper.colToHHmm(col));
            }

        }

    }

    private boolean is5minSpan(final int col) {
        return col % 2 == 1;
    }

    private boolean isHalfHour(final int col) {
        return col % NB_COL_BY_HOUR == NB_COL_BY_HALF_HOUR;
    }

    private boolean isStartingHour(final int col) {
        return col % NB_COL_BY_HOUR == 0;
    }

    private Widget rowTask() {
        return buildRow(100);
    }

    private Widget rowWorkLine() {
        return buildRow(4);
    }

    private Widget rowSepWork() {
        return buildRow(12);
    }

    private Widget rowSep() {
        return buildRow(1);
    }

    private FlowPanel buildRow(final int height) {
        final FlowPanel row = new FlowPanel();
        row.setHeight(height + "px");
        row.setWidth("1px");
        return row;
    }

    public void setPersonMarkers() {

        final int widthHours = ruler_5min.getElement().getOffsetWidth();

        for (final Integer rowSep1 : rowsSep1) {
            final Element td = ruler_persons_fmt.getElement(rowSep1, COL_RULER_PERSONS);
            td.getStyle().setPosition(Position.RELATIVE);

            final FlowPanel sep = new FlowPanel();
            final Style style = sep.getElement().getStyle();
            style.setPosition(Position.ABSOLUTE);
            style.setTop(0, Unit.PX);
            style.setLeft(0, Unit.PX);
            style.setHeight(1, Unit.PX);
            style.setBackgroundColor("#a9a9a9");
            style.setWidth(widthHours, Unit.PX);
            td.appendChild(sep.getElement());
        }

        for (final Integer rowSep2 : rowsSep2) {
            final Element td = ruler_persons_fmt.getElement(rowSep2, COL_RULER_PERSONS);
            td.getStyle().setPosition(Position.RELATIVE);

            final FlowPanel sep = new FlowPanel();
            final Style style = sep.getElement().getStyle();
            style.setPosition(Position.ABSOLUTE);
            style.setTop(0, Unit.PX);
            style.setLeft(0, Unit.PX);
            style.setHeight(1, Unit.PX);
            style.setBackgroundColor("lightgrey");
            style.setWidth(widthHours, Unit.PX);
            td.appendChild(sep.getElement());
        }

    }

    Map<Person, Integer> person2rowTask = new HashMap<Person, Integer>();
    List<Person> persons = new ArrayList<Person>();

    private PickupDragController planningDragController;

    public void setDropControllerFromTasks(final PickupDragController dragController) {

        planningDragController = new PickupDragController(container, true);
        planningDragController.setBehaviorMultipleSelection(false);
        planningDragController.setBehaviorDragProxy(false);
        planningDragController.setBehaviorScrollIntoView(true);
        planningDragController.setBehaviorConstrainedToBoundaryPanel(true);
        planningDragController.addDragHandler(new DemoDragHandler(logText, planningDragController));
        planningDragController.setBehaviorDragStartSensitivity(1);

        planningDragController.unregisterDropControllers();
        planningDragController.registerDropController(new MoveTaskInRowDropController(container, this));

        dragController.unregisterDropControllers();

        final DropTaskInPlanningFromToolbarDropController dropTaskIntoPlanningController = new DropTaskInPlanningFromToolbarDropController(
                contour, this);
        dragController.registerDropController(dropTaskIntoPlanningController);

    }

    //////////////////////////////////////////////////////////////////////////////////////

    public Integer searchColTask(final DragContext context) {
        return searchColTask(context, null);
    }

    public Integer searchColTask(final DragContext context, final Integer X) {

        int low = 0;
        int high = ruler_5min.getCellCount(PlanningGrid.ROW_RULER_HOURS) - 1;
        final int mX = null != X ? X : context.mouseX;

        while (low <= high) {
            final int mid = low + high >>> 1;

            final Element el = ruler_5min_fmt.getElement(PlanningGrid.ROW_RULER_HOURS, mid);
            final int left = el.getAbsoluteLeft();
            final int right = el.getAbsoluteRight();

            if (mX < left) {
                high = mid - 1;
            } else if (mX > right) {
                low = mid + 1;
            } else {
                return mid;
            }

        }
        return null;

    }

    public Integer searchRowTask(final DragContext context) {
        resetPersonsTested();

        int low = 0;
        int high = persons.size() - 1;
        final int mY = context.mouseY;

        while (low <= high) {
            final int mid = low + high >>> 1;
            final Person person = persons.get(mid);

            if (isPersonAlreadyTested(person)) {
                return null; // the mouse is not on a row task
            }

            final Element el = ruler_persons.getWidget(person2rowTask.get(person), PlanningGrid.COL_RULER_PERSONS)
                    .getElement();
            final int top = el.getAbsoluteTop();
            final int bottom = el.getAbsoluteBottom();

            if (mY < top) {
                high = mid - 1;
            } else if (mY > bottom) {
                low = mid + 1;
            } else {
                return person2rowTask.get(person);
            }

        }
        return null;
    }

    List<Person> personsTestedForRowTask = new ArrayList<Person>();

    private void resetPersonsTested() {
        personsTestedForRowTask.clear();
    }

    private boolean isPersonAlreadyTested(final Person person) {
        final boolean isPersonAlreadyTested = personsTestedForRowTask.contains(person);
        if (!isPersonAlreadyTested) {
            personsTestedForRowTask.add(person);
        }
        return isPersonAlreadyTested;
    }

    public void dropTaskInPlanning(final TaskPlanning currentTask, final DragContext context) {
        GWT.log("dropTaskInPlanning");
        if (!isOverlapping(currentTask)) {
            setTaskOnPlanning(currentTask, context);
        }
    }

    public void moveTaskInPlanning(final int colTask, final TaskPlanning currentTask, final DragContext context) {
        GWT.log("moveTaskInPlanning");
        removeTaskFromPersonTasks(currentTask);

        final TaskPlanning copy = currentTask.cloneTask(colTask);
        if (isOverlapping(copy)) {
            restoreTaskInternal(context, currentTask);
        } else {
            setTaskOnPlanning(copy, context);
        }
    }

    public void restoreTask(final DragContext context, final TaskPlanning currentTask) {
        GWT.log("restoreTask");
        removeTaskFromPersonTasks(currentTask);
        restoreTaskInternal(context, currentTask);
    }

    private void restoreTaskInternal(final DragContext context, final TaskPlanning currentTask) {
        GWT.log("restoreTaskInternal");
        setTaskOnPlanning(currentTask.cloneTask(currentTask.getColTask()), context);
    }

    private void setTaskOnPlanning(final TaskPlanning task, final DragContext context) {
        GWT.log("setTaskOnPlanning");
        final Element tdPerson = ruler_persons_fmt.getElement(task.getRowTask(), PlanningGrid.COL_RULER_PERSONS);

        final Style style = task.getElement().getStyle();
        style.setPosition(Position.ABSOLUTE);
        style.setTop(tdPerson.getAbsoluteTop() - container.getAbsoluteTop(), Unit.PX);

        final Element tdHour = ruler_5min_fmt.getElement(PlanningGrid.ROW_RULER_HOURS, task.getColTask());
        style.setLeft(tdHour.getAbsoluteLeft() - tdPerson.getAbsoluteLeft(), Unit.PX);

        style.setHeight(tdPerson.getOffsetHeight(), Unit.PX);

        final Element tdHourEnd = ruler_5min_fmt.getElement(PlanningGrid.ROW_RULER_HOURS,
                task.getColTask() + task.getDurationInMinutes() / 5);

        style.setWidth(tdHourEnd.getAbsoluteLeft() - tdHour.getAbsoluteLeft(), Unit.PX);

        planningDragController.makeDraggable(task);

        container.add(task);

        ////////////////////////////////////////////////////////////////////////////////
        addTaskToPersonTasks(task);
    }

    private void addTaskToPersonTasks(final TaskPlanning task) {
        final Person person = getPersonFromRow(task.getRowTask());
        if (person2tasks.containsKey(person)) {
            person2tasks.get(person).add(getTaskPlanningDto(task));
        } else {
            final PriorityQueue<TaskPlanningDto> tasks = new PriorityQueue<TaskPlanningDto>();
            tasks.add(getTaskPlanningDto(task));
            person2tasks.put(person, tasks);
        }
    }

    private TaskPlanningDto getTaskPlanningDto(final TaskPlanning task) {
        return new TaskPlanningDto(//
                task.getStartInMinutes(), //
                task.getDurationInMinutes(), //
                task.getPerimetre(), //
                task.getTask().label.getText());
    }

    private Person getPersonFromRow(final int rowTask) {
        for (final Entry<Person, Integer> entry : person2rowTask.entrySet()) {
            if (rowTask == entry.getValue()) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("The row is not associated to a person: " + rowTask);
    }

    Map<Person, PriorityQueue<TaskPlanningDto>> person2tasks = new HashMap<Person, PriorityQueue<TaskPlanningDto>>();

    private void removeTaskFromPersonTasks(final TaskPlanning currentTask) {
        GWT.log("removeTaskFromPersonTasks");
        final int startOriginal = currentTask.getStartInMinutes();

        final Person person = getPersonFromRow(currentTask.getRowTask());
        TaskPlanningDto taskToRemove = null;

        final PriorityQueue<TaskPlanningDto> tasks = person2tasks.get(person);
        for (final TaskPlanningDto taskDto : tasks) {
            if (startOriginal == taskDto.getStartInMinutes()) {
                taskToRemove = taskDto;
                break;
            }
        }
        if (null == taskToRemove) {
            throw new NullPointerException("la task n'a pas ete trouvee");
        }
        tasks.remove(taskToRemove);
    }

    private boolean isOverlapping(final TaskPlanning task) {
        GWT.log("isOverlapping");
        final int start = task.getStartInMinutes();
        final int end = task.getEndInMinutes();

        final Person person = getPersonFromRow(task.getRowTask());
        if (person2tasks.containsKey(person)) {
            for (final TaskPlanningDto taskDto : person2tasks.get(person)) {
                if (start >= taskDto.getEndInMinutes()) {
                    continue;
                } else {
                    if (end <= taskDto.getStartInMinutes()) {
                        continue;
                    } else {
                        Window.alert("La tâche va empiéter sur \"" + taskDto.getLabel() + "\"");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    final TaskPlanningActions taskActions = new TaskPlanningActions(this);

    public void showTaskPlanningMenu(final TaskPlanning task) {
        taskActions.showActions(task);
    }

    public boolean modifyDurationTask(final TaskPlanning task, final int newDuration) {

        final int durationBackup = task.getDurationInMinutes();

        removeTaskFromPersonTasks(task);

        task.setDurationInMinutes(newDuration);

        final boolean canBeModified = !isOverlapping(task);
        if (canBeModified) {
            refreshDuration(task);
        } else {
            task.setDurationInMinutes(durationBackup);
        }

        addTaskToPersonTasks(task);
        return canBeModified;
    }

    private void refreshDuration(final TaskPlanning task) {
        final Element tdHour = ruler_5min_fmt.getElement(PlanningGrid.ROW_RULER_HOURS, task.getColTask());

        final Element tdHourEnd = ruler_5min_fmt.getElement(PlanningGrid.ROW_RULER_HOURS,
                task.getColTask() + task.getDurationInMinutes() / 5);

        task.getElement().getStyle().setWidth(tdHourEnd.getAbsoluteLeft() - tdHour.getAbsoluteLeft(), Unit.PX);
    }
}
