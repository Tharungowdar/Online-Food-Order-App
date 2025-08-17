package orderapp.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequest {
	@NotNull
	private List<OrderItemRequest> orderItems;
	@NotNull
	private Integer restaurantId;
}
