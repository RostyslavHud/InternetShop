package internetshop.service;

import internetshop.exception.ServiceException;
import internetshop.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    List<Product> getAll();
    Product findByName(String name) throws ServiceException;
}
