package com.smart.data.msuser.repository;

import com.smart.data.msuser.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    public Optional<User> findByName(String name);

    public Optional<User> findByEmail(String email);
}
