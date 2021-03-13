package com.myProject.blog.controllers;

import com.myProject.blog.models.Post;
import com.myProject.blog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private PostRepository postRepo;

    @GetMapping("/") // ("/") - home page proccesing
    public String blogHome(Model model) { // call the function, Model model is required!!!
        Iterable<Post> homePosts = postRepo.findAll();
        model.addAttribute("homePosts", homePosts); // passing parameters
        return "home"; // return a template
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About us");
        return "about";
    }

}
