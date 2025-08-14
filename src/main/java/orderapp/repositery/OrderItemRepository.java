package orderapp.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import orderapp.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>{

}
