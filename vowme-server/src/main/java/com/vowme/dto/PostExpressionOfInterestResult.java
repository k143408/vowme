package com.vowme.dto;

public class PostExpressionOfInterestResult {

	private String message;
	private boolean flag = false;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PostExpressionOfInterestResult(Boolean flag,String message) {
		super();
		this.message = message;
		this.flag = flag;
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public PostExpressionOfInterestResult(String message) {
		super();
		this.message = message;
	}
	
	
	
}
