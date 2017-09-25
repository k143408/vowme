package com.vowme.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vowme.model.Cause;
import com.vowme.model.Team;
import com.vowme.repository.CauseRepository;
import com.vowme.repository.TeamRepository;
import com.vowme.service.CauseService;
import com.vowme.util.helper.CauseShortDetail;
import com.vowme.util.helper.KeyValue;

@Service
public class CauseServiceImpl implements CauseService {

	@Autowired
	private CauseRepository causeRepository;

	@Autowired
	private TeamRepository teamRepository;

	@Override
	public Page<Cause> getCauses(Long userId, Pageable pageable) {
		return causeRepository.getCauseByUserId(userId, pageable);
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
		return causeRepository.getCauseNames(userId, pageable);
	}

	@Override
	public Cause getCausesById(Long causeId) {
		return causeRepository.findOne(causeId);
	}

	@Override
	public Page<CauseShortDetail> getCausesShortDescription(Long userId, Pageable pageable) {
		return causeRepository.getCauseShortDescriptionByUserId(userId, pageable);
	}

	@Transactional
	@Override
	public Cause addCauseWithTeam(Cause cause) {
		Set<Team> teams = cause.getTeams();
		cause.setTeams(new HashSet<>());
		causeRepository.save(cause);
		if (teams.size() != 0) {
			cause.addTeams(teams);
			teamRepository.save(teams);
		}
		return cause;
	}
	
	@Transactional
	@Override
	public Cause saveCause(Cause cause) {
		causeRepository.save(cause);
		return cause;
	}

	@Override
	public List<Cause> getAllCause() {
		return causeRepository.findAll();
	}

}
