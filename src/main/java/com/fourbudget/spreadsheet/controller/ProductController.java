package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.Product;
import com.fourbudget.spreadsheet.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {

    ProductRepository productRepository;

    @GetMapping("/user/{userId}")
    public List<Product> getProducts(@PathVariable Long userId){
        return productRepository.findByUserId(userId);
    }
}
