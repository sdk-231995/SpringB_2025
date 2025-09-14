package com.abnamro.retailer.resource;

import com.abnamro.retailer.entity.Order;
import com.abnamro.retailer.entity.Product;
import com.abnamro.retailer.entity.dto.OrderDTO;
import com.abnamro.retailer.repository.OrderRepository;
import com.abnamro.retailer.repository.ProductRepository;
import static com.abnamro.retailer.util.TestUtils.asJsonString;
import static com.abnamro.retailer.util.TestUtils.buildRandomOrderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(value = "admin", roles = {"ADMIN", "USER"})
@Transactional
class OrderResourceIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;


    @Test
    public void createValidOrder_SavedSuccessfully() throws Exception {
        OrderDTO orderDTO = buildRandomOrderDTO();
        Integer quantityAvailabilityBeforeRequest = productRepository.findById(orderDTO.getProducts().get(0).getProductId())
                .map(Product::getAvailableQuantity).orElse(0);

        Integer requestedQuantity = orderDTO.getProducts().get(0).getQuantity();

        MvcResult mvcResult = mockMvc.perform(post("/api/orders")
                        .content(asJsonString(orderDTO))
                        .contentType("application/json"))
                .andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Order order = objectMapper.readValue(json, Order.class);
        assertOrder(order, orderDTO);
        Order orderFromDB = orderRepository.findById(order.getId()).orElse(null);
        assertOrder(orderFromDB, orderFromDB);
        assertEquals(orderFromDB.getOrderProducts().get(0).getProduct().getAvailableQuantity(),
                quantityAvailabilityBeforeRequest - requestedQuantity);
    }

    @Test
    public void createInvalidOrderMissingData_Error400() throws Exception {
        OrderDTO orderDTO = buildRandomOrderDTO();
        orderDTO.setCustomerName(null);
        mockMvc.perform(post("/api/orders")
                        .content(asJsonString(orderDTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInvalidOrderByNotExistProduct_Error400() throws Exception {
        OrderDTO orderDTO = buildRandomOrderDTO();
        orderDTO.getProducts().get(0).setProductId(-1L);
        mockMvc.perform(post("/api/orders")
                        .content(asJsonString(orderDTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInvalidOrderByEmptyProducts_Error400() throws Exception {
        OrderDTO orderDTO = buildRandomOrderDTO();
        orderDTO.setProducts(List.of());
        mockMvc.perform(post("/api/orders")
                        .content(asJsonString(orderDTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createInvalidOrderByTooManyProductQuantity_Error400() throws Exception {
        OrderDTO orderDTO = buildRandomOrderDTO();
        orderDTO.getProducts().get(0).setQuantity(10000);
        orderDTO.setProducts(List.of());
        mockMvc.perform(post("/api/orders")
                        .content(asJsonString(orderDTO))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    private static void assertOrder(Order order, OrderDTO orderDTO) {
        assertNotNull(order);
        assertNotNull(orderDTO);
        assertEquals(order.getCustomerName(), orderDTO.getCustomerName());
        assertEquals(order.getCustomerEmail(), orderDTO.getCustomerEmail());
        assertEquals(order.getCustomerPhone(), orderDTO.getCustomerPhone());
        assertEquals(order.getOrderProducts().size(), orderDTO.getProducts().size());
        assertEquals(order.getOrderProducts().get(0).getQuantity(), orderDTO.getProducts().get(0).getQuantity());
        assertEquals(order.getOrderProducts().get(0).getProduct().getId(), orderDTO.getProducts().get(0).getProductId());
    }

    private static void assertOrder(Order order1, Order order2) {
        assertNotNull(order1);
        assertNotNull(order2);
        assertEquals(order1.getId(), order2.getId());
        assertEquals(order1.getCustomerName(), order2.getCustomerName());
        assertEquals(order1.getCustomerEmail(), order2.getCustomerEmail());
        assertEquals(order1.getCustomerPhone(), order2.getCustomerPhone());
        assertEquals(order1.getTotalPrice(), order2.getTotalPrice());
        assertEquals(order1.getOrderProducts().size(), order2.getOrderProducts().size());
        assertEquals(order1.getOrderProducts().get(0).getId(), order2.getOrderProducts().get(0).getId());
    }

}