package com.cts.identity.exception;

import com.cts.identity.dto.AuthDtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomExceptions.NotFoundException.class)
    ResponseEntity<ApiResponse<Object>> notFound(RuntimeException ex){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, ex.getMessage(), null, ex.getMessage())); }
    @ExceptionHandler({CustomExceptions.BadRequestException.class, IllegalArgumentException.class})
    ResponseEntity<ApiResponse<Object>> bad(RuntimeException ex){ return ResponseEntity.badRequest().body(new ApiResponse<>(false, ex.getMessage(), null, ex.getMessage())); }
    @ExceptionHandler(CustomExceptions.UnauthorizedException.class)
    ResponseEntity<ApiResponse<Object>> un(RuntimeException ex){ return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(false, ex.getMessage(), null, ex.getMessage())); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<Object>> valid(MethodArgumentNotValidException ex){
        String errors = ex.getBindingResult().getFieldErrors().stream().map(e->e.getField()+": "+e.getDefaultMessage()).collect(Collectors.joining(", "));
        return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Validation failed", null, errors));
    }
}