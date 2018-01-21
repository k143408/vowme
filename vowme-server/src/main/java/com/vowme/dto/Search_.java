
package com.vowme.dto;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "AdvanceSearchFilters",
    "Durations",
    "Interests",
    "IsWidenLocation",
    "Keywords",
    "Locations",
    "WidenLocation"
})
public class Search_ implements Serializable
{

    @JsonProperty("AdvanceSearchFilters")
    private AdvanceSearchFilters advanceSearchFilters;
    @JsonProperty("Durations")
    private List<Object> durations = null;
    @JsonProperty("Interests")
    private List<Object> interests = null;
    @JsonProperty("IsWidenLocation")
    private Boolean isWidenLocation;
    @JsonProperty("Keywords")
    private String keywords;
    @JsonProperty("Locations")
    private List<String> locations = null;
    @JsonProperty("WidenLocation")
    private String widenLocation;
    private final static long serialVersionUID = -4317534054232375909L;

    @JsonProperty("AdvanceSearchFilters")
    public AdvanceSearchFilters getAdvanceSearchFilters() {
        return advanceSearchFilters;
    }

    @JsonProperty("AdvanceSearchFilters")
    public void setAdvanceSearchFilters(AdvanceSearchFilters advanceSearchFilters) {
        this.advanceSearchFilters = advanceSearchFilters;
    }

    @JsonProperty("Durations")
    public List<Object> getDurations() {
        return durations;
    }

    @JsonProperty("Durations")
    public void setDurations(List<Object> durations) {
        this.durations = durations;
    }

    @JsonProperty("Interests")
    public List<Object> getInterests() {
        return interests;
    }

    @JsonProperty("Interests")
    public void setInterests(List<Object> interests) {
        this.interests = interests;
    }

    @JsonProperty("IsWidenLocation")
    public Boolean getIsWidenLocation() {
        return isWidenLocation;
    }

    @JsonProperty("IsWidenLocation")
    public void setIsWidenLocation(Boolean isWidenLocation) {
        this.isWidenLocation = isWidenLocation;
    }

    @JsonProperty("Keywords")
    public String getKeywords() {
        return keywords;
    }

    @JsonProperty("Keywords")
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @JsonProperty("Locations")
    public List<String> getLocations() {
        return locations;
    }

    @JsonProperty("Locations")
    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    @JsonProperty("WidenLocation")
    public String getWidenLocation() {
        return widenLocation;
    }

    @JsonProperty("WidenLocation")
    public void setWidenLocation(String widenLocation) {
        this.widenLocation = widenLocation;
    }

}
