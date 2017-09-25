package com.vowme.app.models;

import java.util.ArrayList;
import java.util.List;

public class GroupedTimesheetItem {
    public String date;
    public Integer totalHours = Integer.valueOf(0);
    public Integer totalMinutes = Integer.valueOf(0);
    List<TimesheetItem> opportunityHours = new ArrayList();

    public void setDate(String date) {
        this.date = date;
    }

    public List<TimesheetItem> getOpportunityHours() {
        return this.opportunityHours;
    }

    public Integer getMinutes() {
        return Integer.valueOf(this.totalMinutes.intValue() % 60);
    }

    public Integer getHours() {
        return Integer.valueOf(this.totalHours.intValue() + (this.totalMinutes.intValue() / 60));
    }
}
