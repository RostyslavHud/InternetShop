package internetshop.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "price")
    private double price;
}
