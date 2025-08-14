package orderapp.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import orderapp.entity.Food;
import orderapp.entity.Order;
import orderapp.entity.Restaurant;

public interface RestaurantService {

	Restaurant createRestaurant(Restaurant restaurant);

	Restaurant getById(Integer id);

	Page findAllRestaurants(int pagenum, int pageSize, String sortBy);

	Restaurant upadte(Integer id, Restaurant restaurant);
	
	void delete(Integer id);
	
	Restaurant addFoodItems(Integer restaurantId, Set<Integer> foodId);
	
	List<Food> FindRestaurantById(Integer id);
	
	List<Order> FindOrdersByRestaurantId(Integer id);

}
