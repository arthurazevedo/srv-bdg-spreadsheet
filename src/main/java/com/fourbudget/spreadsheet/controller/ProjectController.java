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
    public ResponseEntity<Project> postProject(@PathVariable("userId") Long userId,
                                               @RequestBody @Valid ProjectDTO projectDTO) {
        Project project = this.projectService.createProject(userId, projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProject(@PathVariable("projectId") Long projectId) {
        Project project = this.projectService.getProject(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Project> putProject(@PathVariable("projectId") Long projectId,
                                              @RequestBody ProjectDTO projectDTO) {
        Project project = projectService.updateProject(projectId, projectDTO);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Project> finishProject(@PathVariable("projectId") Long projectId) {
        Project project = projectService.finishProject(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

}
