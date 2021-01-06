package internetshop.repository;

import internetshop.model.Order;
import internetshop.repository.custom.OrderRepositoryCustom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>, OrderRepositoryCustom {
    Optional<Order> findOrderByOrderNumber(Long orderNumber);
}
