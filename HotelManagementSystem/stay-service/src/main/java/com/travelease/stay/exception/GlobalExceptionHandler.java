package com.travelease.stay.exception;

import com.travelease.stay.dto.StayDtos.ApiResponse; 
import org.springframework.http.*; 
import org.springframework.web.bind.annotation.*; 
@RestControllerAdvice 
public class GlobalExceptionHandler 
{ 
	@ExceptionHandler(Exception.class) 
	public ResponseEntity<ApiResponse<Object>> h(Exception ex){ 
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false,ex.getMessage(),null,ex.getMessage())); 
		}}
