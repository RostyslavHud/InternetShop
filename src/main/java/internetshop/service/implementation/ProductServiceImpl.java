package internetshop.service.implementation;

import internetshop.model.Product;
import internetshop.repository.ProductRepository;
import internetshop.service.ProductService;
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

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name).get();
    }
}
