package com.opustech.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationError implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private List<FieldError> fieldErrors = new ArrayList<>();

 
    public void addFieldError(String path, String message) {
        FieldError error = new FieldError(path, message);
        fieldErrors.add(error);
    }


	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
	
	
	public List<FieldError> getFieldErrors(){
		return this.fieldErrors;
	}

}
