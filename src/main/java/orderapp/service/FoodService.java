package orderapp.service;

import org.springframework.data.domain.Page;

import orderapp.entity.Food;

public interface FoodService {

	Food createFood(Food food);

	Food findById(Integer id);
	
	Page<Food> getAllFood(int pageNum,int pageSize);
	
	Food updateFood(Food food, Integer id);
	
	void deleteFood(Integer id);

}
