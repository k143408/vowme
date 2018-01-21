package com.vowme.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vowme.dto.Search_;
import com.vowme.model.Cause;

public interface SearchService {

	Page<Cause> getResult(Search_ search, Pageable pageable);

}
