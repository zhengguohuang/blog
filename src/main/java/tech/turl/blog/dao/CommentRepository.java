package tech.turl.blog.dao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.turl.blog.po.Comment;

import java.util.List;

/**
 * @author zhengguohuang
 * @date 2021/04/02
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBlogIdAndParentCommentNull(Long id, Sort sort);

}
