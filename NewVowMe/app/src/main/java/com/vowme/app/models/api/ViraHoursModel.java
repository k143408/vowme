package com.vowme.app.models.api;

import com.vowme.app.models.TimesheetItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ViraHoursModel extends PostApiModel {
    public Date date;
    public Integer hours;
    public int id;
    public Integer minutes;
    public int positionId;

    public ViraHoursModel() {
        this.hours = Integer.valueOf(0);
        this.minutes = Integer.valueOf(0);
        this.date = Calendar.getInstance().getTime();
    }

    public ViraHoursModel(TimesheetItem model) {
        this.hours = model.hours;
        this.minutes = model.minutes;
        this.date = model.date;
        this.id = model.id;
        this.positionId = model.positionId;
    }

    public ViraHoursModel(JSONObject object) {
        Exception e;
        try {
            this.id = object.getInt("id");
            this.positionId = object.getInt("positionId");
            this.hours = Integer.valueOf(object.getInt("hours"));
            this.minutes = Integer.valueOf(object.getInt("minutes"));
            this.date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(object.getString("date"));
            return;
        } catch (JSONException e2) {
            e = e2;
        } catch (ParseException e3) {
            e = e3;
        }
        e.printStackTrace();
    }
}
