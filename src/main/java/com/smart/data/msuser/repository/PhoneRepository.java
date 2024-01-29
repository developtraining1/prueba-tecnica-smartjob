package com.smart.data.msuser.repository;

import com.smart.data.msuser.entity.Phone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends CrudRepository<Phone, String> {
}
