
package com.vowme.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "search"
})
public class Search implements Serializable
{

    @JsonProperty("search")
    private Search_ search;
    private final static long serialVersionUID = -8458998128389868692L;

    @JsonProperty("search")
    public Search_ getSearch() {
        return search;
    }

    @JsonProperty("search")
    public void setSearch(Search_ search) {
        this.search = search;
    }

}
