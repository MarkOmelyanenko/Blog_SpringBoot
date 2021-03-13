package com.myProject.blog.controllers;

import com.myProject.blog.models.Post;
import com.myProject.blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class BlogController {


    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")     // getting data from a page
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")        // getting data from a form
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String full_text, Model model) { //getting new parameters
        Post post = new Post(title, anons, full_text); // create an object based on the model
        postRepository.save(post); // access the repository and save the object
        return "redirect:/blog"; // redirect the user to the "blog" page
    }

    @GetMapping("/blog/{id}") // prescribing a dynamic value
    public String blogDetails(@PathVariable(value = "id") long id, Model model) { // take a dynamic value from the URL

        if (!postRepository.existsById(id)) { // false
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id); // access the repository and refer to the article by id
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);    // Optional to ArrayList
        model.addAttribute("post", res);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit") // prescribing a dynamic value
    public String blogEdit(@PathVariable(value = "id") long id, Model model) { // take a dynamic value from the URL

        if (!postRepository.existsById(id)) { // false
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(id); // access the repository and refer to the article by id
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);    // Optional to ArrayList
        model.addAttribute("post", res);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id,
                                 @RequestParam String title,
                                 @RequestParam String anons,
                                 @RequestParam String full_text, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setTitle(title);
        post.setAnons(anons);
        post.setFull_text(full_text);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/blog";
    }

}
