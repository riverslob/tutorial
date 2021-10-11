package dev.demo.ddd.blogservice.application.usecase;

import dev.demo.ddd.blogservice.common.archrules.UseCase;
import dev.demo.ddd.blogservice.domain.contexts.blogcontext.Blog;
import dev.demo.ddd.blogservice.domain.contexts.blogcontext.BlogDomainService;
import dev.demo.ddd.blogservice.domain.contexts.blogcontext.BlogRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@UseCase
public class QueryDraftUseCase {
    private final BlogDomainService blogDomainService;

    public QueryDraftUseCase(BlogRepository blogRepository) {
        this.blogDomainService = new BlogDomainService(blogRepository);
    }

    public Blog getByBlogId(UUID id) {
        return blogDomainService.getBlog(id);
    }
}
