package com.roslonek.contracts.repositories;

import com.roslonek.contracts.entities.User;
import com.roslonek.contracts.entities.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    UserDetails findByUserId(long userId);
}
