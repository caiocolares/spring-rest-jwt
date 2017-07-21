package com.opustech.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.opustech.model.dto.ValidationError;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)    
    public @ResponseBody ValidationError processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
 
        return processFieldErrors(fieldErrors);
    }

	private ValidationError processFieldErrors(List<FieldError> fieldErrors) {
		ValidationError dto = new ValidationError();
		 
        for (FieldError fieldError: fieldErrors) {
            dto.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }
 
        return dto;
	}
 
	
}
