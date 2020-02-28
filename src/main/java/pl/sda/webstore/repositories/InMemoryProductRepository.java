package pl.sda.webstore.repositories;

import org.springframework.stereotype.Repository;
import pl.sda.webstore.domains.Product;
import pl.sda.webstore.exceptions.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private List<Product> listOfProducts = new ArrayList<>();

    public InMemoryProductRepository() {
        Product iphone = new Product("P1234", "iPhone 5s", new BigDecimal(500));
        iphone.setDescription("Apple iPhone 5s, smartfon z 4-calowym ekranem o rozdzielczości 640x1136 i 8-megapikselowym aparatem");
        iphone.setCategory("Smartfon");
        iphone.setManufacturer("Apple");
        iphone.setUnitsInStock(1000);

        Product dellNotebook = new Product("P1235", "Dell Inspiron", new BigDecimal(700));
        dellNotebook.setDescription("Dell Inspiron, 14-calowy laptop (czarny) z procesorem Intel Core 3. generacji");
        dellNotebook.setCategory("Laptop");
        dellNotebook.setManufacturer("Dell");
        dellNotebook.setUnitsInStock(1000);

        Product nexusTablet = new Product("P1236", "Nexus 7", new BigDecimal(300));
        nexusTablet.setDescription("Google Nexus 7 jest najlżejszym 7-calowym tabletem z 4-rdzeniowym procesorem Qualcomm Snapdragon™ S4 Pro");
        nexusTablet.setCategory("Tablet");
        nexusTablet.setManufacturer("Google");
        nexusTablet.setUnitsInStock(1000);

        listOfProducts.add(iphone);
        listOfProducts.add(dellNotebook);
        listOfProducts.add(nexusTablet);
    }

    @Override
    public List<Product> getAllProducts() {
        return listOfProducts;
    }

    @Override
    public Product getProductById(String productId) {
        return listOfProducts.stream()
                .filter(x -> x != null && productId.equals(x.getProductId()))
                .findFirst()
                .orElseThrow(() -> (new ProductNotFoundException("Brak produktu o wskazanym id: " + productId)));
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return listOfProducts.stream()
                .filter(x -> category.equalsIgnoreCase(x.getCategory()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getProductsByManufacturer(String manufacturer) {
        return listOfProducts.stream()
                .filter(x -> manufacturer.equalsIgnoreCase(x.getManufacturer()))
                .collect(Collectors.toList());
    }


    @Override
    public Set<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
        Set<Product> productsByBrand = new HashSet<>();
        Set<Product> productsByCategory = new HashSet<>();

        for (String filterParam : filterParams.keySet()) {
            if (filterParam.contains("brand")) {
                for (String brandName : filterParams.get("brand")) {
                    for (Product product : this.getProductsByManufacturer(brandName)) {
                        productsByBrand.add(product);
                    }
                }
            }
            if (filterParam.contains("category")) {
                for (String categoryName : filterParams.get("category")) {
                    for (Product product : this.getProductsByCategory(categoryName)) {
                        productsByCategory.add(product);
                    }
                }
            }
        }
        productsByCategory.retainAll(productsByBrand);
        return productsByCategory;
    }

    @Override
    public Set<Product> getProductsByPriceFilter(Map<String, List<String>> priceFilterParams) {
        Set<Product> productsByLowPrice = new HashSet<>();
        Set<Product> productsByHighPrice = new HashSet<>();

        for (String priceFilterParam : priceFilterParams.keySet()) {
            if (priceFilterParam.contains("low")) {
                for (Product product : this.getAllProducts()) {
                    if (product.getUnitPrice().compareTo((new BigDecimal(priceFilterParams.get("low").get(0)))) >= 0) {
                        productsByLowPrice.add(product);
                    }
                }
            }
            if (priceFilterParam.contains("high")) {
                for (Product product : this.getAllProducts()) {
                    if (product.getUnitPrice().compareTo((new BigDecimal(priceFilterParams.get("high").get(0)))) <= 0) {
                        productsByHighPrice.add(product);
                    }
                }
            }
        }
        if (productsByLowPrice.isEmpty() && priceFilterParams.get("low") == null) {
            return productsByHighPrice;
        } else if (productsByHighPrice.isEmpty() && priceFilterParams.get("high") == null) {
            return productsByLowPrice;
        } else {
            productsByHighPrice.retainAll(productsByLowPrice);
            return productsByHighPrice;
        }
    }

    @Override
    public void addProduct(Product product) {
        listOfProducts.add(product);
    }
}
