package com.xyz.retail.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser(username = "USER")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductControllerIntegrationTest implements BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldSearchProduct() throws Exception {
        // given data is loaded

        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/products/search").param("name", "desk"));

        // then
        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(jsonPath("$.[0].id").value("5"));
        resultActions.andExpect(jsonPath("$.[0].name").value("desk"));
        resultActions.andExpect(jsonPath("$.[0].price").value("250.0"));
        resultActions.andExpect(jsonPath("$.[0].quantityAvailable").value("12"));
    }
}