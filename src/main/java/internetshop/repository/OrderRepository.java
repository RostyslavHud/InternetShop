package internetshop.repository;

import internetshop.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findOrderByOrderNumber(Long orderNumber);
    Optional<List<Order>> findAllByUserId(Long userId);
}
