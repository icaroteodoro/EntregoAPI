package com.entrego.services;

import com.entrego.domain.Order;
import com.entrego.dtos.OrderNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notifyOrderCreated(Order order) {
        OrderNotification notification = new OrderNotification(
            order.getId(),
            order.getNumberOrder(),
            order.getUser().getFirstName() + " " + order.getUser().getLastName(),
            order.getStore().getId(),
            order.getStatus(),
            order.getCreatedAt(),
            order.getTotal()
        );

        // Enviar notificação para um tópico específico da loja
        messagingTemplate.convertAndSend("/topic/orders/" + order.getStore().getId(), notification);
        
        // Enviar para o tópico geral de pedidos
        messagingTemplate.convertAndSend("/topic/orders", notification);
    }
    
    public void notifyOrderCreated(OrderNotification notification) {
        // Enviar para o tópico específico da loja se storeId estiver disponível
        if (notification.getStoreId() != null && !notification.getStoreId().isEmpty()) {
            messagingTemplate.convertAndSend("/topic/orders/" + notification.getStoreId(), notification);
        }
        
        // Enviar para o tópico geral de pedidos
        messagingTemplate.convertAndSend("/topic/orders", notification);
    }

    public void notifyOrderStatusChanged(Order order) {
        OrderNotification notification = new OrderNotification(
            order.getId(),
            order.getNumberOrder(),
            order.getUser().getFirstName() + " " + order.getUser().getLastName(),
            order.getStore().getId(),
            order.getStatus(),
            order.getCreatedAt(),
            order.getTotal()
        );

        // Notificar a loja sobre a alteração de status
        messagingTemplate.convertAndSend("/topic/orders/" + order.getStore().getId(), notification);
        
        // Notificar o cliente específico
        messagingTemplate.convertAndSend("/topic/orders/user/" + order.getUser().getId(), notification);
    }
} 