package com.roslonek.contracts.repositories;

import com.roslonek.contracts.entities.User;
import com.roslonek.contracts.entities.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("from User where email=:email")
    User findUser(@Param("email") String email);

    User findByEmail(String email);
}
