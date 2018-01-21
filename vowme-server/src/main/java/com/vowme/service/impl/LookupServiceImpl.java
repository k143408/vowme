package com.vowme.service.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vowme.dto.LocationDTO;
import com.vowme.model.Lookup;
import com.vowme.repository.LocationRespository;
import com.vowme.repository.LookupRespository;
import com.vowme.service.LookupService;

@Service
public class LookupServiceImpl implements LookupService {

	@Autowired
	private LocationRespository locationRespository;
	
	@Autowired
	private LookupRespository lookupRespository;
	
	
	@Override
	public List<LocationDTO> getLocation(String location) {
		return locationRespository.findByPlaceContaining(location.toUpperCase());
	}


	@Override
	public List<Lookup> getLookup(String type) {
		return lookupRespository.findByType(type.toUpperCase());
	}
	
	@Override
	public List<LocationDTO> getLocations() {
		return locationRespository.findAll()
					.parallelStream().map(l -> new LocationDTO(l.getName()))
					.collect(Collectors.toList());
	}

}
