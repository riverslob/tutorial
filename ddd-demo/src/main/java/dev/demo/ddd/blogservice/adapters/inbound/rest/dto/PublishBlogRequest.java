package dev.demo.ddd.blogservice.adapters.inbound.rest.dto;

public class PublishBlogRequest {
    public String blogId;

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
}
