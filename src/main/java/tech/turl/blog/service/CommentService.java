package tech.turl.blog.service;

import tech.turl.blog.po.Comment;

import java.util.List;

/**
 * @author zhengguohuang
 * @date 2021/04/02
 */
public interface CommentService {
    List<Comment> listCommentByBolgId(Long blogId);
    Comment saveComment(Comment comment);
}
