package com.entrego.controllers;

import java.util.List;

import com.entrego.domain.Order;
import com.entrego.dtos.order.OrderResponse;
import com.entrego.dtos.order.RequestOrderStatusUpdate;
import com.entrego.dtos.order.RequestUpdateStatusOfRequest;
import com.entrego.enums.OrderStatus;
import com.entrego.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.entrego.dtos.order.OrderDTO;

@RestController
@RequestMapping("/order")
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
	
	//@GetMapping
	//@RequestMapping("/store/{storeId}")
	//public List<Order> findOrdersByStoreId(@PathVariable String storeId){
	//	return orderService.findOrdersByStoreId(storeId);
	//}

	@PutMapping
	@RequestMapping("/update-status/{orderId}")
	public Order updateStatusByOrderId (@PathVariable String orderId, @RequestBody RequestUpdateStatusOfRequest data) {
		return this.orderService.updateStatusByOrderId(orderId, OrderStatus.fromValue(data.status()));
	}

	@GetMapping
	@RequestMapping("/store/{email}")
	public List<OrderResponse> findOrdersByStoreEmail(@PathVariable String email) {
		return this.orderService.findOrdersByStoreEmail(email);
	}

	@GetMapping
	@RequestMapping("/store/today/{email}")
	public List<OrderResponse> findOrdersByStoreEmailToday(@PathVariable String email) {
		return this.orderService.findOrdersByStoreEmailToday(email);
	}


	@PutMapping("/update")
	public Order updateStatusOrder(@RequestBody RequestOrderStatusUpdate data) throws Exception {
		return this.orderService.updateStatusOrder(data.orderId(), data.status());
	}
	
}
