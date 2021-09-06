package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.error.MySystemException;
import com.fourbudget.spreadsheet.model.Product;
import com.fourbudget.spreadsheet.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final String ERROR_PRODUCTS_NOT_FOUND = "Products not found.";

    public List<Product> getProductsList(Long userId) {
        Optional<List<Product>> productsOptional =  this.productRepository.findByUserId(userId);

        List<Product> products = productsOptional.orElseThrow(() -> new MySystemException(HttpStatus.NO_CONTENT, ERROR_PRODUCTS_NOT_FOUND));

        if (products.isEmpty()) {
            throw new MySystemException(HttpStatus.NO_CONTENT, ERROR_PRODUCTS_NOT_FOUND);
        }

        return products;
    }
}
