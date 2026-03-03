package com.cts.identity.exception;

public class CustomExceptions {
    public static class NotFoundException extends RuntimeException { public NotFoundException(String m){super(m);} }
    public static class UnauthorizedException extends RuntimeException { public UnauthorizedException(String m){super(m);} }
    public static class BadRequestException extends RuntimeException { public BadRequestException(String m){super(m);} }
}
