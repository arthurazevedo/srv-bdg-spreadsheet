package com.fourbudget.spreadsheet.repository;

import com.fourbudget.spreadsheet.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<List<Product>> findByUserId(String userId);

    void deleteAllByUserId(Long userId);
}
