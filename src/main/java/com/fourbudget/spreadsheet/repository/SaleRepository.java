package com.fourbudget.spreadsheet.repository;

import com.fourbudget.spreadsheet.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    void deleteAllByUserId(Long userId);
}
