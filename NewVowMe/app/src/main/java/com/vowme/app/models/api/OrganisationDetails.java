package com.vowme.app.models.api;

import com.facebook.internal.ServerProtocol;

import org.json.JSONException;
import org.json.JSONObject;

public class OrganisationDetails {
    private String description;
    private int id;
    private String logoFileName;
    private String name;
    private String postcode;
    private String serviceFocus;
    private String state;
    private String stateCode;
    private String street;
    private String suburb;
    private String website;

    public OrganisationDetails() {

    }

    public OrganisationDetails(JSONObject object, boolean isAutenticatedSearch) {
        try {
            this.id = object.getInt("id");
            this.name = object.getJSONObject("userInfo").getString("organizationName");
            this.description = object.getJSONObject("userInfo").getString("aboutMe");
            //this.serviceFocus = object.getString("serviceFocus");
            this.street = object.getJSONObject("userInfo").getString("address");
            this.postcode = object.getJSONObject("userInfo").getString("zipcode");
            //this.suburb = object.getString("suburb");
            this.state = "";//object.getString(ServerProtocol.DIALOG_PARAM_STATE);
            //this.stateCode = object.getString("stateCode");
            this.website = object.getString("email");
            this.logoFileName = "";//object.getString("logoFileName");
            return;

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getServiceFocus() {
        return this.serviceFocus;
    }

    public String getStreet() {
        return this.street;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public String getSuburb() {
        return this.suburb;
    }

    public String getState() {
        return this.state;
    }

    public String getWebsite() {
        return this.website;
    }

    public String getLogoFileName() {
        return this.logoFileName;
    }

    public String getStateCode() {
        return this.stateCode;
    }
}
