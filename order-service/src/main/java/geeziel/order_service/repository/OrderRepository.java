package geeziel.order_service.repository;

import geeziel.order_service.dto.OrderStatus;
import geeziel.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Query("""
                update Order o
                   set o.status = 'CANCELLED'
                 where o.id = :orderId
            """)
    int cancelOrder(@Param("orderId") Long orderId);

    @Modifying
    @Query("""
                update Order o
                   set o.status = :newStatus
                 where o.id = :orderId
            """)
    int updateStatus(@Param("orderId") Long orderId, @Param("newStatus") OrderStatus newStatus);

}
