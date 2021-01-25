package com.internetshop.mongoRepository;

import com.internetshop.mongoModel.Product;
import com.internetshop.mysqlModel.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findByName(String name);

    List<Product> findByCategories(Category category);
}
