package com.krunal.projectbrain.repository;
import com.krunal.projectbrain.model.Idea;
import java.util.Optional;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {
    Optional<Idea> findIdeaById(Long id); 
    Set<Idea> findIdeaByTitleContainingIgnoreCase(String title);
}