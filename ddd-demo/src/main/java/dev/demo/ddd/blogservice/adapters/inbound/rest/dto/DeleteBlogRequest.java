package dev.demo.ddd.blogservice.adapters.inbound.rest.dto;

public class DeleteBlogRequest {
    private String blogId;

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
}
