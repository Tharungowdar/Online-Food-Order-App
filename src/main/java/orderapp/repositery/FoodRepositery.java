package orderapp.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import orderapp.entity.Food;

public interface FoodRepositery extends JpaRepository<Food,Integer> {

}
