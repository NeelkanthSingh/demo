package com.example.demo.repository;

import com.example.demo.entity.UserDetails;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository class to store the user details
@Repository
@Scope("prototype")
public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {

    UserDetails findByUserId(String userId);

    UserDetails findByEmailId(String emailId);

}
