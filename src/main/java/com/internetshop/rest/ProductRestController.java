package com.internetshop.rest;

import com.internetshop.dto.CreationProductDTO;
import com.internetshop.exception.ServiceException;
import com.internetshop.mapper.ProductMapper;
import com.internetshop.mongoModel.Product;
import com.internetshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/v1/products")
public class ProductRestController {

    private final ProductService productService;


    @Autowired
    ProductMapper productMapper;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public List<Product> getAllByCategory(@PathVariable Long id) throws ServiceException {
        return productService.getAllByCategoryId(id);
    }

    @PostMapping
    public CreationProductDTO addProduct(@Valid @RequestBody CreationProductDTO productDTO) throws ServiceException {
        productService.add(productMapper.creationProductToProduct(productDTO));
        return productDTO;
    }
}
