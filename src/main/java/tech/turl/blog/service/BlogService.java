package tech.turl.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.turl.blog.po.Blog;
import tech.turl.blog.vo.BlogQuery;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);
    Blog getAndConvert(Long id);

    /**
     * 根据封装进Blog对象中的条件来查询
     * @param pageable
     * @param blog
     * @return
     */
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> listBlog(String query, Pageable pageable);
    Page<Blog> listBlog(Long tagId, Pageable pageable);
    List<Blog> listRecommendBlogTop(Integer size);
    Map<String, List<Blog>> archiveBlog();

    Long countBlog();

    /**
     * 新增
     * @param blog
     * @return
     */
    Blog saveBlog(Blog blog);

    /**
     * 先用id查询，用blog更新
     * @param id
     * @param blog
     * @return
     */
    Blog updateBlog(Long id, Blog blog);

    /**
     * 根据id删除
     * @param id
     */
    void deleteBlog(Long id);
}
