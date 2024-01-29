package com.smart.data.msuser.repository;

import com.smart.data.msuser.entity.UserSecurityInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecurityInfoRepository extends JpaRepository<UserSecurityInfo, Integer> {

    Optional<UserSecurityInfo> findByName(String username);

}
