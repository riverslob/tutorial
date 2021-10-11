package dev.demo.ddd.blogservice.domain.common;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
