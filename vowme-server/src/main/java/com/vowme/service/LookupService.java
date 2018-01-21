package com.vowme.service;

import java.util.List;

import com.vowme.dto.LocationDTO;
import com.vowme.model.Lookup;

public interface LookupService {
	
	
	List<LocationDTO> getLocation(String location);
	
	List<Lookup> getLookup(String type);

	List<LocationDTO> getLocations();

}
