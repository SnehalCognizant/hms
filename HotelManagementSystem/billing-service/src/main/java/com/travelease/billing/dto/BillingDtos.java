package com.travelease.billing.dto;

import jakarta.validation.constraints.*; 
import java.time.LocalDateTime; 
public class BillingDtos 
{ 
	public record ApiResponse<T>(boolean success,String message,T data,Object errors){} 
	public record BillCreateRequest(@NotNull Long stayId,@NotNull Double amount){} 
	public record BillResponse(Long billId,Long stayId,Double amount,LocalDateTime generatedDate,String status){} 
	
}