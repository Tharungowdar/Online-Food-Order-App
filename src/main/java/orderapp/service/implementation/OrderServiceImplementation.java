package orderapp.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import orderapp.dto.BillResponse;
import orderapp.dto.OrderItemRequest;
import orderapp.dto.OrderRequest;
import orderapp.dto.PaymentDto;
import orderapp.entity.Food;
import orderapp.entity.Order;
import orderapp.entity.OrderItem;
import orderapp.entity.OrderStatus;
import orderapp.entity.Restaurant;
import orderapp.entity.User;
import orderapp.exception.PaymentFailedException;
import orderapp.repositery.OrderRepository;
import orderapp.service.FoodService;
import orderapp.service.OrderService;
import orderapp.service.RestaurantService;
import orderapp.service.UserService;

@Service
@RequiredArgsConstructor
public class OrderServiceImplementation implements OrderService {

	private final RestaurantService restaurantService;
	private final FoodService foodService;
	private final OrderRepository orderRepository;
	private final UserService userService;

	@Override
	public BillResponse generateBill(OrderRequest orderRequest) {
		Restaurant restaurant = restaurantService.getById(orderRequest.getRestaurantId());
		StringBuilder summary = new StringBuilder();

		float totalPrice = 0;

		for (OrderItemRequest orderItem : orderRequest.getOrderItems()) {
			Food food = foodService.findById(orderItem.getFoodId());
			float price = food.getPrice() * orderItem.getQuantity();
			totalPrice += price;
			summary.append(food.getName()).append(" X ").append(orderItem.getQuantity()).append(" = ").append(price)
					.append(" ");
		}

		return new BillResponse(restaurant.getName(), summary.toString(), totalPrice);
	}

	@Override
	public String payAndPlaceOrder(PaymentDto payment) {
		// simulate payment
		if (payment.isPaymentSuccessful()) {
			Order order = new Order();
			order.setStatus(OrderStatus.PLACED);

			Restaurant restaurant = restaurantService.getById(payment.getRestaurantId());
			// set restaurant to order
			order.setRestuarant(restaurant);
			
			User user = userService.getUser(payment.getUserId());
			// set user to order
			order.setUser(user);

			List<OrderItem> items = new ArrayList();
			double totalPrice = 0;

			for (OrderItemRequest request : payment.getOrderItems()) {
				Food food = foodService.findById(request.getFoodId());

				OrderItem orderItem = new OrderItem();
				orderItem.setFood(food);
				orderItem.setQuantity(request.getQuantity());
				orderItem.setOrder(order);
				items.add(orderItem);

				double price = food.getPrice() * request.getQuantity();
				totalPrice += price;
			}

			order.setTotalPrice(totalPrice);
			order.setOrderItems(items);
			orderRepository.save(order);
			return "Order has been placed";
		} else {
			throw new PaymentFailedException("Payment was not successful, hence order cannot be placed");
		}
	}

	@Override
	public void deleteOrder(Integer id) {
		Order order = getOrder(id);
		orderRepository.delete(order);

	}

	@Override
	public Order getOrder(Integer id) {
		Optional<Order> order = orderRepository.findById(id);
		if (order.isPresent()) {
			return order.get();
		} else {
			throw new NoSuchElementException("Order with ID:" + id + "does not exist");
		}
	}

	@Override
	public Order updateStatusByAdmin(OrderStatus status, Integer id) {
		Order order = getOrder(id);
		order.setStatus(status);
		return orderRepository.save(order);
	}

	@Override
	public String cancleOrder(Integer id) {
		Order order = getOrder(id);
		order.setStatus(OrderStatus.CANCELLED);

		orderRepository.save(order);

		return "Payment will be returned in 2 working hours";
	}

}
