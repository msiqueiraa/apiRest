package com.example.apiRest;

import com.example.apiRest.controllers.ProductController;
import com.example.apiRest.dtos.ProductRecordDto;
import com.example.apiRest.models.ProductModel;
import com.example.apiRest.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @InjectMocks
    private ProductController productController;
    @Mock
    @Autowired
    private ProductService productService;
    private ProductRecordDto productRecordDto;

    private ProductModel productModel;

    @BeforeEach
    void setup() {
        BigDecimal big1 = new BigDecimal("1.5");
        productRecordDto = new ProductRecordDto("Guaravita", big1);
        productModel = new ProductModel();

    }

    @Test
    void mustSaveProduct() {
        when(productService.saveProduct(productRecordDto)).thenReturn(productModel);
        var response = assertDoesNotThrow(() -> productController.saveProduct(productRecordDto));
        assertEquals(ResponseEntity.status(CREATED).body(productModel), response);
        assertNotNull(response);
    }

    @Test
    void mustGetAllProducts() {
        List<ProductModel> productList = new ArrayList<>();
        productList.add(new ProductModel());
        productList.add(new ProductModel());
        when(productService.getAllProducts()).thenReturn(productList);
        var response = assertDoesNotThrow(() -> productController.getAllProducts());
        assertEquals(ResponseEntity.status(OK).body(productList), response);
        assertNotNull(response);
    }

    @Test
    void mustGetOneProduct() {
        UUID id = UUID.randomUUID();
        when(productService.getOneProduct(id)).thenReturn(productModel);
        var response = assertDoesNotThrow(() -> productController.getOneProduct(id));
        assertEquals(ResponseEntity.status(OK).body(productModel), response);
        assertNotNull(response);

    }

    @Test
    void mustUpdateProduct() {
        UUID id = UUID.randomUUID();
        when(productService.updateProduct(id,productRecordDto)).thenReturn(productModel);
        var response = assertDoesNotThrow(() -> productController.updateProduct(id,productRecordDto));
        assertEquals(ResponseEntity.status(OK).body(productModel), response);
        assertNotNull(response);
    }

    @Test
    void mustDeleteProduct(){
        UUID id = UUID.randomUUID();
        when(productService.deleteProduct(id)).thenReturn(productModel);
        var response = assertDoesNotThrow(() -> productController.deleteProduct(id));
        assertEquals(ResponseEntity.status(OK).body("Product deleted successfully. " + productModel), response);


    }
}
