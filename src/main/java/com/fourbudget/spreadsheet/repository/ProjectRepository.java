package com.fourbudget.spreadsheet.repository;

import com.fourbudget.spreadsheet.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
