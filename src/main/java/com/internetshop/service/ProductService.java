package com.internetshop.service;

import com.internetshop.exception.ServiceException;
import com.internetshop.mongoModel.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    Page<Product> getAllByCategoryId(Long id, Pageable pageable) throws ServiceException;

    void add(Product product) throws ServiceException;
}
