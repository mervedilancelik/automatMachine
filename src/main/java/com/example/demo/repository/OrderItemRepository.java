package com.example.demo.repository;

import com.example.demo.model.entity.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {

    OrderItemEntity findFirstByStatusOrderByIdDesc(String status);

    OrderItemEntity findByProductId(Integer productId);

    @Query("SELECT ord.status FROM OrderItemEntity ord where ord.status ='TRANSACTION IN PROGRESS' and ord.productId = :productId")
    String getStatusByProductId(@Param("productId") Integer productId);
}
