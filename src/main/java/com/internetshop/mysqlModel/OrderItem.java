package com.internetshop.mysqlModel;

import com.internetshop.mongoModel.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_qty")
    private int productQty;

    @Transient
    private Product product;
}
