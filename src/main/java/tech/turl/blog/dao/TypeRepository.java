package tech.turl.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.turl.blog.po.Type;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
