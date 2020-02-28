package pl.sda.webstore.services;

import pl.sda.webstore.domains.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(String productId);

    List<Product> getProductByCategory(String category);

    List<Product> getProductByManufacturer(String manufacturer);

    Set<Product> getProductsByFilter(Map<String, List<String>> filterParams);

    Set<Product> getProductsByPriceFilter(Map<String, List<String>> priceFilterParams);

    void addProduct(Product product);
}
