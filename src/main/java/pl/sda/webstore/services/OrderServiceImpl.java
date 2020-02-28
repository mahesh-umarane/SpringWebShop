package pl.sda.webstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.webstore.domains.Product;
import pl.sda.webstore.exceptions.ProductNotFoundException;
import pl.sda.webstore.exceptions.ProductOutOfStockException;
import pl.sda.webstore.repositories.ProductRepository;

@Service
public class OrderServiceImpl implements OrderService {

    private ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void processOrder(String productId, int count) {
        Product product = productRepository.getProductById(productId);

        if (product.getUnitsInStock() < count) {
            throw new ProductOutOfStockException(
                    "Zbyt mało towaru. Obecna liczba sztuk w magazynie: " + product.getUnitsInStock());
        }
        product.setUnitsInStock(product.getUnitsInStock() - count);
    }
}
