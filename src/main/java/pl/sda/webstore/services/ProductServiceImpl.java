package pl.sda.webstore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.webstore.domains.Product;
import pl.sda.webstore.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    @Override
    public List<Product> getProductByManufacturer(String manufacturer) {
        return productRepository.getProductsByManufacturer(manufacturer);
    }

    @Override
    public Product getProductById(String productId) {
        return productRepository.getProductById(productId);
    }

    @Override
    public Set<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
        return productRepository.getProductsByFilter(filterParams);
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.getProductsByCategory(category);
    }

    @Override
    public Set<Product> getProductsByPriceFilter(Map<String, List<String>> priceFilterParams) {
        return productRepository.getProductsByPriceFilter(priceFilterParams);
    }

    public Set<Product> getProductsByPriceManufacturerAndCategory(
            Map<String, List<String>> priceParams, String manufacturer, String category){
        Set<Product> productsByPrice = this.getProductsByPriceFilter(priceParams);
        productsByPrice.retainAll(new HashSet<>(getProductByManufacturer(manufacturer)));
        productsByPrice.retainAll(new HashSet<>(getProductByCategory(category)));
        return productsByPrice;
    }

    @Override
    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }
}
