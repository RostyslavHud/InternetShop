package com.internetshop.rest;

import com.internetshop.dto.CreationProductDTO;
import com.internetshop.mapper.ProductMapper;
import com.internetshop.mongoModel.Product;
import com.internetshop.mysqlModel.Category;
import com.internetshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductRestController.class)
class ProductRestControllerMvcTest extends AbstractRestControllerMvcTest {

    @MockBean
    ProductService productService;

    @MockBean
    ProductMapper productMapper;

    Page<Product> productList = new PageImpl<>(asList(new Product("1L", "Product", 0.0,
            asList(new Category(1L, "Category")))), PageRequest.of(0, 2), 1);

    @Test
    void getAllProducts() throws Exception {

        when(productService.getAllByCategoryId(any(), any())).thenReturn(productList);
        mockMvc.perform(get("/v1/products/{id}", 1L)).andExpect(status().isOk());
    }

    @Test
    void addProduct() throws Exception {
        String expectedResult = "{\"name\":\"name\",\"price\":2.2,\"categories\":[{\"id\":1,\"name\":\"Name\"}]}";

        doNothing().when(productService).add(productList.getContent().get(0));

        mockMvc.perform(post("/v1/products")
                .content(asJsonString(new CreationProductDTO("name", 2.2,
                        asList(new Category(1L, "Name")))))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }
}
