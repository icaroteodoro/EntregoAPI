package com.entrego.services;



import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import com.entrego.domain.*;
import com.entrego.dtos.ItemOrderRequest;
import com.entrego.dtos.ItemsOrderResponse;
import com.entrego.dtos.OrderResponse;
import com.entrego.enums.OrderStatus;
import com.entrego.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entrego.dtos.OrderDTO;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StoreRepository storeRepository;

	@Autowired
	private ProductService productService;

	@Autowired
	private ItemOrderRepository itemOrderRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private ProductOptionRepository productOptionRepository;

	public Order createOrder(OrderDTO request) throws Exception {
		Order newOrder = new Order(request);

		User user = this.userRepository.findUserById(request.userId()).orElseThrow(() -> new Exception("User not found"));
		Store store = this.storeRepository.findById(request.storeId()).orElseThrow(() -> new Exception("Store not found"));

		List<ItemOrderRequest> itemsOrderRequest = request.items();
		List<ItemOrder> itemsOrder = new ArrayList<ItemOrder>();

        for (ItemOrderRequest item : itemsOrderRequest) {
            Product product = this.productService.findProductById(item.productId());
			ItemOrder itemRequest = new ItemOrder();
			itemRequest.setName(product.getName());
			
			BigDecimal price = product.getPrice();
			List<ItemOrderOption> selectedOptions = new ArrayList<>();

			if (item.options() != null && !item.options().isEmpty()) {
				for (String optionId : item.options()) {
					ProductOption option = this.productOptionRepository.findById(optionId)
							.orElseThrow(() -> new Exception("Option not found: " + optionId));
					// TODO: Add validation to check if option belongs to product's group
					if (option.getPrice() != null) {
						price = price.add(option.getPrice());
					}
					// Create decoupled ItemOrderOption
					ItemOrderOption orderOption = new ItemOrderOption(option.getName(), option.getPrice(), itemRequest);
					selectedOptions.add(orderOption);
				}
				itemRequest.setOptions(selectedOptions);
			}

			if(product.getMinPrice() != null) {
				price = price.max(product.getMinPrice());
			}
			
			itemRequest.setPrice(price);
			itemRequest.setQuantity(item.quantity());

			ItemOrder newItem = this.itemOrderRepository.save(itemRequest);

			itemsOrder.add(newItem);
        }


		newOrder.setUser(user);
		newOrder.setStore(store);
		newOrder.setItems(itemsOrder);


		BigDecimal total = new BigDecimal(0);

		for (int i = 0; i < newOrder.getItems().size(); i++) {
			total.add(newOrder.getItems().get(i).getPrice().multiply(new BigDecimal(newOrder.getItems().get(i).getQuantity())));
		}

		newOrder.setTotal(total);

		Order order = this.repository.save(newOrder);

		this.notificationService.notifyOrderCreated(order);

		return newOrder;
	}


	public Order updateStatusByOrderId(String requestId, OrderStatus status) {
		Order order = this.repository.getReferenceById(requestId);
		order.setStatus(status);
		order.setUpdatedAt(LocalDateTime.now());
		return this.repository.save(order);
	}

	
	public List<Order> findOrdersByUserId(String id){
		return this.repository.findOrdersByUserId(id);
	}
	public List<Order> findOrdersByStoreId(String id){
		return this.repository.findOrdersByStoreId(id);
	}

	public List<OrderResponse> findOrdersByStoreEmail(String email){
		List<Order> orders = this.repository.findOrdersByStoreAccountEmailOrderByCreatedAt(email);

		List<OrderResponse> orderResponses = new ArrayList<>();

		System.out.println(orderResponses);

		for (Order order: orders) {
			orderResponses.add(order.getOrderResponse(this.addressRepository));
		}
		return orderResponses;
	}

	public List<OrderResponse> findOrdersByStoreEmailToday(String email){
		LocalDateTime date = LocalDateTime.now();
		List<Order> orders = this.repository.findOrdersByStoreAccountEmailAndCreatedAt_DayOfMonthOrderByCreatedAt(email, date.getDayOfMonth());

		List<OrderResponse> orderResponses = new ArrayList<>();

		for (Order order: orders) {
			orderResponses.add(order.getOrderResponse(this.addressRepository));
		}
		return orderResponses;
	}





	public Order updateStatusOrder(Order order){
		return this.repository.save(order);
	}

	public Order findOrderById(String id) throws Exception {
		return this.repository.findById(id).orElseThrow(() -> new Exception("Order not found"));
	}




}
