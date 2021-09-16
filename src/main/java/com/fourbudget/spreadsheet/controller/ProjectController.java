package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.config.error.MySystemException;
import com.fourbudget.spreadsheet.model.Item;
import com.fourbudget.spreadsheet.model.Project;
import com.fourbudget.spreadsheet.model.dto.ProjectDTO;
import com.fourbudget.spreadsheet.repository.ProjectRepository;
import com.fourbudget.spreadsheet.service.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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

    @GetMapping("/{userId}")
    public ResponseEntity<Project> getProject(@PathVariable("userId") Long userId){

       Project project = this.projectService.getProject(userId);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> putProject(@PathVariable("id") Long id, @RequestBody ProjectDTO projectDTO) {

        Project project = projectService.updateProject(id, projectDTO);
        return ResponseEntity.status(HttpStatus.OK).body(project);
    }
}
