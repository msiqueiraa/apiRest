package com.example.apiRest;

import com.example.apiRest.controllers.ProductController;
import com.example.apiRest.dtos.ProductRecordDto;
import com.example.apiRest.models.ProductModel;

import com.example.apiRest.repositories.ProductRepository;
import com.example.apiRest.services.ProductService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.mediatype.hal.HalLinkDiscoverer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    private ProductRecordDto productRecordDto;
    private ProductModel productModel = new ProductModel();
    @Mock
    private ProductRepository productRepository;
    private ProductController productController;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        productController = mock(ProductController.class);
        productService = new ProductService(productRepository, productController);
        BigDecimal big1 = new BigDecimal("1.5");
        productRecordDto = new ProductRecordDto("Guaravita", big1);

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
    void mustGetAllProducts() throws JsonProcessingException {
        List<ProductModel> productList = new ArrayList<>();
        BigDecimal big1 = new BigDecimal("1.5");
        ProductModel newProduct = new ProductModel();
        newProduct.setName("Guaravita");
        newProduct.setValue(big1);
        ProductModel newProduct2 = new ProductModel();
        newProduct2.setName("biscoito");
        newProduct2.setValue(big1);
        productList.add(newProduct);
        productList.add(newProduct2);
        when(productRepository.findAll()).thenReturn(productList);
        var response = productService.getAllProducts();
        assertFalse(response.isEmpty());
       for(var product : response){
           assertFalse(!product.hasLink("self"));
       }
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
