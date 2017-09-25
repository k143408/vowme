package com.vowme.app.models;

import com.vowme.app.models.api.PostApiModel;
import com.vowme.app.models.api.SearchOpportunitiesParameter;

import org.json.JSONException;
import org.json.JSONObject;

public class SavedSearchOpportunitiesItem extends PostApiModel {
    private boolean isFromRecents;
    private boolean isRadiusSearch;
    private String name;
    private SearchOpportunitiesParameter searchParameters = new SearchOpportunitiesParameter();

    public SavedSearchOpportunitiesItem() {
    }

    public SavedSearchOpportunitiesItem(JSONObject object) throws JSONException {
        try {
            this.isFromRecents = object.getBoolean("isFromRecents");
            this.name = object.getString("name");
            this.isRadiusSearch = object.getBoolean("isRadiusSearch");
            this.searchParameters = new SearchOpportunitiesParameter(object.getJSONObject("searchParameters"));
        } catch (JSONException e) {
            throw e;
        }
    }

    public boolean isFromRecents() {
        return this.isFromRecents;
    }

    public void setFromRecents(boolean fromRecents) {
        this.isFromRecents = fromRecents;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SearchOpportunitiesParameter getSearchParameters() {
        return this.searchParameters;
    }

    public void setSearchParameters(SearchOpportunitiesParameter searchParameters) {
        this.searchParameters = searchParameters;
    }

    public boolean isRadiusSearch() {
        return this.isRadiusSearch;
    }

    public void setRadiusSearch(boolean radiusSearch) {
        this.isRadiusSearch = radiusSearch;
    }
}
