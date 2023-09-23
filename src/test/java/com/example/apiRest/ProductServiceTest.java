package com.example.apiRest;

import com.example.apiRest.dtos.ProductRecordDto;
import com.example.apiRest.models.ProductModel;

import com.example.apiRest.repositories.ProductRepository;
import com.example.apiRest.services.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    private ProductRecordDto productRecordDto;
    private ProductModel productModel;
    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setup() {
        BigDecimal big1 = new BigDecimal("1.5");
        productRecordDto = new ProductRecordDto("Guaravita", big1);
        productModel = new ProductModel();

    }
    @Test
    void mustSaveProduct() {
        BigDecimal big1 = new BigDecimal("1.5");
        ProductModel newProduct = new ProductModel();
        newProduct.setName("Guaravita");
        newProduct.setValue(big1);
        productRepository.save(newProduct);
        var response = productService.saveProduct(productRecordDto);
        assertNotNull(response);
    }

    @Test
    void mustGetAllProducts() {
        List<ProductModel> productList = new ArrayList<>();
        productList.add(new ProductModel());
        productList.add(new ProductModel());
        var response = productService.getAllProducts();
        assertNotNull(response);
    }

    @Test
    void MustGetOneProduct() {
        UUID id = UUID.randomUUID();
        var response = productService.getOneProduct(id);
        assertNotNull(response);
    }

    @Test
    void mustUpdateProduct() {
        UUID id = UUID.randomUUID();
        BigDecimal big1 = new BigDecimal("1.5");
        ProductModel newProduct = new ProductModel();
        newProduct.setName("Guaravita");
        newProduct.setValue(big1);
        productRepository.save(newProduct);
        var response = productService.updateProduct(id, productRecordDto);
        assertNotNull(response);
    }

    @Test
    void mustDeleteProduct() {
        UUID id = UUID.randomUUID();
        var response = productService.deleteProduct(id);
        assertNotNull(response);
    }

}
