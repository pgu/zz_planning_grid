package com.pgu.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;

public class PlanningHelper {

    public static void setBorder(final Widget w, final String color) {
        final Style style = w.getElement().getStyle();
        style.setBorderColor(color);
        style.setBorderStyle(BorderStyle.SOLID);
        style.setBorderWidth(1, Unit.PX);
    }

}
