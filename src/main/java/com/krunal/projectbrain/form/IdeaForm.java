package com.krunal.projectbrain.form;

import lombok.Data;
import java.util.Set;

import com.krunal.projectbrain.model.Idea;

@Data
public class IdeaForm {
    private String id;
    private String title;
    private String context;
    private String content;
    private String username;
    private Set<Idea> data;
}