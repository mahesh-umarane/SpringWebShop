package pl.sda.webstore.services;

public interface OrderService {
    void processOrder(String productId, int count);
}
