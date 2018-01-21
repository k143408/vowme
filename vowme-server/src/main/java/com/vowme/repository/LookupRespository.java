package com.vowme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vowme.model.Lookup;

public interface LookupRespository extends JpaRepository<Lookup, Long> {

	@Query("Select c from Lookup c where UPPER(c.type) like %?1%")
	List<Lookup> findByType(String type);

}
