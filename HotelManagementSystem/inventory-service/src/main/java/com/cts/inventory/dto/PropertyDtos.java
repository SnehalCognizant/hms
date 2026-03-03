package com.cts.inventory.dto;

import jakarta.validation.constraints.*; 
import java.util.*; public class PropertyDtos 
{ 
	public record ApiResponse<T>(boolean success,String message,T data,Object errors){} 
	public record PropertyRequest(@NotBlank String name,String location,@DecimalMin("0.0") @DecimalMax("5.0") Double rating){} 
	public record PropertyResponse(Long propertyId,String name,String location,Double rating){} 
	public record RoomTypeRequest(@NotNull Long propertyId,@NotNull Integer capacity,@NotNull Double baseRate){} 
	public record RoomTypeResponse(Long roomTypeId,Long propertyId,Integer capacity,Double baseRate){} 
	public record RoomRequest(@NotNull Long roomTypeId,@NotBlank String roomNumber,Integer floor){} 
	public record RoomResponse(Long roomId,Long roomTypeId,String roomNumber,Integer floor ){} 
}
