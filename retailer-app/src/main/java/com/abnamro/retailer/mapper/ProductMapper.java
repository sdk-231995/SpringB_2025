package com.abnamro.retailer.mapper;

import com.abnamro.retailer.entity.Product;
import com.abnamro.retailer.entity.dto.ProductDTO;
import com.abnamro.retailer.entity.dto.ReportProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product mapDtoToProduct(ProductDTO productDTO);

    @Mapping(target = "isLowQuantity", source = "availableQuantity", qualifiedByName = "isQuantityLow")
    ReportProductDTO mapToReportProductDTO(Product product);

    @Named("isQuantityLow")
    public static boolean isQuantityLow(Integer availableQuantity) {
        return availableQuantity < 10;
    }
}
