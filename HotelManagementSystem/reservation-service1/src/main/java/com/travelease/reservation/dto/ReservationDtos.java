package com.travelease.reservation.dto;

import jakarta.validation.constraints.*; 
import java.time.LocalDate;
public class ReservationDtos { 
	public record ApiResponse<T>(boolean success,String message,T data,Object errors){} 
	public record ReservationRequest(@NotNull Long guestId,@NotNull Long propertyId,@NotNull Long roomTypeId,@NotNull LocalDate checkInDate,@NotNull LocalDate checkOutDate,@NotNull Integer guestsCount){} 
	public record ReservationUpdateRequest(@NotNull LocalDate checkInDate,@NotNull LocalDate checkOutDate,@NotNull Integer guestsCount){} public record ReservationResponse(Long reservationId,Long guestId,Long propertyId,Long roomTypeId,LocalDate checkInDate,LocalDate checkOutDate,Integer guestsCount){} 
	public record RoomTypeDTO(Long roomTypeId,String name,Integer capacity,Double baseRate,Integer availableCount){} }