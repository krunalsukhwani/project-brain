package com.krunal.projectbrain.controller;

import com.krunal.projectbrain.form.IdeaForm;
import com.krunal.projectbrain.form.IdeaResponseForm;
import com.krunal.projectbrain.form.TodoForm;
import com.krunal.projectbrain.model.Idea;
import com.krunal.projectbrain.model.User;
import com.krunal.projectbrain.repository.IdeaRepository;
import com.krunal.projectbrain.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@RestController
public class IdeaController {
    private final IdeaRepository ideaRepository;
    private final UserRepository userRepository;

    @Autowired
    public IdeaController(IdeaRepository ideaRepository,UserRepository userRepository) {
        this.ideaRepository = ideaRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/ideas")
    public IdeaResponseForm findAll() {
        IdeaResponseForm responseForm = new IdeaResponseForm();
        try {
            responseForm.setData(new HashSet<Idea>(ideaRepository.findAll()));
        } catch (Exception e) {
            e.printStackTrace();
            responseForm.setData(new HashSet<Idea>());
        }
        return responseForm;
    }

    @GetMapping("/idea")
    public Idea findOne(@RequestParam Long id) {
        return ideaRepository.findIdeaById(id).orElseThrow();
    }

    @DeleteMapping("/idea/remove")
    public String removeOne(@RequestParam Long id) {
        Idea idea = ideaRepository.findById(id).orElseThrow();
        idea.getComposer().getIdeas().remove(idea);
        idea.setComposer(null);
        ideaRepository.save(idea);
        ideaRepository.deleteById(id);
        return "Successfully Deleted.";
    }

    @PostMapping("/ideas")
    public Idea save(@RequestBody IdeaForm ideaForm) {
        try{
            User user = userRepository.findUserByUsername(ideaForm.getUsername()).orElseThrow();
            Idea idea = new Idea();
            idea.setCiteIdeaId(ideaForm.getCiteIdeaId());
            idea.setTitle(ideaForm.getTitle());
            idea.setContext(ideaForm.getContext());
            idea.setContent(ideaForm.getContent());
            idea.setComposer(user);
            Idea respondeIdea = ideaRepository.save(idea);
            user.getIdeas().add(respondeIdea);
            userRepository.save(user);
            return respondeIdea;
        }catch (Exception e){
            e.printStackTrace();
            return new Idea();
        }
    }

    @PostMapping("/add/todo")
    public User addToDoToUser(@RequestBody TodoForm todoForm) {
        try {
            Idea idea = ideaRepository.findIdeaById(todoForm.getIdeaId()).orElseThrow();
            User user = userRepository.findUserByUsername(todoForm.getUsername()).orElseThrow();
            user.getTodo().add(idea);
            User responseUser = userRepository.save(user);
            return responseUser;
        } catch (Exception e) {
            e.printStackTrace();
            return new User();
        }
    }

    @GetMapping(value = "/idea/{title}/ideas")
    public IdeaResponseForm getIdeasByTitle(@PathVariable String title) {
        IdeaResponseForm responseForm = new IdeaResponseForm();
        try {
            Set<Idea> ideasByTitle = ideaRepository.findIdeaByTitleContainingIgnoreCase(title);

            if(ideasByTitle == null) {
                responseForm.setData(new HashSet<>());
            } else {
                responseForm.setData(ideasByTitle);
            }

        } catch (Exception e) {
            e.printStackTrace();
            responseForm.setData(new HashSet<Idea>());
        }
        return responseForm;
    }
}