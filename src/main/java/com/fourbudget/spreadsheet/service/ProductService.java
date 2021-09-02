package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.model.Product;
import com.fourbudget.spreadsheet.model.Servico;
import com.fourbudget.spreadsheet.repository.ProductRepository;
import com.fourbudget.spreadsheet.repository.ServicoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ServicoRepository servicoRepository;


    public List<Product> getProductsList(Long userId) {
        return this.productRepository.findByUserId(userId);
    }

    public List<Servico> getServicosList(Long userId) {
        return this.servicoRepository.findByUserId(userId);
    }
}
