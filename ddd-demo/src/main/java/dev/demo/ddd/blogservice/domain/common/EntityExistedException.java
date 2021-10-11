package dev.demo.ddd.blogservice.domain.common;


import java.util.UUID;

public class EntityExistedException extends DomainException {
    public EntityExistedException(String message) {
        super(message);
    }

    public EntityExistedException(Class<?> entityClass, UUID id) {
        super("the " + entityClass.getSimpleName().toLowerCase() + " with id " + id + " was existed");
    }
}
