package internetshop.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name")
    private String name;
    @Column(name = "price")
    private double price;
}
