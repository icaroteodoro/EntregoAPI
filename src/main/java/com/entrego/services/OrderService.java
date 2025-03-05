package com.entrego.services;



import java.util.List;

import com.entrego.domain.Order;
import com.entrego.domain.Store;
import com.entrego.domain.User;
import com.entrego.enums.OrderStatus;
import com.entrego.repositories.StoreRepository;
import com.entrego.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entrego.dtos.OrderDTO;
import com.entrego.repositories.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StoreRepository storeRepository;


	public Order createOrder(OrderDTO request) throws Exception {
		Order newOrder = new Order(request);

		User user = this.userRepository.findUserById(request.userId()).orElseThrow(() -> new Exception("User not found"));
		Store store = this.storeRepository.findById(request.storeId()).orElseThrow(() -> new Exception("Store not found"));

		newOrder.setUser(user);
		newOrder.setStore(store);

		double total = 0.0;

		for (int i = 0; i < newOrder.getProducts().size(); i++) {
			total = total + newOrder.getProducts().get(i).getPrice();
		}

		newOrder.setTotal(total);

		this.repository.save(newOrder);
		return newOrder;
	}


	public Order updateStatusByOrderId(String requestId, OrderStatus status) {
		Order order = this.repository.getReferenceById(requestId);
		order.setStatus(status);
		return this.repository.save(order);
	}

	
	public List<Order> findOrdersByUserId(String id){
		return this.repository.findOrdersByUserId(id);
	}
	public List<Order> findOrdersByStoreId(String id){
		return this.repository.findOrdersByStoreId(id);
	}
}
