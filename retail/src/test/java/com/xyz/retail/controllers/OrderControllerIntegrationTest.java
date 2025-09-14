package com.xyz.retail.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xyz.retail.dtos.PlaceOrderRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser(username = "USER")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class OrderControllerIntegrationTest implements BaseIntegrationTest {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldPlaceOrder() throws Exception {
        // given data is loaded with data.sql with 3 orders
        final long customerId = 4;
        final String customerName = "test name";
        final String customerPhone = "123";
        final PlaceOrderRequest placeOrderRequest = PlaceOrderRequest.builder().customerId(customerId).customerName(customerName).customerPhone(customerPhone).build();

        // when
        final ResultActions resultActions = mockMvc.perform(post("/api/orders/place").contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(placeOrderRequest)));

        // then
        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(content().string("4"));
    }
}