package com.vowme.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vowme.service.ChartService;


/**
 * The Class ChartController.
 */
@RestController
@RequestMapping("api/chart/")
public class ChartController extends BaseController {

	/** The chart service. */
	@Autowired
	private ChartService chartService;

	/**
	 * Gets the status.
	 *
	 * @param causeId the cause id
	 * @param pageable the pageable
	 * @return the status
	 */
	@GetMapping("status/{causeId}")
	public Callable<Map<Integer, List<Integer>>> getStatus(@PathVariable Long causeId,@PageableDefault(size = 1)Pageable pageable) {
		return new Callable<Map<Integer, List<Integer>>>() {
			@Override
			public Map<Integer, List<Integer>> call() throws Exception {
				return chartService.getReport(causeId,pageable);
			}
		};
	}
}
