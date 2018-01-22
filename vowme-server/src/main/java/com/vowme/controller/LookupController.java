package com.vowme.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vowme.dto.LocationDTO;
import com.vowme.model.Lookup;
import com.vowme.model.Skill;
import com.vowme.repository.SkillRepository;
import com.vowme.service.LookupService;
import com.vowme.util.AppConstants;

@RestController
@RequestMapping("api/lookup")
public class LookupController extends BaseController {

	@Autowired
	private LookupService lookupService;

	@Autowired
	private SkillRepository skillRepository;
	
	@GetMapping("location")
	public List<LocationDTO> get(@RequestParam(required = false, name = "name") String location) {
		return location == null ? lookupService.getLocations() : lookupService.getLocation(location);
	}

	@GetMapping("causes")
	public List<Lookup> getCauses() {
		return lookupService.getLookup(AppConstants.CAUSE);
	}

	@GetMapping("durations")
	public List<Lookup> getDurations() {
		return lookupService.getLookup(AppConstants.DURATION);
	}
	
	@GetMapping("interests")
	public List<Lookup> getInterests() {
		return lookupService.getLookup(AppConstants.INTEREST);
	}
	
	@GetMapping("skills")
	public List<Skill> getskills() {
		return skillRepository.findAll();
	}

	@GetMapping("programs/explanation")
	public List<Lookup> getExplanation() {
		return lookupService.getLookup(AppConstants.EXPLANATION);
	}

	@GetMapping("requirements")
	public List<Lookup> getRequirements() {
		return lookupService.getLookup(AppConstants.REQUIREMENTS);
	}

	@GetMapping("languages")
	public List<Lookup> getLanguages() {
		return lookupService.getLookup(AppConstants.LANGUAGUES);
	}

	@GetMapping("transports")
	public List<Lookup> getTransports() {
		return lookupService.getLookup(AppConstants.TRANSPORTS);
	}

	@GetMapping("typesOfCar")
	public List<Lookup> getTypesOfCar() {
		return lookupService.getLookup(AppConstants.TYPEOFCARS);
	}
	
	@GetMapping("tShirtSizes")
	public List<Lookup> getTShirtSizes() {
		return lookupService.getLookup(AppConstants.TSHIRTSIZE);
	}
	
	@GetMapping("titles")
	public List<Lookup> getTitles() {
		return lookupService.getLookup(AppConstants.TSHIRTSIZE);
	}

	@GetMapping("classifications")
	public List<Lookup> getClassifications() {
		return lookupService.getLookup(AppConstants.PROSKILLS);
	}
	
	@GetMapping("experiences")
	public List<Lookup> getExperiences() {
		return lookupService.getLookup(AppConstants.EXPERIENCES);
	}
	
	@GetMapping("subClassifications")
	public List<Lookup> getSubClassifications() {
		return lookupService.getLookup(AppConstants.SUBPROSKILLS);
	}
	
	@GetMapping("notificationFrequencies")
	public List<Lookup> getNotificationFrequencies() {
		return lookupService.getLookup(AppConstants.NOTIFICATIONFREQUENCIES);
	}
	
	@GetMapping("locations")
	public List<Lookup> getLocations() {
		return lookupService.getLookup(AppConstants.LOCATIONS);
	}
	
	@GetMapping("avaibilitiyFilter")
	public List<Lookup> getAvaibilitiyFilter() {
		return lookupService.getLookup(AppConstants.AVAIBILITYFILTER);
	}
	
	@GetMapping("avaibilitiy")
	public List<Lookup> getAvaibilitiy() {
		return lookupService.getLookup(AppConstants.AVAIBILITY);
	}
}
