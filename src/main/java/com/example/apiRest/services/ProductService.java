package com.example.apiRest.services;

import com.example.apiRest.controllers.ProductController;
import com.example.apiRest.dtos.ProductRecordDto;
import com.example.apiRest.models.ProductModel;
import com.example.apiRest.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductController productController;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductController productController) {
        this.productRepository = productRepository;
        this.productController = productController;
    }

    public ProductModel saveProduct(ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        productRepository.save(productModel);
        return productModel;
    }

    public List<ProductModel> getAllProducts() {
        List<ProductModel> productsList = productRepository.findAll();
        if (!productsList.isEmpty()) {
            for (ProductModel product : productsList) {
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(product.getIdProduct())).withSelfRel());
            }
        }
        return productsList;
    }

    public Object getOneProduct(UUID id) {
        Optional<ProductModel> productSelect = productRepository.findById(id);
        if (productSelect.isEmpty()) {
            return status(NOT_FOUND).body("Product not found.");
        }
        return productSelect.get();
    }

    public Object updateProduct(UUID id, ProductRecordDto productRecordDto) {
        Optional<ProductModel> productSelect = productRepository.findById(id);
        if (productSelect.isEmpty()) {
            return status(NOT_FOUND).body("Product not found");
        }
        var productModel = productSelect.get();
        BeanUtils.copyProperties(productRecordDto, productModel);
        productRepository.save(productModel);
        return productModel;
    }

    public Object deleteProduct(UUID id) {
        Optional<ProductModel> productSelect = productRepository.findById(id);
        if (productSelect.isEmpty()) {
            return status(NOT_FOUND).body("Product not found");
        }
        productRepository.delete(productSelect.get());
        return productSelect;
    }
}
