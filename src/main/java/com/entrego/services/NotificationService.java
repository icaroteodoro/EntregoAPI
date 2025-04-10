package com.entrego.services;

import com.entrego.domain.Order;
import com.entrego.dtos.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private AddressService addressService;


    public void notifyOrderCreated(Order order) {

        OrderResponse notification = new OrderResponse(
                order.getId(),
                order.getUser().getFirstName() +" "+order.getUser().getLastName(),
                order.getNumberOrder(),
                order.getCreatedAt(),
                order.getStatus(),
                this.addressService.findAddressByUserIdAndIsMain(order.getUser().getId()),
                order.getPaymentMethod(),
                order.getListItems()
        );
        messagingTemplate.convertAndSend("/topic/orders/" + order.getStore().getId(), notification);
    }


}
