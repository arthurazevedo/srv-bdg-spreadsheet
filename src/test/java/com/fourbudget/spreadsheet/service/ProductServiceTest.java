package com.fourbudget.spreadsheet.service;

import com.fourbudget.spreadsheet.config.error.SpreadsheetApplicationException;
import com.fourbudget.spreadsheet.model.Product;
import com.fourbudget.spreadsheet.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ProductServiceTest {

    private String userId;

    private List<Product> productList;

    private Product product;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
        productList.add(product);
    }

    @Test
    public void sucessfullyGetProductsList() {
        Mockito.when(productRepository.findByUserId(Mockito.any())).thenReturn(Optional.of(productList));
        List<Product> returned = productService.getProductsList(userId);
        Assert.assertEquals(1, returned.size());
        Assert.assertEquals(product, returned.get(0));
    }

    @Test
    public void errorGetProductsList() {
        expectedException.expect(SpreadsheetApplicationException.class);
        Mockito.when(productRepository.findByUserId(Mockito.any())).thenReturn(Optional.of(new ArrayList<Product>()));
        productService.getProductsList(userId);
    }

    @Test
    public void errorGetProductsListEmpty() {
        expectedException.expect(SpreadsheetApplicationException.class);
        Mockito.when(productRepository.findByUserId(Mockito.any())).thenReturn(Optional.empty());
        productService.getProductsList(userId);
    }
}