package com.fourbudget.spreadsheet.repository;

import com.fourbudget.spreadsheet.domain.SpreadsheetFromUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpreadsheetFromUserRepository extends JpaRepository<SpreadsheetFromUser, Long> {

    Optional<SpreadsheetFromUser> findByUserProfileId(Long id);


}
