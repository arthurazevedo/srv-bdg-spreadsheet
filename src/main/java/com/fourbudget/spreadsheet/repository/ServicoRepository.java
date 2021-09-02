package com.fourbudget.spreadsheet.repository;

import com.fourbudget.spreadsheet.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

    List<Servico> findByUserId(Long userId);
}
