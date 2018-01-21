package com.vowme.dto;

public class LocationDTO {

	private Boolean IsCity = false;
	private String Name;

	public Boolean getIsCity() {
		return IsCity;
	}

	public LocationDTO(String name) {
		super();
		Name = name;
	}

	public void setIsCity(Boolean isCity) {
		IsCity = isCity;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

}
