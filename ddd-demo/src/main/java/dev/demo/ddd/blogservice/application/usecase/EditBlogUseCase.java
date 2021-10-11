package dev.demo.ddd.blogservice.application.usecase;

import dev.demo.ddd.blogservice.common.archrules.UseCase;
import dev.demo.ddd.blogservice.domain.contexts.blogcontext.Blog;
import dev.demo.ddd.blogservice.domain.contexts.blogcontext.BlogDomainService;
import dev.demo.ddd.blogservice.domain.contexts.blogcontext.BlogRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@UseCase
public class EditBlogUseCase {
    private final BlogDomainService blogDomainService;

    public EditBlogUseCase(BlogRepository blogRepository) {
        this.blogDomainService = new BlogDomainService(blogRepository);
    }

    @Transactional
    public Blog createDraft(String title, String body, UUID authorId) {
        return blogDomainService.createDraft(title, body, authorId);
    }

    @Transactional
    public Blog updateDraft(UUID id, String title, String body) {
        return blogDomainService.updateDraft(id, title, body);
    }

    @Transactional
    public Blog publishBlog(UUID id) {
        return blogDomainService.publish(id);
    }

    @Transactional
    public void deleteBlog(UUID id) {
        blogDomainService.deleteBlog(id);
    }
}
