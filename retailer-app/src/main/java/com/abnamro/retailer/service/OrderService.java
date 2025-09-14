package com.abnamro.retailer.service;

import com.abnamro.retailer.entity.Order;
import com.abnamro.retailer.entity.Product;
import com.abnamro.retailer.entity.dto.OrderDTO;
import com.abnamro.retailer.entity.dto.OrderProductDTO;
import com.abnamro.retailer.exception.InvalidInputException;
import com.abnamro.retailer.repository.OrderRepository;
import com.abnamro.retailer.repository.ProductRepository;
import com.abnamro.retailer.util.ApplicationUtils;
import static com.abnamro.retailer.util.ErrorConstants.ERROR_PRODUCT_IDS_NOT_CORRECT;
import static com.abnamro.retailer.util.ErrorConstants.ERROR_PRODUCT_QUANTITY_NOT_AVAILABLE;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final NotificationService notificationService;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.notificationService = notificationService;
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public Order save(@Valid OrderDTO orderDTO) {

        Set<Long> productIds = orderDTO.getProducts().stream()
                .map(OrderProductDTO::getProductId)
                .collect(Collectors.toSet());
        List<Product> products = productRepository.findAllById(productIds);

        Map<Long, Integer> productRequestedQuantityMap = orderDTO.getProducts().stream()
                .collect(Collectors.toMap(OrderProductDTO::getProductId, OrderProductDTO::getQuantity));

        validateOrder(products, productIds, productRequestedQuantityMap);

        Order order = ApplicationUtils.buildOrder(orderDTO, products, productRequestedQuantityMap);
        Order savedOrder = orderRepository.save(order);

        products.forEach(product -> {
            product.setAvailableQuantity(product.getAvailableQuantity() - productRequestedQuantityMap.get(product.getId()));
            productRepository.save(product);
        });

        notificationService.sendOrderCreatedSMS(order.getCustomerPhone(), order.getCustomerName(), order.getId());
        return savedOrder;
    }

    private void validateOrder(List<Product> products, Set<Long> productIds, Map<Long, Integer> productRequestedQuantityMap) {
        if (products.size() != productIds.size()) {
            throw new InvalidInputException(ERROR_PRODUCT_IDS_NOT_CORRECT);
        }

        String notAvailableQuantityProducts = products.stream()
                .filter(product -> product.getAvailableQuantity() < productRequestedQuantityMap.get(product.getId()))
                .map(Product::getName)
                .collect(Collectors.joining(" and "));

        if (!ObjectUtils.isEmpty(notAvailableQuantityProducts)) {
            throw new InvalidInputException(ERROR_PRODUCT_QUANTITY_NOT_AVAILABLE + notAvailableQuantityProducts);
        }
    }

}
