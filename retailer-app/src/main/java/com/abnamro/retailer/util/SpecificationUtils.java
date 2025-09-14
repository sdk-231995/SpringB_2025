package com.abnamro.retailer.util;

import com.abnamro.retailer.entity.Product;
import com.abnamro.retailer.entity.criteria.BigDecimalFilter;
import com.abnamro.retailer.entity.criteria.Filter;
import com.abnamro.retailer.entity.criteria.StringFilter;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationUtils {


    public static Specification<Product> buildSpecification(Filter filter, String fieldName) {
        if (filter.getEquals() != null) {
            return (root, query, builder) -> builder.equal(root.get(fieldName), filter.getEquals());
        } else if (filter.getNotEquals() != null) {
            return (root, query, builder) -> builder.notEqual(root.get(fieldName), filter.getNotEquals());
        }
        return null;
    }

    public static Specification<Product> buildBigDecimalSpecification(BigDecimalFilter filter, String fieldName) {
        if (filter.getEquals() != null) {
            return (root, query, builder) -> builder.equal(root.get(fieldName), filter.getEquals());
        } else if (filter.getNotEquals() != null) {
            return (root, query, builder) -> builder.notEqual(root.get(fieldName), filter.getNotEquals());
        } else if (filter.getGreaterThanOrEquals() != null) {
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(fieldName), filter.getGreaterThanOrEquals());
        } else if (filter.getLessThanOrEquals() != null) {
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(fieldName), filter.getLessThanOrEquals());
        }
        return null;
    }

    public static Specification<Product> buildStringSpecification(StringFilter filter, String fieldName) {
        if (filter.getEquals() != null) {
            return (root, query, builder) -> builder.equal(builder.upper(root.get(fieldName)), filter.getEquals().toUpperCase());
        } else if (filter.getNotEquals() != null) {
            return (root, query, builder) -> builder.notEqual(builder.upper(root.get(fieldName)), filter.getNotEquals().toUpperCase());
        } else if (filter.getContains() != null) {
            return (root, query, builder) -> builder.like(builder.upper(root.get(fieldName)), wrapLikeQuery(filter.getContains()));
        }
        return null;
    }

    private static String wrapLikeQuery(String txt) {
        return "%" + txt.toUpperCase() + '%';
    }

}
