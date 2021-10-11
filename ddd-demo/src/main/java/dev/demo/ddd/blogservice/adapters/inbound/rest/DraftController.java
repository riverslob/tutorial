package dev.demo.ddd.blogservice.adapters.inbound.rest;

import dev.demo.ddd.blogservice.adapters.inbound.rest.dto.CreateDraftRequest;
import dev.demo.ddd.blogservice.adapters.inbound.rest.dto.DraftResponse;
import dev.demo.ddd.blogservice.adapters.inbound.rest.dto.GetDraftRequest;
import dev.demo.ddd.blogservice.adapters.inbound.rest.dto.SaveDraftRequest;
import dev.demo.ddd.blogservice.application.usecase.EditBlogUseCase;
import dev.demo.ddd.blogservice.application.usecase.QueryDraftUseCase;
import dev.demo.ddd.blogservice.domain.contexts.blogcontext.Blog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/draft")
public class DraftController {

    private final EditBlogUseCase editBlogUseCase;
    private final QueryDraftUseCase queryDraftUseCase;

    public DraftController(EditBlogUseCase editBlogUseCase, QueryDraftUseCase queryDraftUseCase) {
        this.editBlogUseCase = editBlogUseCase;
        this.queryDraftUseCase = queryDraftUseCase;
    }

    @PostMapping("/create")
    public void createDraft(CreateDraftRequest request) {
        if (request.getAuthorId() == null || request.getAuthorId().trim().isEmpty()) {
            throw new IllegalArgumentException("the blog must have author");
        }

        editBlogUseCase.createDraft(request.getTitle(), request.getBody(), UUID.fromString(request.getAuthorId()));
    }

    @GetMapping("/get")
    public DraftResponse getDraft(GetDraftRequest request) {
        Blog blog = queryDraftUseCase.getByBlogId(UUID.fromString(request.getBlogId()));
        return buildDraftDto(blog);
    }

    @PostMapping("/update")
    public DraftResponse updateDraft(SaveDraftRequest request) {
        Blog blog =
                editBlogUseCase.updateDraft(UUID.fromString(request.getBlogId()), request.getTitle(), request.getBody());
        return buildDraftDto(blog);
    }

    private DraftResponse buildDraftDto(Blog blog) {
        DraftResponse draftDto = new DraftResponse();
        draftDto.setBlogId(blog.getId().toString());
        draftDto.setTitle(blog.getDraft().getTitle());
        draftDto.setBody(blog.getDraft().getBody());
        draftDto.setAuthorId(blog.getAuthorId().toString());
        draftDto.setCreatedAt(blog.getCreatedAt().toString());
        draftDto.setSavedAt(blog.getDraft().getSavedAt().toString());
        return draftDto;
    }
}
