package internetshop.repository.custom.implementation;

import internetshop.model.Order;
import internetshop.model.Product;
import internetshop.repository.ProductRepository;
import internetshop.repository.custom.OrderRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Order addProductsToOrder(Order order) {
        String query = "select product_id, product_qty from orders_products where order_id = ?1";
        List<Object[]> objects = entityManager.createNativeQuery(query)
                                              .setParameter(1, order.getId())
                                              .getResultList();
        Map<Product, Integer> products = new HashMap<>();
        if (!objects.isEmpty()) {
            for (Object[] object : objects) {
                products.put(productRepository.findById(new Long(object[0].toString())).get(), (Integer) object[1]);
            }
        }

        order.setProducts(products);
        return order;
    }
}
