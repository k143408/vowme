package com.vowme.app.models.api;

import org.json.JSONException;
import org.json.JSONObject;

public class VolunteerFewThingsModel extends PostApiModel {
    public String birthDate;
    public String dietaryRequirements;
    public boolean hasAttended;
    public boolean hasVolunteered;
    public int id;
    public String licenceNumber;
    public String medicalHistorical;
    public String tShirtSize;
    public String transport;
    public int transportID;
    public String typeOfCar;

    public VolunteerFewThingsModel(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.birthDate = object.getString("birthDate") == "null" ? "" : object.getString("birthDate");
            this.transportID = object.getInt("transportID");
            this.transport = object.getString("transport") == "null" ? "" : object.getString("transport");
            this.typeOfCar = object.getString("typeOfCar") == "null" ? "" : object.getString("typeOfCar");
            this.tShirtSize = object.getString("tShirtSize") == "null" ? "" : object.getString("tShirtSize");
            this.licenceNumber = object.getString("licenceNumber") == "null" ? "" : object.getString("licenceNumber");
            this.dietaryRequirements = object.getString("dietaryRequirements") == "null" ? "" : object.getString("dietaryRequirements");
            this.medicalHistorical = object.getString("medicalHistorical") == "null" ? "" : object.getString("medicalHistorical");
            this.hasVolunteered = object.getBoolean("hasVolunteered");
            this.hasAttended = object.getBoolean("hasAttended");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
