package com.cms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	// Handle all exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage("Internal Server Error");
		response.setDetails(ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); //(500)
	}

	// Handle specific exception (CourseInvalidException)
	@ExceptionHandler(CourseInvalidException.class)
	public ResponseEntity<ExceptionResponse> handleNotFoundException(RuntimeException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage("Not Found");
		response.setDetails(ex.getMessage());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //(404)
	}


}








//package com.cms.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestControllerAdvice
//public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
//
//	// Handle all exceptions
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex) {
//		ExceptionResponse response = new ExceptionResponse();
//		response.setMessage("Internal Server Error");
//		response.setDetails(ex.getMessage());
//		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); //(500)
//	}
//
//	// Handle specific exception (CourseInvalidException, AdmissionInvalidException, AssociateInvalidException)
//	@ExceptionHandler({CourseInvalidException.class, AdmissionInvalidException.class, AssociateInvalidException.class})
//	public ResponseEntity<ExceptionResponse> handleNotFoundException(RuntimeException ex) {
//		ExceptionResponse response = new ExceptionResponse();
//		response.setMessage("Not Found");
//		response.setDetails(ex.getMessage());
//		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //(404)
//	}
//
//	// Handle exception method argument not valid
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
//		BindingResult bindingResult = ex.getBindingResult();
//		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//
//		ExceptionResponse response = new ExceptionResponse();
//		response.setMessage("Validation Error");
//
//		List<String> errors = new ArrayList<>();
//		for (FieldError fieldError : fieldErrors) {
//			String error = fieldError.getField() + ": " + fieldError.getDefaultMessage();
//			errors.add(error);
//		}
//
//		response.setDetails(String.valueOf((errors)));
//		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //(400)
//	}
//
//}
//
//
