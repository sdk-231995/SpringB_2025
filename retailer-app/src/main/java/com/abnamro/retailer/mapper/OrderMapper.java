package com.abnamro.retailer.mapper;

import com.abnamro.retailer.entity.Order;
import com.abnamro.retailer.entity.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order mapDtoToOrder(OrderDTO orderDTO);
}
