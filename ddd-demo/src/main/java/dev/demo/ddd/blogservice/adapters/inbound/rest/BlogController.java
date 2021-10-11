package dev.demo.ddd.blogservice.adapters.inbound.rest;

import dev.demo.ddd.blogservice.adapters.inbound.rest.dto.DeleteBlogRequest;
import dev.demo.ddd.blogservice.adapters.inbound.rest.dto.PublishBlogRequest;
import dev.demo.ddd.blogservice.application.usecase.EditBlogUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/blog")
public class BlogController {

    private final EditBlogUseCase editBlogUseCase;

    public BlogController(EditBlogUseCase editBlogUseCase) {
        this.editBlogUseCase = editBlogUseCase;
    }

    @PostMapping("/publish")
    public void publishBlog(PublishBlogRequest request) {
        editBlogUseCase.publishBlog(UUID.fromString(request.getBlogId()));
    }

    @PostMapping("/delete")
    public void deleteBlog(DeleteBlogRequest request) {
        editBlogUseCase.deleteBlog(UUID.fromString(request.getBlogId()));
    }

}
