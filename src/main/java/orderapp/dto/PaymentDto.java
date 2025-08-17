package orderapp.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentDto {
	@NotNull
	private List<OrderItemRequest> orderItems;
	@NotNull
	private boolean paymentSuccessful;
	@NotNull
	private Integer restaurantId;
	@NotNull
	private Integer userId;
}
