package com.entrego.controllers;

import com.entrego.domain.Order;
import com.entrego.dtos.OrderNotification;
import com.entrego.services.NotificationService;
import com.entrego.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WebSocketController {

    @Autowired
    private NotificationService notificationService;
    
    @Autowired
    private OrderService orderService;

    @MessageMapping("/send-notification")
    @SendTo("/topic/orders")
    public OrderNotification sendNotification(OrderNotification notification) {
        return notification;
    }
    
    // Endpoint para testar o envio de notificações para um pedido específico
    @GetMapping("/notify-test/{orderId}")
    public String testNotification(@PathVariable String orderId) throws Exception {
        Order order = orderService.findOrderById(orderId);
        notificationService.notifyOrderCreated(order);
        return "Notificação enviada com sucesso para o pedido: " + orderId;
    }
    
    // Endpoint para testar se o WebSocket está funcionando
    @GetMapping("/notify-test/dummy")
    public Map<String, Object> testDummyNotification() {
        OrderNotification notification = new OrderNotification(
            "dummy-" + System.currentTimeMillis(),
            "DUMMY-" + System.currentTimeMillis() % 1000,
            "Cliente Teste",
            "store-test",
            null,
            LocalDateTime.now(),
            99.99
        );
        
        notificationService.notifyOrderCreated(notification);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Notificação dummy enviada com sucesso");
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }
} 