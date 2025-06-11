package com.adamhorse.basicecommerce.repositories;

import com.adamhorse.basicecommerce.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}