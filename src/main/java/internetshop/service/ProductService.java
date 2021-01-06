package internetshop.service;

import internetshop.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    Product getById(Long id);
    List<Product> getAllByOrderId(Long id);

}
