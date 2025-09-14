package com.abnamro.retailer.util;

import com.abnamro.retailer.entity.Order;
import com.abnamro.retailer.entity.OrderProduct;
import com.abnamro.retailer.entity.Product;
import com.abnamro.retailer.entity.dto.OrderDTO;
import com.abnamro.retailer.entity.dto.OrderProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class TestUtils {


    public static Product buildRandomProductWithoutId() {
        return Product.builder()
                .name(RandomStringUtils.randomAlphabetic(200))
                .description(RandomStringUtils.randomAlphabetic(1000))
                .price(BigDecimal.valueOf(RandomUtils.nextDouble()))
                .availableQuantity(RandomUtils.nextInt())
                .build();
    }

    public static Product buildRandomProduct() {
        return Product.builder()
                .id(RandomUtils.nextLong())
                .name(RandomStringUtils.randomAlphabetic(200))
                .description(RandomStringUtils.randomAlphabetic(1000))
                .price(buildRandomBigDecimal())
                .build();
    }

    public static Order buildRandomOrder() {
        List<OrderProduct> orderProducts = IntStream.range(1, RandomUtils.nextInt(1, 6))
                .mapToObj(i -> buildRandomOrderProduct())
                .collect(Collectors.toList());

        return Order.builder()
                .id(RandomUtils.nextLong())
                .customerName(buildRandomEmail())
                .customerPhone(buildNumberMobileNumber())
                .orderProducts(orderProducts)
                .build();
    }

    public static OrderDTO buildRandomOrderDTO() {
        List<OrderProductDTO> orderProductDTOS = LongStream.range(1, RandomUtils.nextInt(2, 5))
                .mapToObj(TestUtils::buildRandomOrderProductDTO)
                .collect(Collectors.toList());

        return OrderDTO.builder()
                .customerEmail(buildRandomEmail())
                .customerName(RandomStringUtils.randomAlphabetic(200))
                .customerPhone(buildNumberMobileNumber())
                .products(orderProductDTOS)
                .build();
    }

    public static OrderProductDTO buildRandomOrderProductDTO(Long productId) {
        return OrderProductDTO.builder()
                .productId(productId)
                .quantity(1)
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static OrderProduct buildRandomOrderProduct() {
        return OrderProduct.builder()
                .quantityPrice(buildRandomBigDecimal())
                .quantity(RandomUtils.nextInt())
                .build();
    }

    private static BigDecimal buildRandomBigDecimal() {
        return BigDecimal.valueOf(RandomUtils.nextDouble());
    }

    private static String buildRandomEmail() {
        return RandomStringUtils.randomAlphabetic(12) + "@gmail.com";
    }

    private static String buildNumberMobileNumber() {
        return "+31" + RandomStringUtils.randomNumeric(10);
    }
}
