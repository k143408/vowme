package com.vowme.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class BaseDTO.
 */
public class BaseDTO implements Serializable {

	private List<String> errors = new ArrayList<>();

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
	
}
