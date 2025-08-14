package orderapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import orderapp.dto.ResponseStructure;
import orderapp.entity.User;
import orderapp.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	public UserService userService;
	
	@PostMapping("/save")
	public ResponseEntity<ResponseStructure<User>> createUser(@RequestBody User user) {
		User response = userService.createUser(user);

		ResponseStructure<User> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage("User created successfully.");
		apiResponse.setStatusCode(HttpStatus.CREATED.value());

		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}

	@GetMapping("/getById/{id}")
	public ResponseEntity<ResponseStructure<User>> getUserById(@PathVariable Integer id)
	{
		User response = userService.getUser(id);
		ResponseStructure<User> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage("Restaurant created successfully.");
		apiResponse.setStatusCode(HttpStatus.FOUND.value());

		return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
	}
	
	@GetMapping("/getAllUser")
	public ResponseEntity<ResponseStructure<List<User>>> getUserById()
	{
		List<User> response = userService.getAllUser();
		ResponseStructure<List<User>> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage("Restaurant created successfully.");
		apiResponse.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.ok(apiResponse);
	}
	
	@PutMapping("/updateUser/{id}")
	public ResponseEntity<ResponseStructure<User>> updateUser(@PathVariable Integer id,@RequestBody User user)
	{
		User response = userService.updateUser(user, id);
		ResponseStructure<User> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage("Restaurant created successfully.");
		apiResponse.setStatusCode(HttpStatus.OK.value());

		return ResponseEntity.ok(apiResponse);
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public ResponseEntity deleteUser(@PathVariable Integer id)
	{
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PatchMapping("/image/{id}")
	public ResponseEntity<ResponseStructure<String>> UploadImage(@RequestParam MultipartFile image ,@PathVariable Integer id) throws IOException
	{
		String response = userService.uploadImage(image, id);
		ResponseStructure<String> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage(" successfully.");
		apiResponse.setStatusCode(HttpStatus.OK.value());

		return new ResponseEntity<>(apiResponse,HttpStatus.OK);
	}
	
	@GetMapping(value ="/getImage/{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable Integer id)
	{
		byte[] image = userService.getImage(id);
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(image);
		
	}
	
	
	
}
