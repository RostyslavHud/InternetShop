package com.internetshop.mongoModel;

import com.internetshop.mysqlModel.Category;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "product")
public class Product {

    @Id
    private String id;

    private String name;

    private double price;

    private List<Category> categories;
}
