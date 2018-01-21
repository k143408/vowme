
package com.vowme.dto;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "AvailableDay",
    "AvailableEvening",
    "AvailableWeekday",
    "AvailableWeekend",
    "DoBlackList",
    "EventId",
    "EventVolunteering",
    "IncludeCorporateOrganisationOpportunities",
    "IsAccessible",
    "OrganisationId",
    "Organisations",
    "Suitabilities",
    "Types",
    "VrcOrganisation"
})
public class AdvanceSearchFilters implements Serializable
{

    @JsonProperty("AvailableDay")
    private String availableDay;
    @JsonProperty("AvailableEvening")
    private String availableEvening;
    @JsonProperty("AvailableWeekday")
    private String availableWeekday;
    @JsonProperty("AvailableWeekend")
    private String availableWeekend;
    @JsonProperty("DoBlackList")
    private Boolean doBlackList;
    @JsonProperty("EventId")
    private String eventId;
    @JsonProperty("EventVolunteering")
    private String eventVolunteering;
    @JsonProperty("IncludeCorporateOrganisationOpportunities")
    private String includeCorporateOrganisationOpportunities;
    @JsonProperty("IsAccessible")
    private String isAccessible;
    @JsonProperty("OrganisationId")
    private String organisationId;
    @JsonProperty("Organisations")
    private List<Object> organisations = null;
    @JsonProperty("Suitabilities")
    private List<Object> suitabilities = null;
    @JsonProperty("Types")
    private List<Object> types = null;
    @JsonProperty("VrcOrganisation")
    private Boolean vrcOrganisation;
    private final static long serialVersionUID = 463722649749833567L;

    @JsonProperty("AvailableDay")
    public String getAvailableDay() {
        return availableDay;
    }

    @JsonProperty("AvailableDay")
    public void setAvailableDay(String availableDay) {
        this.availableDay = availableDay;
    }

    @JsonProperty("AvailableEvening")
    public String getAvailableEvening() {
        return availableEvening;
    }

    @JsonProperty("AvailableEvening")
    public void setAvailableEvening(String availableEvening) {
        this.availableEvening = availableEvening;
    }

    @JsonProperty("AvailableWeekday")
    public String getAvailableWeekday() {
        return availableWeekday;
    }

    @JsonProperty("AvailableWeekday")
    public void setAvailableWeekday(String availableWeekday) {
        this.availableWeekday = availableWeekday;
    }

    @JsonProperty("AvailableWeekend")
    public String getAvailableWeekend() {
        return availableWeekend;
    }

    @JsonProperty("AvailableWeekend")
    public void setAvailableWeekend(String availableWeekend) {
        this.availableWeekend = availableWeekend;
    }

    @JsonProperty("DoBlackList")
    public Boolean getDoBlackList() {
        return doBlackList;
    }

    @JsonProperty("DoBlackList")
    public void setDoBlackList(Boolean doBlackList) {
        this.doBlackList = doBlackList;
    }

    @JsonProperty("EventId")
    public String getEventId() {
        return eventId;
    }

    @JsonProperty("EventId")
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @JsonProperty("EventVolunteering")
    public String getEventVolunteering() {
        return eventVolunteering;
    }

    @JsonProperty("EventVolunteering")
    public void setEventVolunteering(String eventVolunteering) {
        this.eventVolunteering = eventVolunteering;
    }

    @JsonProperty("IncludeCorporateOrganisationOpportunities")
    public String getIncludeCorporateOrganisationOpportunities() {
        return includeCorporateOrganisationOpportunities;
    }

    @JsonProperty("IncludeCorporateOrganisationOpportunities")
    public void setIncludeCorporateOrganisationOpportunities(String includeCorporateOrganisationOpportunities) {
        this.includeCorporateOrganisationOpportunities = includeCorporateOrganisationOpportunities;
    }

    @JsonProperty("IsAccessible")
    public String getIsAccessible() {
        return isAccessible;
    }

    @JsonProperty("IsAccessible")
    public void setIsAccessible(String isAccessible) {
        this.isAccessible = isAccessible;
    }

    @JsonProperty("OrganisationId")
    public String getOrganisationId() {
        return organisationId;
    }

    @JsonProperty("OrganisationId")
    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    @JsonProperty("Organisations")
    public List<Object> getOrganisations() {
        return organisations;
    }

    @JsonProperty("Organisations")
    public void setOrganisations(List<Object> organisations) {
        this.organisations = organisations;
    }

    @JsonProperty("Suitabilities")
    public List<Object> getSuitabilities() {
        return suitabilities;
    }

    @JsonProperty("Suitabilities")
    public void setSuitabilities(List<Object> suitabilities) {
        this.suitabilities = suitabilities;
    }

    @JsonProperty("Types")
    public List<Object> getTypes() {
        return types;
    }

    @JsonProperty("Types")
    public void setTypes(List<Object> types) {
        this.types = types;
    }

    @JsonProperty("VrcOrganisation")
    public Boolean getVrcOrganisation() {
        return vrcOrganisation;
    }

    @JsonProperty("VrcOrganisation")
    public void setVrcOrganisation(Boolean vrcOrganisation) {
        this.vrcOrganisation = vrcOrganisation;
    }

}
