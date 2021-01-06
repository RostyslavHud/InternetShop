package internetshop.repository.custom;

import internetshop.model.Order;
import internetshop.repository.RepositoryException;

public interface OrderRepositoryCustom {
    Order addProductsToOrder(Order order) throws RepositoryException;
}
