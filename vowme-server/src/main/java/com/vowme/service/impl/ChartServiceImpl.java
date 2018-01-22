package com.vowme.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vowme.model.Cause;
import com.vowme.service.CauseService;
import com.vowme.service.ChartService;

@Service
public class ChartServiceImpl implements ChartService {

	@Autowired
	private CauseService causeService;

	@Override
	public Map<Integer, Long> getReport(Long causeId, Pageable pageable) {
		Map<Integer, Long> map = new HashMap<>();
		Cause causesById = causeService.getCausesById(causeId);
		if (causesById != null) {
			map.put(0, (long) ((causesById.getBoardcasts() == null ? 0 : causesById.getBoardcasts().size())));
			map.put(1,
					(causesById.getApprovals().stream().filter(s -> s.getIsApproved().equals(new Byte("1"))).count()));
			map.put(2,
					(causesById.getApprovals().stream().filter(s -> s.getIsApproved().equals(new Byte("2"))).count()));
		}

		return map;
	}

}
