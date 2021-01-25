package com.internetshop.service.implementation;

import com.internetshop.model.Product;
import com.internetshop.repository.ProductRepository;
import com.internetshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;


    @Override
    public List<Product> getAll() {
        return (List<Product>) productRepository.findAll();
    }
}
