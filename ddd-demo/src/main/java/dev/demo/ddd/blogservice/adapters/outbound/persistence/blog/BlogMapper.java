package dev.demo.ddd.blogservice.adapters.outbound.persistence.blog;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Mapper
@Component
public interface BlogMapper {
    void insert(@Param("blog") BlogPO blog);

    Optional<BlogPO> findById(@Param("id") String id);

    void update(@Param("blog") BlogPO blogPO);

    boolean existsById(@Param("id") String id);

    void deleteById(@Param("id") String id);
}
