package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.Project;
import com.fourbudget.spreadsheet.model.dto.ProjectDTO;
import com.fourbudget.spreadsheet.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/{userId}")
    public ResponseEntity<Project> postProject(@PathVariable("userId") String userId,
                                               @RequestBody @Valid ProjectDTO projectDTO) {
        Project project = this.projectService.createProject(userId, projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }
}
