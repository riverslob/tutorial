package dev.demo.ddd.blogservice.adapters.inbound.rest.dto;

import dev.demo.ddd.blogservice.common.archrules.RequestDto;
import dev.demo.ddd.blogservice.common.archrules.ResponseDto;

@ResponseDto
public class DraftResponse {
    private String blogId;
    private String title;
    private String body;
    private String authorId;
    private String createdAt;
    private String savedAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(String savedAt) {
        this.savedAt = savedAt;
    }
}
