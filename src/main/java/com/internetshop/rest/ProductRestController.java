package com.internetshop.rest;

import com.internetshop.dto.CreationProductDTO;
import com.internetshop.exception.ServiceException;
import com.internetshop.mapper.ProductMapper;
import com.internetshop.mongoModel.Product;
import com.internetshop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/products")
public class ProductRestController {

    private final ProductService productService;


    @Autowired
    ProductMapper productMapper;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get all products by category id")
    @ApiResponse(responseCode = "200", description = "Get all products by",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))})
    public Page<Product> getAllByCategory(@Parameter(description = "Category if for search product") @PathVariable Long id,
                                          @Parameter(description = "Parameters for pagination and sorting") Pageable pageable) throws ServiceException {
        return productService.getAllByCategoryId(id, pageable);
    }

    @PostMapping
    @Operation(summary = "Add new product")
    @ApiResponse(responseCode = "200", description = "Add new product",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))})
    public CreationProductDTO addProduct(@Parameter(description = "new product") @Valid @RequestBody CreationProductDTO productDTO) throws ServiceException {
        productService.add(productMapper.creationProductToProduct(productDTO));
        return productDTO;
    }
}
