package dev.demo.ddd.blogservice.domain.contexts.blogcontext;

import dev.demo.ddd.blogservice.common.archrules.DomainEntity;

import java.time.Instant;
import java.util.Objects;

@DomainEntity
public class PublishedBlog {
    private final String title;
    private final String body;
    private final Instant publishedAt;
    private final Instant updatedAt;

    public PublishedBlog(String title, String body, Instant publishedAt, Instant updatedAt) {
        this.title = title;
        this.body = body;
        this.publishedAt = publishedAt;
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

    public Instant getPublishedAt() {
        return this.publishedAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    // Value Object 需要实现 equals 和 hashCode 方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PublishedBlog that = (PublishedBlog) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(body, that.body) &&
                Objects.equals(publishedAt, that.publishedAt) &&
                Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, publishedAt, updatedAt);
    }
}
