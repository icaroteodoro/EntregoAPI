package com.entrego.controllers;

import java.util.List;

import com.entrego.domain.Order;
import com.entrego.dtos.RequestUpdateStatusOfRequest;
import com.entrego.enums.OrderStatus;
import com.entrego.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.entrego.dtos.OrderDTO;

@RestController
@RequestMapping("/request")
public class OrderController {
	

	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public Order createOrder(@RequestBody OrderDTO data) throws Exception {
        return this.orderService.createOrder(data);
	}
	
	
	@GetMapping
	@RequestMapping("/user/{userId}")
	public List<Order> findOrdersByUserId(@PathVariable String userId){
		return orderService.findOrdersByUserId(userId);
	}
	
	@GetMapping
	@RequestMapping("/enterprise/{storeId}")
	public List<Order> findOrdersByStoreId(@PathVariable String storeId){
		return orderService.findOrdersByStoreId(storeId);
	}

	@PutMapping
	@RequestMapping("/update-status/{orderId}")
	public Order updateStatusByOrderId (@PathVariable String orderId, @RequestBody RequestUpdateStatusOfRequest data) {
		return this.orderService.updateStatusByOrderId(orderId, OrderStatus.fromValue(data.status()));
	}


	
}
