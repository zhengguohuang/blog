package tech.turl.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import tech.turl.blog.service.BlogService;

import java.util.Map;

/**
 * @author zhengguohuang
 * @date 2021/04/02
 */
@Controller
public class ArchiveShowController {
    @Autowired private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }
}
