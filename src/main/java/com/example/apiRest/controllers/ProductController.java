package com.example.apiRest.controllers;

import com.example.apiRest.services.ProductService;
import com.example.apiRest.dtos.ProductRecordDto;
import com.example.apiRest.models.ProductModel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ProductModel> saveProduct(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productSaved = productService.saveProduct(productRecordDto);
        return ResponseEntity.status(CREATED).body(productSaved);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        var productsList = productService.getAllProducts();
        return ResponseEntity.status(OK).body(productsList);
    }

    @GetMapping("products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id") UUID id) {
        var productSelect = productService.getOneProduct(id);
        return ResponseEntity.status(OK).body(productSelect);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id, @RequestBody @Valid ProductRecordDto productRecordDto) {
        var productUpdated = productService.updateProduct(id, productRecordDto);
        return ResponseEntity.status(OK).body(productUpdated);
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(value = "id") UUID id) {
        var productSelected = productService.deleteProduct(id);
        return ResponseEntity.status(OK).body("Product deleted successfully. " + productSelected);
    }
}
