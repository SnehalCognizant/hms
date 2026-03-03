package com.travelease.stay.dto;

import jakarta.validation.constraints.*; 
import java.time.LocalDateTime; 
public class StayDtos { 
	public record ApiResponse<T>(boolean success,String message,T data,Object errors){} 
	public record CheckInRequest(@NotNull Long reservationId){} 
	public record StayResponse(Long stayId,Long reservationId,Long roomId,LocalDateTime checkInTime,LocalDateTime checkOutTime,String status,Double billAmount){} }