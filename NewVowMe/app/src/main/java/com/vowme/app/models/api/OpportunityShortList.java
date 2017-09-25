package com.vowme.app.models.api;

public class OpportunityShortList extends PostApiModel {
    private String date = "";
    private String description = "";
    private boolean disabledAccess;
    private String duration = "";
    private int id;
    private boolean isExpired;
    private boolean isShortlisted;
    private String name = "";
    private String orgServiceFocus = "";
    private int organisationId;
    private String organisationName = "";
    private String serviceFocus = "";
    private String source;
    private String state = "";
    private String stateCode = "";
    private String status;
    private String suburb = "";

    public OpportunityShortList(int oppId, String oppSource) {
        this.id = oppId;
        this.source = oppSource;
        this.status = "";
    }
}
