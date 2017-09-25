package com.vowme.app.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupedEOIItem {
    public Date dateGroupedEoi;
    public List<OpportunityItem> expressOfInterest = new ArrayList();

    public Date getDateGroupedEoi() {
        return this.dateGroupedEoi;
    }

    public void setDateGroupedEoi(Date dateGroupedEoi) {
        this.dateGroupedEoi = dateGroupedEoi;
    }

    public List<OpportunityItem> getExpressOfInterest() {
        return this.expressOfInterest;
    }

    public void setExpressOfInterest(List<OpportunityItem> expressOfInterest) {
        this.expressOfInterest = expressOfInterest;
    }
}
