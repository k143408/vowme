package com.vowme.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vowme.dto.Search;
import com.vowme.model.Cause;
import com.vowme.service.SearchService;

/**
 * The Class ChartController.
 */
@RestController
@RequestMapping("api/search")
public class SearchController extends BaseController {

	/** The chart service. */
	@Autowired
	private SearchService searchService;

	private ObjectMapper mapper = new ObjectMapper();
	/**
	 * Gets the status.
	 *
	 * @param causeId
	 *            the cause id
	 * @param pageable
	 *            the pageable
	 * @return the status
	 */
	@PostMapping
	public Page<Cause> getSearch(@RequestBody Search search , @PageableDefault(size = 4) Pageable pageable) {
		return searchService.getResult(search.getSearch(), pageable);
	}
}
