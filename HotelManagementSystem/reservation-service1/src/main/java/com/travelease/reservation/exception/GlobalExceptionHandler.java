package com.travelease.reservation.exception;

import com.travelease.reservation.dto.ReservationDtos.ApiResponse; 
import org.springframework.http.*; 
import org.springframework.web.bind.annotation.*; 
@RestControllerAdvice 
public class GlobalExceptionHandler { 
	@ExceptionHandler(Exception.class) ResponseEntity<ApiResponse<Object>> h(Exception ex)
	{ 
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(false,ex.getMessage(),null,ex.getMessage())); 
		}}
