package orderapp.service.implementation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import orderapp.entity.Food;
import orderapp.entity.Order;
import orderapp.entity.Restaurant;
import orderapp.repositery.FoodRepositery;
import orderapp.repositery.RestaurantRepositery;
import orderapp.service.FoodService;
import orderapp.service.RestaurantService;

@Service
public class RestaurantServiceImplementation implements RestaurantService {

	@Autowired
	private RestaurantRepositery restaurantRepositery;
	
	@Autowired
	private FoodService foodService;

	@Override
	public Restaurant createRestaurant(Restaurant restaurant) {

		return restaurantRepositery.save(restaurant);
	}

	@Override
	public Restaurant getById(Integer id) {
		Optional<Restaurant> response = restaurantRepositery.findById(id);

		if (response.isPresent()) {

			return response.get();
		} else {
			throw new NoSuchElementException("restaurant not found by the id : " + id);
		}

	}

	@Override
	public Page findAllRestaurants(int pagenum, int pageSize, String sortBy) {
		Sort sort = Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pagenum, pageSize, sort);
		Page page = restaurantRepositery.findAll(pageable);

		return page;
	}

	@Override
	public Restaurant upadte(Integer id, Restaurant restaurant) {
		Restaurant exiting = getById(id);
		exiting.setName(restaurant.getName());
		exiting.setAddress(restaurant.getAddress());
		exiting.setContactNumber(restaurant.getContactNumber());
		exiting.setEmail(restaurant.getEmail());

		return restaurantRepositery.save(restaurant);
	}

	@Override
	public void delete(Integer id) {
		Restaurant restaurant = getById(id);
		restaurantRepositery.delete(restaurant);
	}

	@Override
	public Restaurant addFoodItems(Integer restaurantId, Set<Integer> foodId) {
		Restaurant restaurant = getById(restaurantId);
		
		List<Food> food = new ArrayList<>();
		
		for(Integer id: foodId)
		{
			Food foodItem = foodService.findById(id);
			food.add(foodItem);
		}
		restaurant.setFood(food);
		return createRestaurant(restaurant);
	}

	@Override
	public List<Food> FindRestaurantById(Integer id) {
		List<Food> food = restaurantRepositery.FindFoodyByRestaurentId(id);
		if(food==null || food.size() == 0)
		{
			throw new NoSuchElementException("Restaurant with id"+id+"is not found or food assigned is not found");
		}else {
			return food;
		}
	}

	@Override
	public List<Order> FindOrdersByRestaurantId(Integer id) {
		List<Order> order = restaurantRepositery.FindOrdersByRestaurantId(id);
		if(order==null || order.size() == 0)
		{
			throw new NoSuchElementException("Restaurant with id"+id+"is not found or order assigned is not found");
		}else {
			return order;
		}
	}

}
