package com.example.apiRest.services;

import com.example.apiRest.controllers.ProductController;
import com.example.apiRest.dtos.ProductRecordDto;
import com.example.apiRest.models.ProductModel;
import com.example.apiRest.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    public ResponseEntity<ProductModel> saveProduct(ProductRecordDto productRecordDto){
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));
    }
    public ResponseEntity<List<ProductModel>>getAllProducts(){
        List<ProductModel> productsList = productRepository.findAll();
        if(!productsList.isEmpty()){
            for(ProductModel product : productsList){
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(product.getIdProduct())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productsList);
    }

    public ResponseEntity<Object> getOneProduct(UUID id) {
        Optional<ProductModel> productSelect = productRepository.findById(id);
        if(productSelect.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(productSelect.get());
    }
    public ResponseEntity<Object> updateProduct(UUID id, ProductRecordDto productRecordDto){ Optional<ProductModel> productSelect = productRepository.findById(id);
        if(productSelect.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        var productModel = productSelect.get();
        BeanUtils.copyProperties(productRecordDto,productModel);
        return ResponseEntity.status(HttpStatus.OK).body(productRepository.save(productModel));
    }
    public ResponseEntity<Object> deleteProduct(UUID id){
        Optional<ProductModel> productSelect = productRepository.findById(id);
        if (productSelect.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productRepository.delete(productSelect.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
    }
}
