package com.rob.controllers;

import com.rob.domain.models.dtos.ProjectDTO;
import com.rob.models.Project;
import com.rob.domain.models.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api-rest/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects(@RequestParam(name = "name",required = false) String name) {
        List<ProjectDTO> projects = new ArrayList<>();
        if (name != null) {
            projects = projectService.findProjectByName(name).get()
                    .stream()
                    .map(ProjectDTO::new)
                    .toList();
            if(!projects.isEmpty()) {
                return ResponseEntity.ok(projects);
            }
            return ResponseEntity.notFound().build();
        } else {
            projects = projectService.getAllProjects().get()
                    .stream()
                    .map(ProjectDTO::new)
                    .toList();
            if(!projects.isEmpty()) {
                return ResponseEntity.ok(projects);
            }
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable(name = "id",required = true) Long id) {
        Optional<Project> existingProject = projectService.findProjectById(id);
        if(existingProject.isPresent()) {
            return ResponseEntity.ok(new ProjectDTO(existingProject.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> saveProject(@RequestBody ProjectDTO project) {
        try {
            Optional<Project> savedProject = projectService.saveProject(project);
            if (savedProject.isPresent()) {
                System.out.println(savedProject.get());
                return ResponseEntity.ok(new ProjectDTO(savedProject.get()));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProjectDTO> deleteProjectById(@PathVariable(name = "id",required = true) Long id) {
        Optional<Project> deletedProject = projectService.deleteProjectById(id);
        if(deletedProject.isPresent()) {
            return ResponseEntity.ok(new ProjectDTO(deletedProject.get()));
        }
        return ResponseEntity.notFound().build();
    }
}
