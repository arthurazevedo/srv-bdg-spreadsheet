package com.fourbudget.spreadsheet.controller;

import com.fourbudget.spreadsheet.model.Product;
import com.fourbudget.spreadsheet.repository.ProductRepository;
import com.fourbudget.spreadsheet.service.ProductService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductControllerTest {

    private String userId;

    private List<Product> productList;

    private Product product;

    @Mock
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        productList = new ArrayList<>();
        product = new Product();
        userId = "1";
        product.setUserId(userId);
        productService = new ProductService(productRepository);
        productController = new ProductController(productService);
        productList.add(product);
    }

    @Test
    public void getProducts() {
        Mockito.when(productRepository.findByUserId(Mockito.any())).thenReturn(Optional.of(productList));
        List<Product> returned = productController.getProducts(userId);
        Assert.assertEquals(1, returned.size());
        Assert.assertEquals(product, returned.get(0));
    }
}