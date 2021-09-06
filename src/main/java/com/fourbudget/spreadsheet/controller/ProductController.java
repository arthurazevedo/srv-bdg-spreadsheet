package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.Product;
import com.fourbudget.spreadsheet.model.Services;
import com.fourbudget.spreadsheet.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/user/{userId}")
    public List<Product> getProducts(@PathVariable Long userId) {
        return this.productService.getProductsList(userId);
    }
}
