package com.vowme.app.models;

import com.vowme.app.models.api.ViraHoursModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class TimesheetItem extends ViraHoursModel {
    public String dateAsString;
    public String name;

    public TimesheetItem() {
    }

    public TimesheetItem(String name,Integer causeId) {
        super();
        this.name = name;
        this.positionId = causeId;
        this.dateAsString = new SimpleDateFormat("dd/MM/yyyy").format(this.date);
    }

    public TimesheetItem(JSONObject object, String name, Integer causeId) {
        super(object);
        this.name = name;
        this.positionId = causeId;
        if (this.date != null)
        this.dateAsString = new SimpleDateFormat("dd/MM/yyyy").format(this.date);
    }

    public TimesheetItem(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.positionId = object.getInt("positionId");
            this.hours = Integer.valueOf(object.getInt("hours"));
            this.minutes = Integer.valueOf(object.getInt("minutes"));
            this.name = object.getString("name");
            this.dateAsString = object.getString("dateAsString");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
