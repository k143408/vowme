package com.vowme.app.models.lookUp;

import org.json.JSONException;
import org.json.JSONObject;

public class LookupChild extends Lookup {
    private int parentId;

    public LookupChild(int id, String name, int parentId) {
        super(id, name);
        this.parentId = parentId;
    }

    public LookupChild(JSONObject object) {
        super(object);
        try {
            this.parentId = object.getInt("parentId");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getParentId() {
        return this.parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
