package com.adamhorse.basicecommerce.repositories;

import com.adamhorse.basicecommerce.dtos.ProductDto;
import com.adamhorse.basicecommerce.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //Tells Hibernate to eagerly fetch the category when fetching products
    @EntityGraph(attributePaths = "category")
    List<Product> findByCategoryId(byte categoryId);

    @Query("SELECT p FROM Product p JOIN FETCH p.category")
    List<Product> findAllWithCategory();

}