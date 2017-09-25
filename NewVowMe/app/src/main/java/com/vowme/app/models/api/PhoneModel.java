package com.vowme.app.models.api;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneModel extends PostApiModel {
    public int id;
    public String number;
    public int typeId;
    public String typeName;

    public PhoneModel(JSONObject object) {
        try {
            this.id = object.getInt("id");
            this.typeId = object.getInt("typeId");
            this.typeName = object.getString("typeName");
            this.number = object.getString("number") == "null" ? "" : object.getString("number");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
