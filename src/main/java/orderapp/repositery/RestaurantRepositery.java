package orderapp.repositery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import orderapp.entity.Food;
import orderapp.entity.Order;
import orderapp.entity.Restaurant;

public interface RestaurantRepositery extends JpaRepository<Restaurant, Integer> {
	
	@Query("Select r.food from Restaurant r where r.id = :restaurantId")
	List<Food> FindFoodyByRestaurentId(@Param(value = "restaurantId") int id);
	
	@Query("Select r.orders from Restaurant r where r.id = :restaurantId")
	List<Order> FindOrdersByRestaurantId(@Param(value = "restaurantId") int id);

}
