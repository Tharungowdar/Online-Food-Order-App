package orderapp.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
	private Integer foodId;
	private int quantity;
}
