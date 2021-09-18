package com.fourbudget.spreadsheet.repository;

import com.fourbudget.spreadsheet.model.Item;
import com.fourbudget.spreadsheet.model.Project;
import com.fourbudget.spreadsheet.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByUserId(Long userId);
}
