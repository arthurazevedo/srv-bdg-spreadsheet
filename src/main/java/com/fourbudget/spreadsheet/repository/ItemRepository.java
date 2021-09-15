package com.fourbudget.spreadsheet.repository;

import com.fourbudget.spreadsheet.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
