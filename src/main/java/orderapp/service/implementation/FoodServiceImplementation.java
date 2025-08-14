package orderapp.service.implementation;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import orderapp.entity.Food;
import orderapp.entity.Restaurant;
import orderapp.repositery.FoodRepositery;
import orderapp.repositery.RestaurantRepositery;
import orderapp.service.FoodService;

@Service
public class FoodServiceImplementation implements FoodService {

	@Autowired
	private FoodRepositery foodRepositery;
	
	@Autowired
	private RestaurantRepositery restaurantRepositery;

	@Override
	public Food createFood(Food food) {

		return foodRepositery.save(food);
	}

	@Override
	public Food findById(Integer id) {
		Optional<Food> food = foodRepositery.findById(id);
		if (food.isEmpty()) {
			throw new NoSuchElementException("Food object of Id " + id + "is not found");
		} else {
			return food.get();
		}
	}

	@Override
	public Page<Food> getAllFood(int pageNum, int pageSize) {
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		Page page = foodRepositery.findAll(pageable);
		return page;
	}

	@Override
	public Food updateFood(Food food, Integer id) {
		Food existing = findById(id);
		existing.setName(food.getName());
		existing.setDescription(food.getDescription());
		existing.setPrice(food.getPrice());
		return createFood(existing);
	}

	@Override
	public void deleteFood(Integer id) {
		Food food = findById(id);
		List<Restaurant> restaurants = food.getRestaurants();
		
		if(restaurants.size() == 0)
		{
			foodRepositery.delete(food);
			return;
		}
		
		for (Restaurant restaurant : restaurants) {
			restaurant.getFood().remove(food);
			
		}
		restaurantRepositery.saveAll(restaurants);
		foodRepositery.delete(food);
		
	}

}
