package com.vowme.app.models.lookUp;

import org.json.JSONException;
import org.json.JSONObject;

public class Lookup {
    private Integer id;
    private String name;

    public Lookup(int id, String name) {
        this.id = Integer.valueOf(id);
        this.name = name;
    }

    public Lookup(JSONObject object) {
        try {
            if (object != null) {
                this.id = Integer.valueOf(object.getInt("id"));
                this.name = object.getString("name");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Lookup(JSONObject object, boolean fromVolunteer) {
        if (fromVolunteer) {
            try {
                this.id = Integer.valueOf(object.getInt("id"));
                this.name = object.getString("name");
                return;
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
        }
        try {
            this.id = Integer.valueOf(object.getString("Name").hashCode());
            this.name = object.getString("Name");
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }

    public int getId() {
        return this.id.intValue();
    }

    public void setId(int id) {
        this.id = Integer.valueOf(id);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public boolean equals(Object obj) {
        return obj == null ? false : ((Lookup) obj).id.equals(this.id);
    }
}
