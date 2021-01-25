package com.internetshop.service.implementation;

import com.internetshop.mysqlModel.Category;
import com.internetshop.mysqlRepository.CategoryRepository;
import com.internetshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return (List<Category>) categoryRepository.findAll();
    }
}
