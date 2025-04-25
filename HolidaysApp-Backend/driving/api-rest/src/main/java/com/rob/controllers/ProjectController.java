package com.rob.controllers;

import com.rob.domain.models.dtos.ProjectDTO;
import com.rob.domain.models.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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


}
