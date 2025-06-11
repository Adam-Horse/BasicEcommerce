package com.adamhorse.basicecommerce.repositories;

import com.adamhorse.basicecommerce.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}