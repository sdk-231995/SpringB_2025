package com.xyz.retail.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.retail.dtos.AddProductToCartRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser(username = "USER")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CartControllerIntegrationTest implements BaseIntegrationTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAddProductToCart() throws Exception {
        // given
        final long customerId = 11543;
        final int quantity = 1;
        final long productId = 5;
        final AddProductToCartRequest addProductToCartRequest = AddProductToCartRequest.builder().customerId(customerId).quantity(quantity).productId(productId).build();

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/carts/add").contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(addProductToCartRequest)));

        // then
        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(jsonPath("$.customerId").value(String.valueOf(customerId)));
        resultActions.andExpect(jsonPath("$.productId").value(String.valueOf(productId)));
        resultActions.andExpect(jsonPath("$.quantity").value(String.valueOf(quantity)));
    }
}