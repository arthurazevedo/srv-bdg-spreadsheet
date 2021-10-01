package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.Product;
import com.fourbudget.spreadsheet.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/user/{userId}")
    public List<Product> getProducts(@PathVariable String userId) {
        return this.productService.getProductsList(userId);
    }
}
