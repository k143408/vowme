package com.vowme.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "eoi" })
public class EOI implements Serializable {

	@JsonProperty("eoi")
	private EoiDTO eoi;
	private final static long serialVersionUID = -6196263712132206233L;

	@JsonProperty("eoi")
	public EoiDTO getEoi() {
		return eoi;
	}

	@JsonProperty("eoi")
	public void setEoi(EoiDTO eoi) {
		this.eoi = eoi;
	}

}