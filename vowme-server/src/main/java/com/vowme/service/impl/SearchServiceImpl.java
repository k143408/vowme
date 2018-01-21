package com.vowme.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vowme.dto.Search_;
import com.vowme.model.Cause;
import com.vowme.repository.CauseRepository;
import com.vowme.service.SearchService;
import com.vowme.specification.CauseSpecifications;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private CauseRepository causeRepository;
	
	@Override
	public Page<Cause> getResult(Search_ search, Pageable pageable) {
		return causeRepository.findAll(CauseSpecifications.search(search), pageable);
	}

}
