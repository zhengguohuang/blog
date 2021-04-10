package tech.turl.blog.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tech.turl.blog.po.Tag;
import tech.turl.blog.service.TagService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired private TagService tagService;

    @GetMapping("tags")
    public String tags(
            @PageableDefault(
                            size = 10,
                            sort = {"id"},
                            direction = Sort.Direction.DESC)
                    Pageable pageable,
            Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("tags/input")
    public String input(Model model) {
        Tag tag = new Tag();
        model.addAttribute("tag", tag);

        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        if (null != tagService.getTagByName(tag.getName())) {
            result.rejectValue("name", "nameError", "不能重复添加分类");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            // 保存失败
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            // 成功
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(
            @Valid Tag tag,
            BindingResult result,
            @PathVariable Long id,
            RedirectAttributes attributes) {
        if (null != tagService.getTagByName(tag.getName())) {
            result.rejectValue("name", "nameError", "不能重复添加分类");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            // 更新失败
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            // 成功
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id) {
        tagService.deleteTag(id);
        return "redirect:/admin/tags";
    }
}
