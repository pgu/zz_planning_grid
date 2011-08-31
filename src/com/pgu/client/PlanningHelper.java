package com.pgu.client;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Widget;

public class PlanningHelper {

    private PlanningHelper() {
        throw new UnsupportedOperationException();
    }

    public static void setBorder(final Widget w, final String color) {
        final Style style = w.getElement().getStyle();
        style.setBorderColor(color);
        style.setBorderStyle(BorderStyle.SOLID);
        style.setBorderWidth(1, Unit.PX);
    }

    public static int colToMinutes(final int col) {
        return col * 5;
    }

    public static String colToHHmm(final int col) {
        return toStringHHmm(colToMinutes(col));
    }

    public static String toStringHHmm(final int totalMin) {
        final int hour = totalMin / 60;
        final int min = totalMin % 60;
        final String minRestantes = (min < 10 ? "0" : "") + Integer.toString(min);

        return hour + ":" + minRestantes;
    }

    public static boolean fitsInPlanningHours(final int start, final int duration) {
        return start + duration <= PlanningGrid.NB_HOURS * 60;
    }

}
