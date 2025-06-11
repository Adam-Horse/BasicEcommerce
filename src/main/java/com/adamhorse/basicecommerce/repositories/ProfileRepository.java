package com.adamhorse.basicecommerce.repositories;

import com.adamhorse.basicecommerce.entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}