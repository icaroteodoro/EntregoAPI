package com.entrego.services;

import com.entrego.domain.Order;
import com.entrego.dtos.order.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;




    public void notifyOrderCreated(Order order) {

        OrderResponse notification = new OrderResponse(
                order.getId(),
                order.getUser().getFirstName() +" "+order.getUser().getLastName(),
                order.getNumberOrder(),
                order.getTotal(),
                order.getCreatedAt(),
                order.getStatus(),
                order.getAddress(),
                order.getPaymentMethod(),
                order.getItems()
        );
        messagingTemplate.convertAndSend("/topic/orders/" + order.getStore().getId(), notification);
    }


}
