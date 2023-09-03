package com.example.targetapplication.Controller;

import com.example.targetapplication.Entity.Post;
import com.example.targetapplication.Service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Data
    class PostForm {
        String title;
        String writer;
        String content;
    }

    @GetMapping
    public String posts(Model model) {
        List<Post> posts = postService.posts();
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/new")
    public String postForm(PostForm postForm) {
        return "postForm";
    }

    @PostMapping("/new")
    public String create(PostForm postForm) {
        postService.createPost(postForm.title, postForm.writer, postForm.content);
        return "redirect:/posts";
    }

    @GetMapping("/{postId}")
    public String showPost(@PathVariable String postId, Model model) {
        Post post = postService.find(postId);

        model.addAttribute("post", post);
        return "post";
    }
}
