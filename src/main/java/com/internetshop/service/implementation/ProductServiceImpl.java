package com.internetshop.service.implementation;

import com.internetshop.enums.Errors;
import com.internetshop.exception.ServiceException;
import com.internetshop.mongoModel.Product;
import com.internetshop.mongoRepository.ProductRepository;
import com.internetshop.mysqlModel.Category;
import com.internetshop.mysqlRepository.CategoryRepository;
import com.internetshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAllByCategoryId(Long id) throws ServiceException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ServiceException(Errors.CATEGORY_NOT_FOUND));
        return productRepository.findByCategories(category);
    }

    @Override
    public void add(Product product) throws ServiceException {
        if (product == null) {
            throw new ServiceException(Errors.EMPTY_PRODUCT);
        }
        productRepository.save(product);
    }
}
