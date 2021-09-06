package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.Product;
import com.fourbudget.spreadsheet.model.Servico;
import com.fourbudget.spreadsheet.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/{userId}")
    public List<Product> getProducts(@PathVariable Long userId) {
        return this.productService.getProductsList(userId);
    }

    @GetMapping("/services/{userId}")
    public List<Servico> getServices(@PathVariable Long userId) {
        return this.productService.getServicosList(userId);
    }
}
