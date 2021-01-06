package internetshop.repository.custom;

import internetshop.model.Order;

public interface OrderRepositoryCustom {
    Order addProductsToOrder(Order order);
}
