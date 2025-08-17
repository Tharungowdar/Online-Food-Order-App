package orderapp.controller;

import java.util.List;
import java.util.Set;

import orderapp.service.implementation.RestaurantServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import orderapp.OnlineFoodOrderApplication;
import orderapp.dto.ResponseStructure;
import orderapp.entity.Food;
import orderapp.entity.Order;
import orderapp.entity.Restaurant;
import orderapp.exception.GlobalExceptionHandler;
import orderapp.repositery.RestaurantRepositery;
import orderapp.service.RestaurantService;

@RestController
@RequestMapping("/restaurant/api")
public class RestaurantController {

	@Autowired
	private RestaurantService restaurantService;

	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<Restaurant>> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
		Restaurant response = restaurantService.createRestaurant(restaurant);

		ResponseStructure<Restaurant> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage("Restaurant created successfully.");
		apiResponse.setStatusCode(HttpStatus.CREATED.value());

		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<Restaurant>> getById(@PathVariable Integer id) {
		Restaurant response = restaurantService.getById(id);

		ResponseStructure<Restaurant> apiResponse = new ResponseStructure<>();
		apiResponse.setStatusCode(HttpStatus.FOUND.value());
		apiResponse.setMessage("Restaurant object found successfully.");
		apiResponse.setData(response);

		return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
	}

	@GetMapping("/getallrestaurants") // This is correct
	public ResponseEntity<ResponseStructure<Page>> getAllRestaurants(
			@RequestParam(defaultValue = "0", required = false) int pageNum,
			@RequestParam(defaultValue = "5", required = false) int pageSize,
			@RequestParam(defaultValue = "createdAt", required = false) String sortBy) {
		Page response = restaurantService.findAllRestaurants(pageNum, pageSize, sortBy);
		ResponseStructure<Page> apiresponse = new ResponseStructure<>();
		apiresponse.setStatusCode(HttpStatus.OK.value());
		apiresponse.setMessage("Restaurant objects found.");
		apiresponse.setData(response);
		return ResponseEntity.ok(apiresponse);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseStructure<Restaurant>> updateRestaurant(@PathVariable Integer id,
			@Valid @RequestBody Restaurant restaurant) {
		Restaurant response = restaurantService.upadte(id, restaurant);
		ResponseStructure<Restaurant> apiResponse = new ResponseStructure<Restaurant>();
		apiResponse.setData(response);
		apiResponse.setMessage("Resturant object update succesfully");
		apiResponse.setStatusCode(HttpStatus.OK.value());

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity deleteRestaurant(@PathVariable Integer id) {
		restaurantService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}
	
	@PutMapping("/addFood/{restaurantId}")
	public ResponseEntity<ResponseStructure<Restaurant>> addFoodItems(@PathVariable Integer restaurantId,@Valid @RequestBody Set<Integer> FoodId)
	{
		Restaurant response = restaurantService.addFoodItems(restaurantId, FoodId);
		ResponseStructure<Restaurant> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage("Added Food Items to Restaurant Succesfully !");
		apiResponse.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@GetMapping("/getFood/{id}")
	public ResponseEntity<ResponseStructure<List<Food>>> DisplayFoodByRestaurantId(@PathVariable Integer id)
	{
		List<Food> response = restaurantService.FindRestaurantById(id);
		ResponseStructure<List<Food>> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage("Found food items succesfully");
		apiResponse.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.ok(apiResponse);
	}

	
	@GetMapping("/getOrders/{id}")
	public ResponseEntity<ResponseStructure<List<Order>>> DisplayOrderByRestaurantId(@PathVariable Integer id)
	{
		List<Order> response = restaurantService.FindOrdersByRestaurantId(id);
		ResponseStructure<List<Order>> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage("Found orders succesfully");
		apiResponse.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.ok(apiResponse);
	}
}
