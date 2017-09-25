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
            if (isAutenticatedSearch) {

                this.id = object.getInt("id");
                this.name = object.getString("name");
                this.description = object.getString("description");
                this.serviceFocus = object.getString("serviceFocus");
                this.street = object.getString("street");
                this.postcode = object.getString("postcode");
                this.suburb = object.getString("suburb");
                this.state = object.getString(ServerProtocol.DIALOG_PARAM_STATE);
                this.stateCode = object.getString("stateCode");
                this.website = object.getString("website");
                this.logoFileName = object.getString("logoFileName");
                return;

            }
            this.id = object.getInt("Id");
            this.name = object.getString("Name");
            this.description = object.getString("Description");
            this.serviceFocus = object.getString("ServiceFocusName");
            this.street = object.getString("Street");
            this.postcode = object.getString("Postcode");
            this.suburb = object.getString("SuburbName");
            this.state = object.getString("StateName");
            this.stateCode = object.getString("StateCode");
            this.website = object.getString("Website");
            this.logoFileName = object.getString("LogoFileName");
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
