package com.fourbudget.spreadsheet.repository;

import com.fourbudget.spreadsheet.model.SpreadsheetFromUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpreadsheetFromUserRepository extends JpaRepository<SpreadsheetFromUser, Long> {

    Optional<SpreadsheetFromUser> findOneByUserProfileId(String id);


}
