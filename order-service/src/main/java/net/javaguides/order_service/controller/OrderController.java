package net.javaguides.order_service.controller;

import net.javaguides.base_domain.dto.Order;
import net.javaguides.base_domain.dto.OrderEvent;
import net.javaguides.order_service.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private OrderProducer orderProducer;
    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/order")
    public String placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setMessage("Pending");
        orderEvent.setStatus("Order status is in pending");
        orderEvent.setOrder(order);
        orderProducer.sendMessage(orderEvent);
        return "Order placed Successfully";
    }
}
