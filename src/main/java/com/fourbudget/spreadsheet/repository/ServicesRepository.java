package com.fourbudget.spreadsheet.repository;

import com.fourbudget.spreadsheet.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServicesRepository extends JpaRepository<Services, Long> {

    Optional<List<Services>> findByUserId(Long userId);
}
