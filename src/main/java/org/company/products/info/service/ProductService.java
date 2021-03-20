package org.company.products.info.service;

import org.company.products.info.model.Product;
import org.company.products.info.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        return save(product);
    }

    @Transactional
    protected Product save(Product product) {
        return productRepository.save(product);
    }

    public Product get(int id) {
        return productRepository.findById(id).get();
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public void update(Product product) {
        save(product);
    }

    @Transactional
    public void delete(int id) {
        productRepository.deleteById(id);
    }
}
