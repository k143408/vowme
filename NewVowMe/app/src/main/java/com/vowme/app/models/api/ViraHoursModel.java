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
        try {
            if (object != null) {
                this.id = object.getInt("id");
                this.positionId = 0;//object.getInt("positionId");
                this.hours = Integer.valueOf(object.getInt("hours"));
                this.minutes = Integer.valueOf(object.getInt("minutes"));
                this.date = new SimpleDateFormat("yyyy-MM-dd").parse(object.getString("createAt"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
