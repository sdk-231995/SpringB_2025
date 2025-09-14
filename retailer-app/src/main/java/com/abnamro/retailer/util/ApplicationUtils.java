package com.abnamro.retailer.util;

import com.abnamro.retailer.entity.Order;
import com.abnamro.retailer.entity.OrderProduct;
import com.abnamro.retailer.entity.Product;
import com.abnamro.retailer.entity.dto.OrderDTO;
import com.abnamro.retailer.mapper.OrderMapper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApplicationUtils {

    private static final String DATE_PATTERN = "yyyy/MM/dd";

    public static Order buildOrder(OrderDTO orderDTO, List<Product> products, Map<Long, Integer> productQuantityMap) {
        Order order = OrderMapper.INSTANCE.mapDtoToOrder(orderDTO);
        order.setTime(ZonedDateTime.now());
        List<OrderProduct> orderProducts = products.stream().map(product -> {
                    Integer quantity = productQuantityMap.get(product.getId());
                    BigDecimal amount = product.getPrice().multiply(BigDecimal.valueOf(quantity));
                    return OrderProduct.builder()
                            .order(order)
                            .product(product)
                            .quantity(quantity)
                            .quantityPrice(amount)
                            .build();
                })
                .collect(Collectors.toList());
        order.setOrderProducts(orderProducts);

        BigDecimal totalAmount = orderProducts.stream()
                .map(OrderProduct::getQuantityPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalPrice(totalAmount);
        return order;
    }

    public static String buildAndFormatDate(Integer year, Integer month, Integer day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        return simpleDateFormat.format(calendar.getTime());
    }


}
