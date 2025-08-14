package orderapp.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import orderapp.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

}
