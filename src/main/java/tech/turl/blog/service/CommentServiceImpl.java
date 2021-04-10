package tech.turl.blog.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.turl.blog.dao.CommentRepository;
import tech.turl.blog.po.Comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhengguohuang
 * @date 2021/04/02
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired private CommentRepository commentRepository;
    private List<Comment> tempReplys = new ArrayList<>();

    @Override
    public List<Comment> listCommentByBolgId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    /**
     * 循环每个顶级评论的评论节点
     *
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentView.add(c);
        }
        // 合并评论的各层子代到第一级子代集合中
        combineChildren(commentView);
        return commentView;
    }

    /** @param comments root根节点，blog不为空的对象集合 */
    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for (Comment reply1 : replys1) {
                // 循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            // 修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            // 清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    private void recursively(Comment comment) {
        tempReplys.add(comment);// 顶节点添加到临时存放集合
        if (comment.getReplyComments() != null && comment.getReplyComments().size() >0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if(reply.getReplyComments() != null && reply.getReplyComments().size() > 0){
                    recursively(reply);
                }
            }
        }
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentRepository.getOne(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }
}
