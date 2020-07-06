package com.drifai.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MvcExceptionHandler {

	 @ExceptionHandler(ConstraintViolationException.class)
	    public ResponseEntity<List> validationErrorHandler(ConstraintViolationException ex){
	        List<String> errorsList = new ArrayList<>(ex.getConstraintViolations().size());

	        ex.getConstraintViolations().forEach(error -> errorsList.add(error.toString()));

	        return new ResponseEntity<>(errorsList, HttpStatus.BAD_REQUEST);
	    }
	
	@ExceptionHandler(BindException.class)
	public ResponseEntity handleBindException(BindException e) {
		return(new ResponseEntity(e.getAllErrors(), HttpStatus.BAD_REQUEST));
	}
}

