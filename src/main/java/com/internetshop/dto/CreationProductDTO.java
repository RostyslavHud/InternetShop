package com.internetshop.dto;

import com.internetshop.mysqlModel.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreationProductDTO {

    private String name;

    private double price;

    private List<Category> categories;
}
