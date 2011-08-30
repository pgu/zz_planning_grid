package com.pgu.client;

import com.allen_sauer.gwt.dnd.client.HasDragHandle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ToolbarTask extends AbsolutePanel implements HasDragHandle {

    public interface TaskToolbarPlanningCss extends CssResource {
        String labelTaskToolbar();

        String colorTaskToolbar();
    }

    public interface TaskToolbarPlanningResources extends ClientBundle {
        public static final TaskToolbarPlanningResources INSTANCE = GWT.create(TaskToolbarPlanningResources.class);

        @Source("TaskToolbarPlanningResources.css")
        public TaskToolbarPlanningCss css();

    }

    private final FocusPanel shim = new FocusPanel();
    public Label label;
    public FlowPanel color;

    private final String bgColor;

    public ToolbarTask(final String text, final String bgColor) {
        checkBgColor(bgColor);
        this.bgColor = bgColor;

        //        getElement().getStyle().setPosition(Position.RELATIVE);

        TaskToolbarPlanningResources.INSTANCE.css().ensureInjected();

        shim.addStyleName("demo-PaletteWidget-shim");

        label = new Label(text);
        label.addStyleName(TaskToolbarPlanningResources.INSTANCE.css().labelTaskToolbar());
        color = new FlowPanel();
        color.addStyleName(TaskToolbarPlanningResources.INSTANCE.css().colorTaskToolbar());
        color.getElement().getStyle().setBackgroundColor(bgColor);

        final HorizontalPanel vp = new HorizontalPanel();
        vp.add(color);
        vp.add(label);

        add(vp);
    }

    private static final Integer COLOR_MEDIAN = Integer.parseInt("999999", 16);

    public boolean isBgDark() {
        final Integer color = Integer.parseInt(bgColor.replace("#", ""), 16);
        return color < COLOR_MEDIAN;
    }

    private void checkBgColor(final String bgColor) {
        if (!bgColor.startsWith("#")) {
            throw new IllegalArgumentException("The color must be defined as an hex value.");
        }
        if ("#xxyyzz".length() != bgColor.length()) {
            throw new IllegalArgumentException("The color must be defined as #xxyyzz.");
        }
    }

    public ToolbarTask cloneWidget() {
        return new ToolbarTask(label.getText(), bgColor);
    }

    @Override
    public Widget getDragHandle() {
        return shim;
    }

    @Override
    public void setPixelSize(final int width, final int height) {
        super.setPixelSize(width, height);
        shim.setPixelSize(width, height);
    }

    @Override
    public void setSize(final String width, final String height) {
        super.setSize(width, height);
        shim.setSize(width, height);
    }

    @Override
    protected void onLoad() {
        super.onLoad();
        shim.setPixelSize(getOffsetWidth(), getOffsetHeight());
        add(shim, 0, 0);
    }

    @Override
    protected void onUnload() {
        super.onUnload();
        shim.removeFromParent();
    }

    public String getBgColor() {
        return bgColor;
    }
}
