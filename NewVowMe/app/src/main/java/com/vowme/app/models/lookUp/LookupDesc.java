package com.vowme.app.models.lookUp;

import org.json.JSONException;
import org.json.JSONObject;

public class LookupDesc extends Lookup {
    private String description;

    public LookupDesc(int id, String name, String description) {
        super(id, name);
        this.description = description;
    }

    public LookupDesc(Lookup item, String description) {
        super(item.getId(), item.getName());
        this.description = description;
    }

    public LookupDesc(JSONObject object) {
        super(object);
        try {
            this.description = object.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
