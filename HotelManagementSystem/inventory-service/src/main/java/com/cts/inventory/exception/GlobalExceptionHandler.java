package com.cts.inventory.exception;

import com.cts.inventory.dto.PropertyDtos.ApiResponse; 
import org.springframework.http.*; 
import org.springframework.web.bind.annotation.*; 
@RestControllerAdvice 
public class GlobalExceptionHandler { 
	@ExceptionHandler(Exception.class) 
	ResponseEntity<ApiResponse<Object>> h(Exception ex){ 
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false,ex.getMessage(),null,ex.getMessage()));}
	}