package com.krunal.projectbrain.form;

import java.util.Set;

import com.krunal.projectbrain.model.Idea;

import lombok.Data;

@Data
public class IdeaResponseForm {
    private Set<Idea> data;
}