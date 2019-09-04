package ru.sgera.smddevTestTask.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.sgera.smddevTestTask.model.Product;
import ru.sgera.smddevTestTask.repos.ProductRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductRepository productRepo;

    private String customId = "5d63e5660815ee34c046f6ff";

    @Before
    public void clearDB() {
        productRepo.deleteAll();
    }

    @Test
    public void getDetailsById() throws Exception {
        Product customIdProduct = new Product(customId,"name", "description", new ArrayList<>());
        when(productRepo.findById(customId)).thenReturn(Optional.of(customIdProduct));
        mvc.perform(get("/products/" + customId))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"" + customId + "\",\"name\":\"name\",\"description\":\"description\",\"parameters\":[]}"));

        String unknownId = "1";
        when(productRepo.findById(unknownId)).thenReturn(Optional.empty());
        mvc.perform(get("/products/" + unknownId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getByName() throws Exception {
        String name1 = "name 1";
        String name2 = "name 2";
        String nameDifferent = "something new";

        String commonSubstr = "name";
        when(productRepo.findByNameLike(commonSubstr)).thenReturn(Arrays.asList(name1, name2));
        mvc.perform(get("/products/search/" + commonSubstr))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"" + name1 + "\",\"" + name2 + "\"]"));

        String uniqueSubstr = "new";
        when(productRepo.findByNameLike(uniqueSubstr)).thenReturn(Collections.singletonList(nameDifferent));
        mvc.perform(get("/products/search/" + uniqueSubstr))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"" + nameDifferent + "\"]"));

        String nonexistentSubstr = "non";
        when(productRepo.findByNameLike(nonexistentSubstr)).thenReturn(Collections.emptyList());
        mvc.perform(get("/products/search/" + nonexistentSubstr))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
