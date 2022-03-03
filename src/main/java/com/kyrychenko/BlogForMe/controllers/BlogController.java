package com.kyrychenko.BlogForMe.controllers;

import com.kyrychenko.BlogForMe.models.Post;
import com.kyrychenko.BlogForMe.repos.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blog(Model model ) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model ) {
        return "blogAdd";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, @RequestParam String anons, @RequestParam String full, Model model) {
        Post post = new Post(title, anons, full);
        postRepository.save(post);
    return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails (@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> arrayList = new ArrayList<>();
        post.ifPresent(arrayList::add);
        model.addAttribute("post", arrayList);
        return "blogDetails";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> arrayList = new ArrayList<>();
        post.ifPresent(arrayList::add);
        model.addAttribute("post", arrayList);
        return "blogEdit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostEdit(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String anons, @RequestParam String full, Model model) {
       Post post = postRepository.findById(id).orElseThrow();
       post.setTitle(title);
       post.setAnons(anons);
       post.setFull(full);
       postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/delete")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }
}
