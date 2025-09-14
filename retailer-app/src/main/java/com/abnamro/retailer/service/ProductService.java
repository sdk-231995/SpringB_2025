package com.abnamro.retailer.service;

import com.abnamro.retailer.entity.Product;
import com.abnamro.retailer.entity.criteria.ProductCriteria;
import com.abnamro.retailer.exception.InvalidInputException;
import com.abnamro.retailer.repository.ProductRepository;
import com.abnamro.retailer.util.ErrorConstants;
import static com.abnamro.retailer.util.SpecificationUtils.buildBigDecimalSpecification;
import static com.abnamro.retailer.util.SpecificationUtils.buildSpecification;
import static com.abnamro.retailer.util.SpecificationUtils.buildStringSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        Product savedProduct = productRepository.findByName(product.getName());
        if (savedProduct != null) {
            throw new InvalidInputException(ErrorConstants.ERROR_NAME_ALREADY_USED);
        }

        return productRepository.save(product);
    }

    public List<Product> findAll(ProductCriteria productCriteria) {
        Specification<Product> specification = createSpecification(productCriteria);
        return productRepository.findAll(specification);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findAllById(Set<Long> ids) {
        return productRepository.findAllById(ids);
    }

    private static Specification<Product> createSpecification(ProductCriteria criteria) {
        Specification<Product> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), "id"));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), "name"));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), "description"));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildBigDecimalSpecification(criteria.getPrice(), "price"));
            }
        }
        return specification;
    }
}
