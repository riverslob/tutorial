package dev.demo.ddd.blogservice.domain.common;


import java.util.UUID;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(Class<?> entityClass, UUID id) {
        super("cannot find the " + entityClass.getSimpleName().toLowerCase() + " with id " + id);
    }
}
