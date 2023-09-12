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

    }

    @Test
    void MustSaveProduct() {
        BigDecimal big1 = new BigDecimal("1.5");
        ProductModel newProduct = new ProductModel();
        newProduct.setName("Guaravita");
        newProduct.setValue(big1);
        productRepository.save(newProduct);
        var response =  productService.saveProduct(productRecordDto);
        assertNotNull(response);
    }
}
