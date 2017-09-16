package com.vowme.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;


/**
 * The Interface ChartService.
 */
public interface ChartService {

	/**
	 * Gets the report.
	 *
	 * @param causeId the cause id
	 * @param pageable the pageable
	 * @return the report
	 */
	Map<Integer, List<Integer>> getReport(Long causeId, Pageable pageable);

}
