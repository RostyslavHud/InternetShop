package com.internetshop.mapper;

import com.internetshop.dto.CreationProductDTO;
import com.internetshop.mongoModel.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product creationProductToProduct(CreationProductDTO creationProductDTO);

}
