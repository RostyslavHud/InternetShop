package com.internetshop.service;

import com.internetshop.exception.ServiceException;
import com.internetshop.mongoModel.Product;
import com.internetshop.mysqlModel.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<Product> getAll();

    List<Product> getAllByCategoryId(Long id) throws ServiceException;

    void add(Product product) throws ServiceException;
}
