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
import com.vowme.model.CauseSkill;
import com.vowme.model.Causetype;
import com.vowme.model.Shortlist;
import com.vowme.model.Skill;
import com.vowme.model.Team;
import com.vowme.model.User;
import com.vowme.repository.ApprovalRepository;
import com.vowme.repository.CauseRepository;
import com.vowme.repository.CauseSkillRepository;
import com.vowme.repository.CauseTypeRepository;
import com.vowme.repository.RecommendedRepository;
import com.vowme.repository.ShortListRepository;
import com.vowme.repository.SkillRepository;
import com.vowme.repository.TeamRepository;
import com.vowme.repository.UserRepository;
import com.vowme.service.CauseService;
import com.vowme.util.helper.CauseShortDetail;
import com.vowme.util.helper.KeyValue;

@Service
public class CauseServiceImpl implements CauseService {

	@Autowired
	private CauseRepository causeRepository;

	@Autowired
	private ShortListRepository shortListRepository;

	@Autowired
	private ApprovalRepository approvalRepository;

	@Autowired
	private RecommendedRepository recommendedRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private SkillRepository skillRepository;
	
	@Autowired
	private CauseSkillRepository causeSkillRepository ;
	
	@Autowired
	private CauseTypeRepository causeTypeRepository;  
	
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

	@Override
	public Cause addCauseWithTeam(Cause cause) {
		Set<Team> teams = cause.getTeams();
		Set<CauseSkill> causeSkills = cause.getCauseSkills();
		Set<Causetype> causetypes = cause.getCausetypes();
		cause.setTeams(new HashSet<>());
		final Cause causeSave = causeRepository.save(cause);
		if (teams != null && teams.size() != 0) {
			cause.addTeams(teams);
			teamRepository.save(teams);
		}
		if (causeSkills != null && causeSkills.size() != 0){
			causeSkills.forEach(cs -> {
				cs.setCause(causeSave);
				causeSkillRepository.saveAndFlush(cs);
			});
		}
		if (causetypes != null && causetypes.size() != 0){
			causetypes.forEach(ct -> {
				ct.setCause(causeSave);
				causeTypeRepository.save(ct);
			});
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

	@Override
	public Page<Cause> getShortListCause(Long userId, Pageable pageable) {
		return shortListRepository.getShortListCause(userId, pageable);
	}

	@Override
	public Page<Cause> getAccpectedCause(Long userId, Pageable pageable) {
		return approvalRepository.getApproval(userId, pageable);
	}

	@Override
	public Page<Cause> getRecommendedCause(Long userId, Pageable pageable) {
		return recommendedRepository.getRecommendedCause(userId, pageable);
	}

	@Override
	public User getEOI(Long userId, Long causeId) {
		return approvalRepository.getApprovalUserWithCause(userId, causeId);
	}

	@Override
	public Boolean addShortListCause(Long userId, Long causeId) {
		User user = userRepository.findById(userId);
		Cause cause = causeRepository.findOne(causeId);
		return shortListRepository.saveAndFlush(new Shortlist(cause, user)) != null ? true : false;
	}

	@Override
	public Boolean deleteShortListCause(Long userId, Long causeId) {
		shortListRepository.findshortListUserAndCause(userId, causeId).stream().forEach(s -> {
			shortListRepository.delete(s.getId());
		});
		return true;
	}

	@Override
	public Page<Cause> getExpressedinterestCause(Long userId, Pageable pageable) {
		return approvalRepository.getCauseExpressed(userId, pageable);
	}

	@Override
	public Page<Cause> getAllCause(Pageable pageable) {
		return causeRepository.findAll(pageable);
	}

}
