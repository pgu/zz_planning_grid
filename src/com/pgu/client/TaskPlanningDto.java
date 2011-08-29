package com.pgu.client;

import com.google.gwt.user.client.rpc.IsSerializable;

public class TaskPlanningDto implements IsSerializable, Comparable<TaskPlanningDto> {

    private final Integer startInMinutes;
    private final int durationInMinutes;
    private final String perimetre;
    private final String label;

    public TaskPlanningDto(final Integer startInMinutes, final int durationInMinutes, final String perimetre,
            final String label) {
        if (startInMinutes == null) {
            throw new NullPointerException("a start must be given");
        }
        this.startInMinutes = startInMinutes;
        this.durationInMinutes = durationInMinutes;
        this.perimetre = perimetre;
        this.label = label;
    }

    public Integer getStartInMinutes() {
        return startInMinutes;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public String getPerimetre() {
        return perimetre;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public int compareTo(final TaskPlanningDto that) {
        return startInMinutes.compareTo(that.startInMinutes);
    }

    public int getEndInMinutes() {
        return startInMinutes + durationInMinutes;
    }

}
