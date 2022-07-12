package com.example.demo.repository;


import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Query("SELECT COUNT(p) FROM ProductEntity p where p.productType = :type")
    Long countAll(@Param("type") String type);

    @Query("SELECT new com.example.demo.model.dto.ProductDto(p.productName, p.productType,p.price) FROM ProductEntity p WHERE p.id = :id")
    ProductDto findByProductId(@Param("id") Integer id);

}
