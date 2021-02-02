package com.internetshop.service.implementation;

import com.internetshop.enums.Errors;
import com.internetshop.exception.ServiceException;
import com.internetshop.mongoModel.Product;
import com.internetshop.mongoRepository.ProductRepository;
import com.internetshop.mysqlModel.Category;
import com.internetshop.mysqlRepository.CategoryRepository;
import com.internetshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    @Cacheable("product")
    public Page<Product> getAllByCategoryId(Long id, Pageable pageable) throws ServiceException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ServiceException(Errors.CATEGORY_NOT_FOUND));
        return productRepository.findByCategories(category, pageable);
    }

    @Override
    @CacheEvict(value = "product", allEntries = true)
    public void add(Product product) throws ServiceException {
        if (product == null) {
            throw new ServiceException(Errors.EMPTY_PRODUCT);
        }
        List<Category> categories = new ArrayList<>();
        for (Category category : product.getCategories()) {
            categories.add(categoryRepository.findById(category.getId())
                    .orElseThrow(() -> new ServiceException(Errors.CATEGORY_NOT_FOUND)));
        }
        product.setCategories(categories);
        productRepository.save(product);
    }
}
