package edu.iuh.fit.backEnd.services;

import edu.iuh.fit.backEnd.models.Order;
import edu.iuh.fit.backEnd.repositories.OrderRepository;

import java.util.List;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService() {
        orderRepository = new OrderRepository();
    }

    public List<Order> getAll() {
        return orderRepository.getAll();
    }
}
