package dev.demo.ddd.blogservice.domain.contexts.blogcontext;

import dev.demo.ddd.blogservice.common.archrules.DomainEntity;

import java.time.Instant;
import java.util.Objects;

@DomainEntity
public class Draft {
    private final String title;
    private final String body;
    private final Instant savedAt;

    public Draft(String title, String body, Instant savedAt) {
        this.title = title;
        this.body = body;
        this.savedAt = savedAt;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

    public Instant getSavedAt() {
        return this.savedAt;
    }

    // Value Object 需要实现 equals 和 hashCode 方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Draft draft = (Draft) o;
        return Objects.equals(title, draft.title) &&
                Objects.equals(body, draft.body) &&
                Objects.equals(savedAt, draft.savedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, body, savedAt);
    }
}
