package com.abnamro.retailer.resource;

import com.abnamro.retailer.entity.Product;
import com.abnamro.retailer.repository.ProductRepository;
import static com.abnamro.retailer.util.TestUtils.asJsonString;
import static com.abnamro.retailer.util.TestUtils.buildRandomProductWithoutId;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "admin", roles = {"ADMIN", "USER"})
public class ProductResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void getProductById() throws Exception {
        Long id = 1L;
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/products/{id}", id)
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        Product product = new ObjectMapper().readValue(json, Product.class);
        Product productFromDB = productRepository.findById(id).orElse(null);
        assertProductEquals(productFromDB, product);
    }

    @Test
    public void getProductByInvalidId_notFound() throws Exception {
        Long id = -1L;
        mockMvc.perform(
                        get("/api/products/{id}", id)
                                .contentType("application/json")
                )
                .andExpect(status().isNotFound());
        assertNull(productRepository.findById(id).orElse(null));
    }

    @Test
    public void getAllProducts() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/products?name.contains=Apple")
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        List<Product> products = new ObjectMapper().readValue(json, List.class);
        List<Product> productFromDB = productRepository.findAll();
        assertEquals(products.size(), productFromDB.stream().filter(product -> product.getName().contains("Apple")).count());
    }

    @Test
    public void createProduct() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        post("/api/products")
                                .content(asJsonString(buildRandomProductWithoutId()))
                                .contentType("application/json")
                )
                .andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        Product products = new ObjectMapper().readValue(json, Product.class);
        Product productFromDB = productRepository.findById(products.getId()).orElse(null);
        assertProductEquals(products, productFromDB);
    }

    @Test
    public void createDuplicateProduct() throws Exception {
        Product product = buildRandomProductWithoutId();
        MvcResult mvcResult = mockMvc.perform(post("/api/products")
                        .content(asJsonString(product))
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        Product products = new ObjectMapper().readValue(json, Product.class);
        Product productFromDB = productRepository.findById(products.getId()).orElse(null);
        assertProductEquals(products, productFromDB);

        mockMvc.perform(post("/api/products")
                        .content(asJsonString(product))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest()).andReturn();
    }

    private static void assertProductEquals(Product product1, Product product2) {
        assertNotNull(product1);
        assertNotNull(product2);
        assertEquals(product1.getId(), product2.getId());
        assertEquals(product1.getName(), product2.getName());
        assertEquals(product1.getDescription(), product2.getDescription());
        assertEquals(product1.getPrice(), product2.getPrice());
    }
}