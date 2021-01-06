package internetshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
@NamedQuery(
        name = "Order.findByNumber",
        query = "select o from Order o where o.orderNumber = :orderNumber")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "order_number")
    private Long orderNumber;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    private Date date;
    @Column(name = "order_status")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Transient
    private Map<Product, Integer> products;
    @Column(name = "shipping_address")
    private String shippingAddress;
    @Column(name = "description")
    private String description;
}
