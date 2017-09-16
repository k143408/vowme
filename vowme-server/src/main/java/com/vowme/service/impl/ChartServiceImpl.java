package com.vowme.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vowme.service.ChartService;

@Service
public class ChartServiceImpl implements ChartService {

	@Override
	public Map<Integer, List<Integer>> getReport(Long causeId,Pageable pageable) {
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		for (int count = 0; count < 3; count++) {
			List<Integer> arrayList = new ArrayList<>();
			for (int value = 0; value < 10; value++) {
				arrayList.add(ThreadLocalRandom.current().nextInt(10, 1000 / (count + 2)));
			}
			map.put(count, arrayList);
		}
		return map;
	}

}
