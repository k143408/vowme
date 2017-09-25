package com.vowme.app.models;

import com.google.gson.Gson;
import com.vowme.app.models.Enum.AutoCompleteCategorie;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchItem {
    private AutoCompleteCategorie categoryType;
    private int id;
    private boolean isCategory;
    private boolean isFromRecents;
    private String name;

    public SearchItem(AutoCompleteCategorie categoryType, String name, boolean isCategory) {
        this.categoryType = categoryType;
        this.name = name;
        this.isCategory = isCategory;
        this.isFromRecents = false;
    }

    public SearchItem(AutoCompleteCategorie categoryType, String name, int id, boolean isCategory) {
        this.categoryType = categoryType;
        this.name = name;
        this.id = id;
        this.isCategory = isCategory;
        this.isFromRecents = false;
    }

    public SearchItem(AutoCompleteCategorie categoryType, String name, int id, boolean isCategory, boolean isFromRecents) {
        this.categoryType = categoryType;
        this.name = name;
        this.id = id;
        this.isCategory = isCategory;
        this.isFromRecents = isFromRecents;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AutoCompleteCategorie getCategoryType() {
        return this.categoryType;
    }

    public void setCategoryType(AutoCompleteCategorie categoryType) {
        this.categoryType = categoryType;
    }

    public boolean isCategory() {
        return this.isCategory;
    }

    public void setIsCategory(boolean isCategory) {
        this.isCategory = isCategory;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFromRecents() {
        return this.isFromRecents;
    }

    public void setIsFromRecents(boolean isFromRecents) {
        this.isFromRecents = isFromRecents;
    }

    public JSONObject toJsonObject() {
        try {
            return new JSONObject("{\"searchAutoCompleteItem\":" + new Gson().toJson((Object) this) + "}");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
