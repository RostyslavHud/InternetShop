package com.internetshop.service;

import com.internetshop.mysqlModel.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> getAll();
}
