package com.internetshop.rest;

import com.internetshop.mysqlModel.Category;
import com.internetshop.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CategoryRestController.class)
class CategoryRestControllerMvcTest extends AbstractRestControllerMvcTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryService categoryService;

    List<Category> categoryList = asList(new Category(1L, "Category 1"),
            new Category(2L, "Category 2"));

    @Test
    void getAllCategories() throws Exception {
        String expectedResult =
                "[{\"id\":1,\"name\":\"Category 1\"}," +
                        "{\"id\":2,\"name\":\"Category 2\"}]";
        when(categoryService.getAll()).thenReturn(categoryList);
        mockMvc.perform(get("/v1/categories")).andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }
}
