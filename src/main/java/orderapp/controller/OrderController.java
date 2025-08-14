package orderapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import orderapp.dto.BillResponse;
import orderapp.dto.OrderRequest;
import orderapp.dto.PaymentDto;
import orderapp.dto.ResponseStructure;
import orderapp.entity.Order;
import orderapp.entity.OrderStatus;
import orderapp.entity.Restaurant;
import orderapp.repositery.OrderItemRepository;
import orderapp.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderItemRepository orderItemRepository;
	
	@Autowired
	private OrderService orderService;

    OrderController(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }
	
	@PostMapping("/generate-bill")
	public ResponseEntity<ResponseStructure<BillResponse>> generateBill(@RequestBody OrderRequest orderRequest){
		BillResponse response = orderService.generateBill(orderRequest);
		ResponseStructure<BillResponse> apiResponse = new ResponseStructure();
		apiResponse.setData(response);
		apiResponse.setMessage("Bill has been generated");
		apiResponse.setStatusCode(HttpStatus.CREATED.value());
		
		return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
	}
	
	@PostMapping("/pay-and-place-order")
	public ResponseEntity<ResponseStructure<String>> payAndPlaceOrder(@RequestBody PaymentDto payment){
		String data = orderService.payAndPlaceOrder(payment);
		ResponseStructure<String> apiResponse = new ResponseStructure();
		apiResponse.setData(data);
		apiResponse.setMessage("Order placed");
		apiResponse.setStatusCode(HttpStatus.CREATED.value());
		
		return new ResponseEntity<>(apiResponse,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity delete(@PathVariable Integer id)
	{
		orderService.deleteOrder(id);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<ResponseStructure<Order>> getById(@PathVariable Integer id) {
		Order response = orderService.getOrder(id);

		ResponseStructure<Order> apiResponse = new ResponseStructure<>();
		apiResponse.setStatusCode(HttpStatus.FOUND.value());
		apiResponse.setMessage("Order object found successfully.");
		apiResponse.setData(response);

		return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
	}
	
	@PatchMapping("/updateStatus/{id}")
	public ResponseEntity<ResponseStructure<Order>> updateStatus(@PathVariable Integer id,
			@RequestParam OrderStatus status) {
		Order response = orderService.updateStatusByAdmin(status,id);
		ResponseStructure<Order> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage("Status update succesfully");
		apiResponse.setStatusCode(HttpStatus.OK.value());

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
	@PatchMapping("/cancle/{id}")
	public ResponseEntity<ResponseStructure<String>> cancleOrder(@PathVariable Integer id) {
		String response = orderService.cancleOrder(id);
		ResponseStructure<String> apiResponse = new ResponseStructure<>();
		apiResponse.setData(response);
		apiResponse.setMessage("canclled");
		apiResponse.setStatusCode(HttpStatus.OK.value());

		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}
	
}
