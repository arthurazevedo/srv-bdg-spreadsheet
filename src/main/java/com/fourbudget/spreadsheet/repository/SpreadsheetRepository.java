package com.fourbudget.spreadsheet.repository;

import com.fourbudget.spreadsheet.domain.Spreadsheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpreadsheetRepository extends JpaRepository<Spreadsheet, Long> {

}
