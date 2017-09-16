package com.vowme.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vowme.model.Cause;
import com.vowme.repository.CauseRepository;
import com.vowme.service.CauseService;
import com.vowme.util.helper.KeyValue;

@Service
public class CauseServiceImpl implements CauseService {

	@Autowired
	private CauseRepository causeRepository;
	
	@Override
	public Page<Cause> getCauses(Long userId, Pageable pageable) {
		return causeRepository.getCauseByUserId(userId,pageable);
	}

	@Override
	public Long getCauseCount(Long userId) {
		return causeRepository.getCauseCount(userId);
	}

	@Override
	public Long getBackoutCauseCount(Long userId) {
		return causeRepository.getBackoutCauseCount(userId);
	}

	@Override
	public Long getParticipatesCauseCount(Long userId) {
		return causeRepository.getParticipatesCauseCount(userId);
	}

	@Override
	public Page<KeyValue> getCauseListOnlyName(Long userId, Pageable pageable) {
		return causeRepository.getCauseNames(userId,pageable);
	}

	@Override
	public Cause getCausesById(Long causeId) {
		return causeRepository.findOne(causeId);
	}

}
