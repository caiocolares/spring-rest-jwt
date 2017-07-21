package com.opustech.model.dto;

import java.io.Serializable;

public class FieldError implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String field;
	 
    private String message;
 
    public FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }

	public String getField() {
		return field;
	}


	public String getMessage() {
		return message;
	}

}
