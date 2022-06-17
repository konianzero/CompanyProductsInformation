package org.company.front.service;

import lombok.RequiredArgsConstructor;
import org.company.persistence.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.company.front.util.ValidationUtil.checkNotFoundWithId;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product create(Product product) {
        return save(product);
    }

    protected Product save(Product product) {
        return productRepository.save(product);
    }

    public Product get(int id) {
        return checkNotFoundWithId(productRepository.findById(id), id);
    }

    public List<Product> getAll(Optional<String> sortColumn,
                                Optional<String> filterColumn,
                                Optional<String> filter,
                                Optional<Integer> costFrom,
                                Optional<Integer> costTo)
    {
        if (filterColumn.isPresent()) {
            Specification<Product> spec = JpaSpecificationUtil.filterBy(filterColumn.get(), filter, costFrom, costTo);
            return sortColumn.map(col -> productRepository.findAll(spec, Sort.by(Sort.Direction.DESC, col)))
                    .orElse(productRepository.findAll(spec));
        } else
            return sortColumn.map(s -> productRepository.findAll(Sort.by(Sort.Direction.DESC, s)))
                    .orElseGet(productRepository::findAll);
    }

    @Transactional
    public void update(Product product) {
        checkNotFoundWithId(save(product), product.getId());
    }

    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(productRepository.delete(id), id);
    }
}
