package com.xyz.retail.controllers;

import com.xyz.retail.entities.ProductEntity;
import com.xyz.retail.services.CartItemService;
import com.xyz.retail.services.OrderService;
import com.xyz.retail.services.ProductService;
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
@WithMockUser(username = "ADMIN", roles = {"USER", "ADMIN"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminControllerIntegrationTest implements BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Test
    void shouldSearchOrder() throws Exception {
        // given data is loaded

        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/admin/search-order").param("orderId", "3"));

        // then
        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(jsonPath("$.customerName").value("customer3"));
        resultActions.andExpect(jsonPath("$.customerPhoneNumber").value("+31768686678"));
        resultActions.andExpect(jsonPath("$.totalAmount").value(989.98));
        resultActions.andExpect(jsonPath("$.orderItems.[0].orderId").value(3));
        resultActions.andExpect(jsonPath("$.orderItems.[0].productId").value(4));
        resultActions.andExpect(jsonPath("$.orderItems.[0].quantity").value(2));
        resultActions.andExpect(jsonPath("$.orderItems.[0].totalPrice").value(239.98));
        resultActions.andExpect(jsonPath("$.orderItems.[1].orderId").value(3));
        resultActions.andExpect(jsonPath("$.orderItems.[1].productId").value(5));
        resultActions.andExpect(jsonPath("$.orderItems.[1].quantity").value(3));
        resultActions.andExpect(jsonPath("$.orderItems.[1].totalPrice").value(750));
    }

    @Test
    void shouldRetrieveSaleAmountReport() throws Exception {
        // given data is loaded

        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/admin/retrieve-sale-amount-report")
                .param("startDate", "2023-01-11")
                .param("endDate", "2023-01-15"));

        // then
        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(jsonPath("$.[0].date").value("2023-01-11 00:00:00.0"));
        resultActions.andExpect(jsonPath("$.[0].totalAmount").value("699.39"));
        resultActions.andExpect(jsonPath("$.[1].date").value("2023-01-12 00:00:00.0"));
        resultActions.andExpect(jsonPath("$.[1].totalAmount").value("449.99"));
        resultActions.andExpect(jsonPath("$.[2].date").value("2023-01-13 00:00:00.0"));
        resultActions.andExpect(jsonPath("$.[2].totalAmount").value("989.98"));
    }

    @Test
    void shouldRetrieveBestSellingReport() throws Exception {
        // given
        final ProductEntity product = productService.getProductById(5L);
        final long customerId = 12L;
        cartItemService.addProductToCart(customerId, product, 9);
        orderService.placeOrder(customerId, "test1", "+31123");

        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/admin/retrieve-best-selling-report"));

        // then
        resultActions.andExpect(status().is2xxSuccessful());
        resultActions.andExpect(jsonPath("$.[0].productName").value("desk"));
        resultActions.andExpect(jsonPath("$.[0].totalQuantitySold").value("9"));
        resultActions.andExpect(jsonPath("$.[0].quantityAvailable").value("3"));
        resultActions.andExpect(jsonPath("$.[0].price").value("250.0"));
        resultActions.andExpect(jsonPath("$.[0].isNearingOutOfStock").value("true"));
    }
}