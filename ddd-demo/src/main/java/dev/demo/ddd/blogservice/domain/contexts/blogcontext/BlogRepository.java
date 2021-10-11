package dev.demo.ddd.blogservice.domain.contexts.blogcontext;


import dev.demo.ddd.blogservice.common.archrules.Persistence;

import java.util.Optional;
import java.util.UUID;

@Persistence
public interface BlogRepository {
    void save(Blog blog);

    Optional<Blog> findById(UUID id);

    boolean existsById(UUID id);

    void deleteById(UUID id);
}
