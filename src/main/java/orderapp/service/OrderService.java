package orderapp.service;

import orderapp.dto.BillResponse;
import orderapp.dto.OrderRequest;
import orderapp.dto.PaymentDto;
import orderapp.entity.Order;
import orderapp.entity.OrderStatus;

public interface OrderService {
	
	BillResponse generateBill(OrderRequest orderRequest);
	
	String payAndPlaceOrder(PaymentDto payment);
	
	void deleteOrder(Integer id);
	
	Order getOrder(Integer id);
	
	Order updateStatusByAdmin(OrderStatus status,Integer id);
	
	String cancleOrder(Integer id);
}
