package com.spring.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/start")
public class DemoController {

	@GetMapping("/hello")
	public String helloworld() {
		return "Hello World";
	}
	
	// Add @ControllerAdvice annotation to a class, so that the exception handler is copied to that class
	// To handle responses we use @ExceptionHandler annotation
	//public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException ex){
	//StudentErrorResponse error = new StudentErrorResponse();
	// return ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}
