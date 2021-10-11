package dev.demo.ddd.blogservice.domain.contexts.blogcontext.exceptions;


import dev.demo.ddd.blogservice.domain.common.EntityExistedException;

public class NoNeedToPublishException extends EntityExistedException {
    public NoNeedToPublishException() {
        super("the blog has not changed, no need to publish.");
    }
}
