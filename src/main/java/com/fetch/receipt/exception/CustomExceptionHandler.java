package com.fetch.receipt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequestExceptions() {		
		return new ResponseEntity<Object>("The receipt is invalid.", HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundExceptions() {		
		return new ResponseEntity<Object>("No receipt found for that ID.", HttpStatus.NOT_FOUND);
	}
}