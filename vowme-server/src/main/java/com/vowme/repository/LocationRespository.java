package com.vowme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vowme.dto.LocationDTO;
import com.vowme.model.Location;


public interface LocationRespository extends JpaRepository<Location, Long>{

	@Query("Select new com.vowme.dto.LocationDTO(c.name) from Location c where UPPER(c.name) like %?1%")
	List<LocationDTO> findByPlaceContaining(String place);
	
}
