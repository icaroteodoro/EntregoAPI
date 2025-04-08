package com.entrego.services;



import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.*;

import com.entrego.domain.*;
import com.entrego.dtos.ItemsOrderResponse;
import com.entrego.dtos.OrderResponse;
import com.entrego.enums.OrderStatus;
import com.entrego.repositories.AddressRepository;
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

	@Autowired
	private ProductService productService;

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private NotificationService notificationService;

	public Order createOrder(OrderDTO request) throws Exception {
		Order newOrder = new Order(request);

		User user = this.userRepository.findUserById(request.userId()).orElseThrow(() -> new Exception("User not found"));
		Store store = this.storeRepository.findById(request.storeId()).orElseThrow(() -> new Exception("Store not found"));

		List<String> productIds = request.productIds();
		List<Product> products = new ArrayList<Product>();

        for (String productId : productIds) {
            Product product = this.productService.findProductById(productId);
            products.add(product);
        }


		newOrder.setUser(user);
		newOrder.setStore(store);
		newOrder.setProducts(products);

		double total = 0.0;

		for (int i = 0; i < newOrder.getProducts().size(); i++) {
			total = total + newOrder.getProducts().get(i).getPrice();
		}

		newOrder.setTotal(total);

		this.repository.save(newOrder);
		
		// Enviar notificação de criação de pedido
		this.notificationService.notifyOrderCreated(newOrder);
		
		return newOrder;
	}


	public Order updateStatusByOrderId(String requestId, OrderStatus status) {
		Order order = this.repository.getReferenceById(requestId);
		order.setStatus(status);
		order.setUpdatedAt(LocalDateTime.now());
		
		Order updatedOrder = this.repository.save(order);
		
		// Enviar notificação de alteração de status
		this.notificationService.notifyOrderStatusChanged(updatedOrder);
		
		return updatedOrder;
	}

	
	public List<Order> findOrdersByUserId(String id){
		return this.repository.findOrdersByUserId(id);
	}
	public List<Order> findOrdersByStoreId(String id){
		return this.repository.findOrdersByStoreId(id);
	}
	public List<OrderResponse> findOrdersByStoreEmail(String email){
		List<Order> orders = this.repository.findOrdersByStoreEmail(email);

		List<OrderResponse> responses = new ArrayList<>();

		for (Order order: orders) {
			List<Product> products = order.getProducts();
			List<ItemsOrderResponse> items = new ArrayList<>();

			// Mapa para contar a quantidade de cada produto
			Map<Product, Integer> productCount = new HashMap<>();

			// Conta a quantidade de cada produto na lista
			for (Product product : products) {
				productCount.put(product, productCount.getOrDefault(product, 0) + 1);
			}

			// Percorre o mapa e adiciona os itens à lista de resposta
			for (Map.Entry<Product, Integer> entry : productCount.entrySet()) {
				Product product = entry.getKey();
				int quantity = entry.getValue();
				double totalPrice = product.getPrice() * quantity; // Calcula o preço total

				items.add(new ItemsOrderResponse(
						product.getName(),  // Nome do produto
						totalPrice,         // Preço total (preço unitário * quantidade)
						quantity            // Quantidade do produto
				));
			}

			OrderResponse orderResponse = new OrderResponse(
						order.getId(),
					order.getUser().getFirstName() + " " + order.getUser().getLastName(),
						order.getNumberOrder(),
						order.getCreatedAt(),
						order.getStatus(),
						this.addressRepository.findAddressByUserIdAndIsMain(order.getUser().getId()),
						order.getPaymentMethod(),
						items
					);
			responses.add(orderResponse);
		}
		return responses;
	}

	public List<OrderResponse> findOrdersByStoreEmailToday(String email){
		LocalDateTime date = LocalDateTime.now();
		List<Order> orders = this.repository.findOrdersByStoreEmailAndCreatedAt_DayOfMonth(email, date.getDayOfMonth());

		List<OrderResponse> responses = new ArrayList<>();

		for (Order order: orders) {
			List<Product> products = order.getProducts();
			List<ItemsOrderResponse> items = new ArrayList<>();

			// Mapa para contar a quantidade de cada produto
			Map<Product, Integer> productCount = new HashMap<>();

			// Conta a quantidade de cada produto na lista
			for (Product product : products) {
				productCount.put(product, productCount.getOrDefault(product, 0) + 1);
			}

			// Percorre o mapa e adiciona os itens à lista de resposta
			for (Map.Entry<Product, Integer> entry : productCount.entrySet()) {
				Product product = entry.getKey();
				int quantity = entry.getValue();
				double totalPrice = product.getPrice() * quantity;

				items.add(new ItemsOrderResponse(
						product.getName(),
						totalPrice,
						quantity
				));
			}

			OrderResponse orderResponse = new OrderResponse(
					order.getId(),
					order.getUser().getFirstName() + " " + order.getUser().getLastName(),
					order.getNumberOrder(),
					order.getCreatedAt(),
					order.getStatus(),
					this.addressRepository.findAddressByUserIdAndIsMain(order.getUser().getId()),
					order.getPaymentMethod(),
					items
			);
			responses.add(orderResponse);
		}
		return responses;
	}


	public Order updateStatusOrder(Order order){
		Order updatedOrder = this.repository.save(order);
		
		// Enviar notificação de alteração de status
		this.notificationService.notifyOrderStatusChanged(updatedOrder);
		
		return updatedOrder;
	}

	public Order findOrderById(String id) throws Exception {
		return this.repository.findById(id).orElseThrow(() -> new Exception("Order not found"));
	}

}
