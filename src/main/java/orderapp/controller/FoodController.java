package orderapp.controller;

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

import orderapp.dto.ResponseStructure;
import orderapp.entity.Food;
import orderapp.service.FoodService;

@RestController
@RequestMapping("/food/api")
public class FoodController {

	@Autowired
	private FoodService foodService;

	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<Food>> createFood(@RequestBody Food food) {
		Food response = foodService.createFood(food);
		ResponseStructure<Food> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage("food adde Succesfully !");
		apiResponse.setStatusCode(HttpStatus.CREATED.value());

		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<ResponseStructure<Food>> findFoodById(@PathVariable Integer id) {
		Food response = foodService.findById(id);
		ResponseStructure<Food> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage("Food found Succesfully !");
		apiResponse.setStatusCode(HttpStatus.FOUND.value());

		return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<ResponseStructure<Page>> getAllFood(
			@RequestParam(defaultValue = "0", required = false) int pageNum,
			@RequestParam(defaultValue = "4", required = false) int pageSize)
	{
		Page response = foodService.getAllFood(pageNum, pageSize);
		ResponseStructure<Page> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage("All Food Items Found");
		apiResponse.setStatusCode(HttpStatus.OK.value());
		
		return ResponseEntity.ok(apiResponse);
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<ResponseStructure<Food>> updateFood(@PathVariable Integer id,@RequestBody Food food)
	{
		Food response = foodService.updateFood(food, id);
		ResponseStructure<Food> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage("Food object update Succesfully !");
		apiResponse.setStatusCode(HttpStatus.OK.value());

		return  ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity deleteFood(@PathVariable Integer id) {
		foodService.deleteFood(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

}
