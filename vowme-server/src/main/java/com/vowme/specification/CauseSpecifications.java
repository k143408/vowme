package com.vowme.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.vowme.dto.Search_;
import com.vowme.model.Cause;

public class CauseSpecifications {

	public static Specification<Cause> search(Search_ search) {
		return (root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			
			for (String location : search.getLocations()) {
				if (location.contains(",")) {
					String[] split = location.split(",");
					Double lessThan = 100.00d; 
					javax.persistence.criteria.Expression<Double> latitude = cb.literal(new Double(split[0]));
					javax.persistence.criteria.Expression<Double> longtiude = cb.literal(new Double(split[1]));
					
					predicate.add(cb.lessThan(cb.function("haversine", Double.class, root.get("latitude"), root.get("longitude"),latitude,longtiude), lessThan));
					// predicate.add(cb.lessThan(path, lessThan));
				} else {
					predicate.add((cb.like(cb.lower(root.get("city")), location.toLowerCase())));
				}
			}
			Predicate[] predicateArray = predicate.toArray(new Predicate[predicate.size()]);
			return cb.or(predicateArray);
		};
	}

}
